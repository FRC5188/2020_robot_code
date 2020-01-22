package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Constants;
import frc.robot.Subsystem;
import frc.robot.Constants.Axis;
import frc.robot.Constants.Buttons;


public class DriveTrain implements Subsystem{

    //motor controllers
    WPI_TalonFX leftMotor1;
    WPI_TalonFX leftMotor2;
    WPI_TalonFX rightMotor1;
    WPI_TalonFX rightMotor2;
    DifferentialDrive diffDrive;

    XboxController driveCtrl;
    //constructor
    public DriveTrain(XboxController controller){
        this.driveCtrl = controller;

        this.initCANMotors();
        this.initCurrentLimit();
        this.initRamping();
        this.initDiffDrive();

    }

    private void initCANMotors() {
        this.leftMotor1 = new WPI_TalonFX(Constants.leftFalcon1);
        this.leftMotor2 = new WPI_TalonFX(Constants.leftFalcon2);
        this.rightMotor1 = new WPI_TalonFX(Constants.rightFalcon1);
        this.rightMotor2 = new WPI_TalonFX(Constants.rightFalcon2);

        //enable braking mode
        leftMotor1.setNeutralMode(NeutralMode.Brake);
        leftMotor2.setNeutralMode(NeutralMode.Brake);
        rightMotor1.setNeutralMode(NeutralMode.Brake);
        rightMotor2.setNeutralMode(NeutralMode.Brake);

        this.leftMotor2.follow(leftMotor1);
        this.rightMotor2.follow(rightMotor1);
    }
    private void initCurrentLimit(){
        //create current config, new for 2020
        //it is a little long........ :(
        SupplyCurrentLimitConfiguration supplyCurrentConfig;
        supplyCurrentConfig = new SupplyCurrentLimitConfiguration(
            true, Constants.SupplyCurrentLimit, Constants.SupplyTriggerCurremt, Constants.SupplyCurrentDuration
        );

        //apply current limits
        leftMotor1.configSupplyCurrentLimit(supplyCurrentConfig);
        rightMotor1.configSupplyCurrentLimit(supplyCurrentConfig);

    }

    private void initRamping(){
        leftMotor1.configOpenloopRamp(Constants.openRampDuration);
        rightMotor1.configOpenloopRamp(Constants.openRampDuration); 
    }

    private void initDiffDrive() {
        diffDrive = new DifferentialDrive(leftMotor1, rightMotor1);

    }

    private void teleopDefaultDrive() {
        // default shifter to low for now
        double shifterVal = 0.5;
        double throttle = driveCtrl.getRawAxis(Axis.LY);
        double turn = driveCtrl.getRawAxis(Axis.RX);

        // allow for manual quick turn enable 
        boolean isQuickTurn = driveCtrl.getRawButton(Constants.Buttons.R);

        // if left bumper button pressed, activate shifter
         if(driveCtrl.getRawButton(Constants.Buttons.L)){
             shifterVal = 1;
         }
         if(Math.abs(throttle) < .02){
             isQuickTurn = true;
         }

        diffDrive.curvatureDrive(throttle * shifterVal, turn, isQuickTurn);

    }


    @Override
    public void Init() {
        // TODO Auto-generated method stub

    }

    @Override
    public void InitShuffle() {
        // TODO Auto-generated method stub

    }

    @Override
    public void Operate() {
        // TODO Auto-generated method stub
        this.teleopDefaultDrive();

    }

    @Override
    public void Test() {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateShuffle() {
        // TODO Auto-generated method stub

    }

    @Override
    public void Kill() {
        // TODO Auto-generated method stub

    }



}