package frc.robot;

import frc.robot.utils.Gains;
import frc.robot.utils.InputButton;

public class Constants {
    
  //can ids
  public static final int leftFalcon1 = 0;
  public static final int leftFalcon2 = 1;
  public static final int rightFalcon1 = 2;
  public static final int rightFalcon2 = 3;

  public static final int shooterTopFalcon = 4;
  public static final int shooterBottomFalcon = 5;
  public static final int beltTop775Pro = 6;
  public static final int beltBottom775Pro = 7;
  public static final int wheelSpinner = 8;
  public static final int intakeMotor = 9;
  public static final int climberMotor = 10;

  public static final int frontLBsensor = 0;
  public static final int backLBsensor = 1;

  //solenoid channel 
  public static final int intakeSolenoid = 0;
  public static final int lifterSolenoid = 1;
  public static final int colorWheelSolenoid = 2;
  public static final int climbSolenoid = 3;

  //current limiting params
  public static final int DriveTrainSupplyTriggerCurremt = 35; // don't activate current limit until current exceeds __A...
  public static final int DriveTrainSupplyCurrentDuration = 50; //... for at least __ ms
  public static final int DriveTrainSupplyCurrentLimit = 30; // once current-limiting is activated, hold at __A
  
  public static final int ShooterSupplyTriggerCurremt = 35; // don't activate current limit until current exceeds __A...
  public static final int ShooterSupplyCurrentDuration = 0; //... for at least __ ms
  public static final int ShooterSupplyCurrentLimit = 30; // once current-limiting is activated, hold at __A

  public static final int BeltSupplyTriggerCurremt = 35; // don't activate current limit until current exceeds __A...
  public static final int BeltSupplyCurrentDuration = 0; //... for at least __ ms
  public static final int BeltSupplyCurrentLimit = 30; // once current-limiting is activated, hold at __A

  // Shooter Teleop Variables // 750 RPM
  public static final double shooterShooterSpeed = 1500.0/500.0*2000.0*2048.0/600.0; // Velocity, not percent output
  public static final double shooterBeltSpeed = 1.0; // TODO: Specify Speed vs Percent
  public static final double intakeShooterSpeed = 1000.0/500.0*2000.0*2048.0/600.0;
  public static final double intakeBeltSpeed = 0.2;


  public static final double ENCODER_TICKS_PER_INCH  = 941.1;
  public static final double openRampDuration = 0.5; //seconds from zero to full throttle
  public static final double shooterOpenRampDuration = 0.50; //seconds from zero to full throttle

  // Autonomous Task Move Variables
  public static final double TASK_MOVE_PID_P = 0.1;
  public static final double TASK_MOVE_PID_I = 0.0;
  public static final double TASK_MOVE_PID_D = 0.0;
  public static final double TASK_MOVE_DEFAULT_TOLERANCE = 1.0;
  public static final int TASK_MOVE_DEFAULT_TIME = 60; // MS

  // Autonomous Task Turn Variables
  public static final double TASK_TURN_PID_P = 0.1;
  public static final double TASK_TURN_PID_I = 0.0;
  public static final double TASK_TURN_PID_D = 0.0;
  public static final double TASK_TURN_DEFAULT_TOLERANCE = 1.0;
  public static final int TASK_TURN_DEFAULT_TIME = 0;

  // Autonomous Task Shoot Variables
  public static final int TASK_SHOOT_RUN_DEFAULT_TICKTIME_RUN = 1000; // MS
  
  // Autonomous Intake Variables
  public static final int TASK_INTAKE_TIME = 1000; // MS
  public static final double TASK_INTAKE_SPEED = 1.0;

  // Subsystem Variables while in Autonomous
  public static final double AUTONOMOUS_MAX_THROTTLE = 0.5;
  public static final double AUTONOMOUS_MAX_TURN = 0.5;
  public static final double AUTO_SHOOTER_SHOOTER_SPEED = Constants.shooterShooterSpeed; // default to 
  public static final double AUTO_SHOOTER_BELT_SPEED = Constants.shooterBeltSpeed; // teleop's defaults

  // Vision PID
  public static final double VISION_PID_P = 0.01;
  public static final double VISION_PID_I = 0;
  public static final double VISION_PID_D = 0;

  // Teleop button controls
  
