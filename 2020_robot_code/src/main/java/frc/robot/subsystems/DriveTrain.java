package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.CTRL_AXIS;
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
    //contructor
    public DriveTrain(XboxController controller){
        this.driveCtrl = controller;

        this.initCANMotors();
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
    
    private void initDiffDrive() {
        // SpeedControllerGroup leftDrive = new SpeedControllerGroup(leftMotor1, leftMotor2);
        // SpeedControllerGroup rightDrive = new SpeedControllerGroup(rightMotor1, rightMotor2);

        diffDrive = new DifferentialDrive(leftMotor1, rightMotor1);

    }

    private void teleopDefaultDrive() {
        diffDrive.curvatureDrive(driveCtrl.getRawAxis(Axis.LY)/2, driveCtrl.getRawAxis(Axis.RX), driveCtrl.getRawButton(Buttons.L));
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