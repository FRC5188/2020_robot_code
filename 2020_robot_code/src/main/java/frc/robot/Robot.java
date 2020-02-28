/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.autonomous.AutoManager;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.ColorWheel;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
 
  ArrayList <Subsystem> subsystems = new ArrayList<>();

  XboxController driveController;
  DriveTrain dt;
  AutoManager autoManager = new AutoManager();
  Intake intake;
  Shooter shooter;
  Climber climber;
  ColorWheel colorWheel;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    driveController = new XboxController(Constants.driverPort);
    dt = new DriveTrain(driveController);
    intake = new Intake(driveController);
    shooter = new Shooter(driveController);
    climber = new Climber(driveController);
    colorWheel = new ColorWheel(driveController);
    subsystems.add(dt);
    subsystems.add(intake);
    subsystems.add(shooter);
    subsystems.add(climber);
    subsystems.add(colorWheel);
    autoManager.initShuffleboard();

    for(Subsystem subsystem: subsystems){
      subsystem.init();
      subsystem.initShuffle();
    }
    
  }

  public DriveTrain getDriveTrain() {
    return this.dt;
  }

  public Shooter getShooter() {
    return this.shooter;
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
    autoManager.updateShuffleboard();
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
    autoManager.init(this);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    // Was removed (for a reason?)
    //autoManager.periodic();
  }

  @Override
  public void disabledPeriodic() {
    super.disabledPeriodic();

    //put this here so encoders can be zeroed in disabled mode, 
    //since pid runs as soon as enabled. kinda bad....
    
    // stop putting things in here
    /*
    System.out.println(dt.getLeftEncoderInches());
    if(driveController.getRawButton(Constants.Buttons.X)){
      dt.resetEncoders();

    }
    */
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    //dt.Operate();
    for(Subsystem subsystem: subsystems){
      subsystem.operate();
    }

  
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

public static Object getControllerManager() {
	return null;
}

}
