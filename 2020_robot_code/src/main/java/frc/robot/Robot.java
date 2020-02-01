/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.autonomous.AutoManager;
import frc.robot.subsystems.DriveTrain;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  private static Robot inst;

  private NetworkTableInstance ntInst;
  private NetworkTable table;
  private NetworkTableEntry pidP;
  private NetworkTableEntry pidI;
  private NetworkTableEntry pidD;
  private NetworkTableEntry setPoint;
  XboxController driveController = new XboxController(Constants.driverPort);
  DriveTrain dt = new DriveTrain(driveController);
  PIDController distCont;
  AutoManager autoManager = new AutoManager();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    inst = this;
    dt.init();
    dt.initShuffle();
    autoManager.init(this);

    //shuffle board entrys to update pid values

    this.ntInst = NetworkTableInstance.getDefault();
    this.table = ntInst.getTable("SmartDashboard");
    this.pidP = table.getEntry("pid/p");
    this.pidI = table.getEntry("pid/i");
    this.pidD = table.getEntry("pid/d");
    this.setPoint = table.getEntry("pid/setPoint");
    this.pidP.setDouble(0.05);
    this.pidI.setDouble(0.00);
    this.pidD.setDouble(0.00);
    this.setPoint.setDouble(0.00);

    //make new controller
    distCont = new PIDController(0.05, 0, 0.02);
    distCont.setSetpoint(0.0);

    
  }
  /*
  public static Robot getInst() {
    return inst;
  }
  */
  public DriveTrain getDriveTrain() {
    return this.dt;
  }

  /** 
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
 
  }

  @Override
  public void disabledPeriodic() {
    super.disabledPeriodic();

    //put this here so encoders can be zeroed in disabled mode, 
    //since pid runs as soon as enabled. kinda bad....

    System.out.println(dt.getLeftEncoderInches());
    if(driveController.getRawButton(Constants.Buttons.X)){
      dt.resetEncoders();

    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    //dt.Operate();
    System.out.println(dt.getLeftEncoderInches());
    SmartDashboard.putNumber("Error", dt.getLeftEncoderInches()-this.setPoint.getValue().getDouble()); //this is so we can look at a graph of the values

    //update controller values
    distCont.setPID(this.pidP.getValue().getDouble(), this.pidI.getValue().getDouble(), this.pidD.getValue().getDouble());
    
    //if x button pressed, reset enocders
    if(driveController.getRawButton(Constants.Buttons.X)){
      dt.resetEncoders();

    }

    //calculate output from controller
    double PIDOutput = distCont.calculate(dt.getLeftEncoderInches(), this.setPoint.getValue().getDouble());
    
    //use output
    dt.tankDrive(PIDOutput, PIDOutput);

  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
