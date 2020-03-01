package frc.robot.utils;

public class InputButton {
     
    // Not an enum so we can instantiate it outside of here 
    // (for multiple buttons to control same thing, 
    // check InputButton(InputButton... buttons) constructor)
    public static final InputButton DRIVER_A = new InputButton(0,0,1);
    public static final InputButton DRIVER_B = new InputButton(0,0,2);
    public static final InputButton DRIVER_X = new InputButton(0,0,3);
    public static final InputButton DRIVER_Y = new InputButton(0,0,4);
    public static final InputButton DRIVER_L = new InputButton(0,0,5);
    public static final InputButton DRIVER_R = new InputButton(0,0,6);
    public static final InputButton DRIVER_BACK = new InputButton(0,0,7);
    public static final InputButton DRIVER_START = new InputButton(0,0,8);
    public static final InputButton DRIVER_L_STICK = new InputButton(0,0,9);
    public static final InputButton DRIVER_R_STICK = new InputButton(0,0,10);
    public static final InputButton DRIVER_LX = new InputButton(0,1,0);
    public static final InputButton DRIVER_LY = new InputButton(0,1,1);
    public static final InputButton DRIVER_LTrigger = new InputButton(0,1,2);
    public static final InputButton DRIVER_RTrigger = new InputButton(0,1,3);
    public static final InputButton DRIVER_RX = new InputButton(0,1,4);
    public static final InputButton DRIVER_RY = new InputButton(0,1,5);

    public static final InputButton OPERATOR_A = new InputButton(1,0,1);
    public static final InputButton OPERATOR_B = new InputButton(1,0,2);
    public static final InputButton OPERATOR_X = new InputButton(1,0,3);
    public static final InputButton OPERATOR_Y = new InputButton(1,0,4);
    public static final InputButton OPERATOR_L = new InputButton(1,0,5);
    public static final InputButton OPERATOR_R = new InputButton(1,0,6);
    public static final InputButton OPERATOR_BACK = new InputButton(1,0,7);
    public static final InputButton OPERATOR_START = new InputButton(1,0,8);
    public static final InputButton OPERATOR_L_STICK = new InputButton(1,0,9);
    public static final InputButton OPERATOR_R_STICK = new InputButton(1,0,10);
    public static final InputButton OPERATOR_LX = new InputButton(1,1,0);
    public static final InputButton OPERATOR_LY = new InputButton(1,1,1);
    public static final InputButton OPERATOR_LTrigger = new InputButton(1,1,2);
    public static final InputButton OPERATOR_RTrigger = new InputButton(1,1,3);
    public static final InputButton OPERATOR_RX = new InputButton(1,1,4);
    public static final InputButton OPERATOR_RY = new InputButton(1,1,5);

    public boolean isList;
    public int controller;
    public int buttonOrAxis;
    public int id;
    public InputButton[] buttonList;
    private InputButton(int controller, int buttonOrAxis,int id) {
        this.isList = false;
        // controller : 0 = driver, 1 = operator
        // buttonOrAxis : 0 = button, 1 = axis
        // id : Just the id for XboxController.getRawButton(id) if button or XboxController.getRawAxis(id) if axis
        this.controller = controller;
        this.buttonOrAxis = buttonOrAxis;
        this.id = id;
    }

    public InputButton(InputButton... buttons) {
        this.isList = true;
        this.buttonList = buttons;
    }

  }