  /*
  // For the Operator
  public static final int shooterCtrlShoot = Constants.Axis.RTrigger; // RTrigger
  public static final int shooterCtrlLiftToggle = Constants.Buttons.R; // Right Button

  //public static final int shooterCtrlReverse = Constants.Buttons.; // Don't need?
  public static final int intakeAxisForward = Axis.LTrigger; 
  public static final int intakeAxisBackward = Buttons.L; // L Button "Unjam"

  public static final int colorWheelPneumaticButton = Constants.Buttons.A; 
  public static final int colorWheelSpinButton = Constants.Buttons.X;

  public static final int climberCtrlAxis = Constants.Axis.LY;
  public static final int climberButtonToggle = Constants.Buttons.Y;
  // To driver
  public static final int[] intakeCtrlButtonToggle = new int[] {Constants.Buttons.A, Constants.Buttons.B,Constants.Buttons.X,Constants.Buttons.Y};
  */

  public static final InputButton shooterCtrlShoot = InputButton.OPERATOR_RTrigger; // RTrigger
  public static final InputButton shooterCtrlLiftToggle = InputButton.OPERATOR_R; // Right Button

  //public static final int shooterCtrlReverse = Constants.Buttons.; // Don't need?
  public static final InputButton intakeAxisForward = InputButton.OPERATOR_LTrigger; 
  public static final InputButton intakeAxisBackward = InputButton.OPERATOR_L; // L Button "Unjam"

  public static final InputButton colorWheelPneumaticButton = InputButton.OPERATOR_A; 
  public static final InputButton colorWheelSpinButton = InputButton.OPERATOR_X;

  public static final InputButton climberCtrlAxis = InputButton.OPERATOR_LY;
  public static final InputButton climberButtonToggle = InputButton.OPERATOR_Y;
  // To driver
  public static final InputButton intakeCtrlButtonToggle = new InputButton(
      InputButton.DRIVER_A, InputButton.DRIVER_B,InputButton.DRIVER_X,InputButton.DRIVER_Y);
  

  // Drive Train Buttons
  public static final InputButton throttleShiftButton = InputButton.DRIVER_R;
  public static final InputButton shooterAlignVisionButton = InputButton.DRIVER_L;
  
  //public static final InputButton shooterBeltIntake = InputButton.DRIVER_BACK;

  /*
  public static final int colorWheelButton = Constants.Buttons.Y;
  public static final int climberCtrlAxis = Constants.Axis.LX;
  public static final int shooterCtrlShoot = Constants.Buttons.R;
  public static final int shooterCtrlReverse = Constants.Buttons.L;
  public static final int intakeCtrlButtonToggle = Constants.Buttons.B;
  public static final int shooterCtrlLiftToggle = Constants.Buttons.X;
  public static final int climberButtonToggle = Constants.Buttons.Y;
  public static final int intakeAxisForward = Axis.LTrigger;
  public static final int intakeAxisBackward = Axis.RTrigger;
  */
  
  //public static final int colorWheelSpinButton = Constants.Buttons;

  // Misc
  public static final boolean SOLENOID_DOWN = false;
  public static final boolean SOLENOID_UP = true;
  
  public static final Gains SHOOTER_CONFIG = new Gains(1023.0/20660.0,0.5,0.0,0.0,300,1.00);//0.001,5.0,1023.0/20660.0,300, 1.00);
  
  // Drive Train Variables
  public static final double driveTrainTurnShifter = 0.65;
  public static final double driveTrainCThrottle = 1;
  public static final double driveTrainCTurn = 5;
  public static final double driveTrainDeadSpaceThrottle = 0.2;
  public static final double driveTrainDeadSpaceTurn = 0.45;
  public static final double driveTrainThrottleShifter = 0.6;
  public static final double driveTrainMinimumThreshold = 0.005;


  public static class Buttons {

    public static int
    A = 1,
    B = 2,
    X = 3,
    Y = 4,
    L = 5,
    R = 6,
    BACK = 7,
    START = 8,
    L_STICK = 9,
    R_STICK = 10,
    TOTAL_BUTTONS = 10;
}
  /*
  Controller axis  (including rTrigger and lTrigger),
  access with OI.Axis.AXISNAME
 */
  public static class Axis {

      public static int
      LX = 0,
      LY = 1,
      LTrigger = 2,
      RTrigger = 3,
      RX = 4,
      RY = 5,
      AXIS_TOTAL = 6;

  }


}