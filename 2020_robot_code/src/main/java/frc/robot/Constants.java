package frc.robot;

public class Constants{
    
  //can ids
  public static final int leftFalcon1 = 0;
  public static final int leftFalcon2 = 1;
  public static final int rightFalcon1 = 2;
  public static final int rightFalcon2 = 3;

  public static final int driverPort = 0;

  //current limiting params
  public static final int SupplyTriggerCurremt = 25; // don't activate current limit until current exceeds 30 A...
  public static final int SupplyCurrentDuration = 50; //... for at least 50 ms
  public static final int SupplyCurrentLimit = 20; // once current-limiting is activated, hold at 20A
  
  public static final double ENCODER_TICKS_PER_INCH  = 941.1;
  public static final double openRampDuration = 0.5; //seconds from zero to full throttle 
  
  public static final double TASK_MOVE_PID_P = 1.0;
  public static final double TASK_MOVE_PID_I = 0.0;
  public static final double TASK_MOVE_PID_D = 0.0;
  public static final double TASK_MOVE_DEFAULT_TOLERANCE = 1.0;
  public static final int TASK_MOVE_DEFAULT_TIME = 3;
  
  public static final double TASK_TURN_PID_P = 1.0;
  public static final double TASK_TURN_PID_I = 0.0;
  public static final double TASK_TURN_PID_D = 0.0;
  public static final double TASK_TURN_DEFAULT_TOLERANCE = 0;
  public static final int TASK_TURN_DEFAULT_TIME = 0;

  public static final double AUTONOMOUS_MAX_THROTTLE = 0.5;
  public static final double AUTONOMOUS_MAX_TURN = 0.5;
  
  
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