package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Constants;
import frc.robot.Subsystem;
import frc.robot.Constants.Axis;
import frc.robot.Constants.Buttons;


public class DriveTrain implements Subsystem{

    private NetworkTableInstance inst;
  private NetworkTable table;
  private NetworkTableEntry xEntry;
  private NetworkTableEntry yEntry;

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
        
        leftMotor2.configSupplyCurrentLimit(supplyCurrentConfig);
        rightMotor2.configSupplyCurrentLimit(supplyCurrentConfig);

    }

    private void initRamping(){
        leftMotor1.configOpenloopRamp(Constants.openRampDuration);
        rightMotor1.configOpenloopRamp(Constants.openRampDuration); 
        leftMotor2.configOpenloopRamp(Constants.openRampDuration);
        rightMotor2.configOpenloopRamp(Constants.openRampDuration);
    }

    private void initDiffDrive() {
        diffDrive = new DifferentialDrive(leftMotor1, rightMotor1);
    
    }

// default shifter to low for now
private double shifterVal = 0.5;
private double turnShifter = .65; // explore this farther
private double throttle;
private double turn;
// Put Throttle and Turn to the power of cThrottle and cTurn to have a better distribution curve
private double cThrottle = 7;
private double cTurn = 7;
private double deadSpace = 0.2;
private double minimumThreshold = 0.001;

    private void teleopDefaultDrive() {
        throttle = driveCtrl.getRawAxis(Axis.LY);
        turn = driveCtrl.getRawAxis(Axis.RX);


        // Map turn and throttle to be from "deadSpace" to 1.0
        // So that a small bump actually moves the robot ( < deadSpace doesn't move)
        // minimumThreshold is the minimum the controller has to move to map it. (Otherwise it'd move without user input)
        /*
        throttle = throttle > 0 ? Math.pow(Math.abs(throttle),cThrottle) : -Math.pow(Math.abs(throttle),cThrottle);
        turn = turn > 0 ? Math.pow(Math.abs(turn),cTurn) : -Math.pow(Math.abs(turn),cTurn);
        throttle = throttle * (1-deadSpace) + (Math.abs(throttle) < minimumThreshold ? 0.0 : (throttle > 0 ? deadSpace : -deadSpace));
        turn = turn * (1-deadSpace) + (Math.abs(turn) < minimumThreshold ? 0.0 : (turn > 0 ? deadSpace : -deadSpace));
        */
        // allow for manual quick turn enable 
        boolean isQuickTurn = driveCtrl.getRawButton(Constants.Buttons.R);

        // if left bumper button pressed, activate shifter
         if(driveCtrl.getRawButton(Constants.Buttons.L)) {
             shifterVal = 1;
         }
         if(Math.abs(throttle) < .02){
             isQuickTurn = true;
         }

        diffDrive.curvatureDrive(throttle * shifterVal, turn * turnShifter, isQuickTurn);

    }


    public void setUpPID(){
        
    }

    @Override
    public void Init() {
        // TODO Auto-generated method stub

    }

    @Override
    public void InitShuffle() {
        // TODO Auto-generated method stub
         NetworkTableInstance inst;
         NetworkTable table;
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