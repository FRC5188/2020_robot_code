package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.utils.InputButton;

public class ControllerManager {

    XboxController driverController;
    XboxController operatorController;
    XboxController[] controllers;
    boolean[] axesPressed;

    public ControllerManager() {
        this.driverController = new XboxController(0);
        this.operatorController = new XboxController(1);
        this.controllers = new XboxController[] {
            this.driverController,
            this.operatorController
        };
        axesPressed = new boolean[Constants.Axis.AXIS_TOTAL];
    }

    public boolean getButton(InputButton button) {
        if(button.isList) {
            for(InputButton b: button.buttonList) {
                if(this.getButton(b))
                    return true;
            }
            return false;
        }
        if(button.buttonOrAxis == 0)
            return controllers[button.controller].getRawButton(button.id);
        else
            return controllers[button.controller].getRawAxis(button.id) > 0.5;
    }

    /*
    public boolean getButtonDriver(int button) {
        return this.driverController.getRawButton(button);
    }

    public boolean getButtonOperator(int button) {
        return this.operatorController.getRawButton(button);
    }

    public boolean getButtonPressedDriver(int button) {
        return this.driverController.getRawButtonPressed(button);
    }

    public boolean getButtonPressedOperator(int button) {
        return this.operatorController.getRawButtonPressed(button);
    }*/

    public boolean getButtonPressed(InputButton button) {
        if(button.isList) {
            for(InputButton b: button.buttonList) {
                if(this.getButtonPressed(b))
                    return true;
            }
            return false;
        }
        if(button.buttonOrAxis == 0)
            return controllers[button.controller].getRawButtonPressed(button.id);
        else {
            if(controllers[button.controller].getRawAxis(button.id) < 0.5) {
                if(axesPressed[button.id] != false)
                    axesPressed[button.id] = false;
                return false;
            }
            if(axesPressed[button.id] == true)
                return false;
            axesPressed[button.id] = true;
            return false;
        }
    }

    /*
    public boolean getButtonReleasedDriver(int button) {
        return this.driverController.getRawButtonReleased(button);
    }

    public boolean getButtonReleasedOperator(int button) {
        return this.operatorController.getRawButtonReleased(button);
    }*/

    public boolean getButtonReleased(InputButton button) {
        if(button.isList) {
            for(InputButton b: button.buttonList) {
                if(this.getButtonReleased(b))
                    return true;
            }
            return false;
        }
        if(button.buttonOrAxis == 0)
            return controllers[button.controller].getRawButtonReleased(button.id);
        else {
            if(controllers[button.controller].getRawAxis(button.id) > 0.5) {
                if(axesPressed[button.id] != true)
                    axesPressed[button.id] = true;
                return false;
            }
            if(axesPressed[button.id] == false)
                return false;
            axesPressed[button.id] = false;
            return true;
        }
    }

    /*
    public double getAxisDriver(int axis) {
        return this.driverController.getRawAxis(axis);
    }

    public double getAxisOperator(int axis) {
        return this.operatorController.getRawAxis(axis);
    }*/

    public double getAxis(InputButton button) {
        if(button.isList) {
            for(InputButton b: button.buttonList) {
                double val = this.getAxis(b);
                if(val != 0.0)
                    return val;
            }
            return 0.0;
        }
        if(button.buttonOrAxis == 0)
            return controllers[button.controller].getRawButton(button.id) ? 1.0 : 0.0;
        else {
            return controllers[button.controller].getRawAxis(button.id);
        }
    }

    public double getIntakeSpeed() {
        if(getButton(Constants.intakeAxisBackward))
            return -1;
        return getAxis(Constants.intakeAxisForward);
    }
    /*
    public double getIntakeSpeed() {
        if(this.operatorController.getRawButton(Constants.intakeAxisBackward))
            return -1;
        //this.operatorController.getRawAxis(Constants.intakeAxisBackward))
        return this.operatorController.getRawAxis(Constants.intakeAxisForward);
    }*/

    public boolean getIntakeEnabled() {
        return this.getIntakeSpeed() != 0.0;
    }

}