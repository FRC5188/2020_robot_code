package frc.robot;

public class Constants{
    
  //can ids
  public static final int leftFalcon1 = 0;
  public static final int leftFalcon2 = 1;
  public static final int rightFalcon1 = 2;
  public static final int rightFalcon2 = 3;

  public static final int shooterTopFalcon = 4;
  public static final int shooterBottomFalcon = 5;
  public static final int beltTop775Pro = 6;
  public static final int beltBottom775Pro = 7;
  public static final int intakeMotor = 9;

  public static final int wheelSpinner = 8;
  // Motor in climber class was set to wheelSpinner, (which was same as color wheel)
  // Don't know for sure what you want, but I knew it was wrong.
  public static final int climberMotor = 10;

  public static final int driverPort = 0;

  public static final int frontLBsensor = 0;
  public static final int backLBsensor = 1;

  //solenoid channel 
  public static final int intakeSolenoid = 0;
  public static final int lifterSolenoid = 1;
  public static final int colorWheelSolenoid = 2;
  public static final int climbSolenoid = 3;

  //current limiting params
  public static final int SupplyTriggerCurremt = 25; // don't activate current limit until current exceeds 30 A...
  public static final int SupplyCurrentDuration = 50; //... for at least 50 ms
  public static final int SupplyCurrentLimit = 20; // once current-limiting is activated, hold at 20A

  // Shooter Teleop Variables
  public static final double shooterShooterSpeed = 0.7;
  public static final double shooterBeltSpeed = 0.5;

  public static final double ENCODER_TICKS_PER_INCH  = 941.1;
  public static final double openRampDuration = 0.5; //seconds from zero to full throttle 

  // Autonomous Task Move Variables
  public static final double TASK_MOVE_PID_P = 1.0;
  public static final double TASK_MOVE_PID_I = 0.0;
  public static final double TASK_MOVE_PID_D = 0.0;
  public static final double TASK_MOVE_DEFAULT_TOLERANCE = 1.0;
  public static final int TASK_MOVE_DEFAULT_TIME = 3;

  // Autonomous Task Turn Variables
  public static final double TASK_TURN_PID_P = 1.0;
  public static final double TASK_TURN_PID_I = 0.0;
  public static final double TASK_TURN_PID_D = 0.0;
  public static final double TASK_TURN_DEFAULT_TOLERANCE = 0;
  public static final int TASK_TURN_DEFAULT_TIME = 0;

  // Autonomous Task Shoot Variables
  public static final int TASK_SHOOT_REVERSE_DEFAULT_TICKTIME_RUN = 30;
  public static final int TASK_SHOOT_RUN_DEFAULT_TICKTIME_RUN = 30;

  // Subsystem Variables while in Autonomous
  public static final double AUTONOMOUS_MAX_THROTTLE = 0.5;
  public static final double AUTONOMOUS_MAX_TURN = 0.5;
  public static final double AUTO_SHOOTER_SHOOTER_SPEED = Constants.shooterShooterSpeed; // default to 
  public static final double AUTO_SHOOTER_BELT_SPEED = Constants.shooterBeltSpeed; // teleop's defaults

  // Teleop button controls
  public static final int colorWheelButton = Constants.Buttons.Y;
  public static final int climberCtrlAxis = Constants.Axis.RY;
  public static final int shooterCtrlShoot = Constants.Buttons.R;
  public static final int shooterCtrlReverse = Constants.Buttons.L;
  public static final int intakeCtrlButtonToggle = Constants.Buttons.B;
  public static final int shooterCtrlLiftToggle = Constants.Buttons.X;

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