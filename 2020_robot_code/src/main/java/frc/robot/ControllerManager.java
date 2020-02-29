package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class ControllerManager {

    XboxController driverController;
    XboxController operatorController;

    public ControllerManager() {
        this.driverController = new XboxController(0);
        this.operatorController = new XboxController(1);
    }

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
    }

    public boolean getButtonReleasedDriver(int button) {
        return this.driverController.getRawButtonReleased(button);
    }

    public boolean getButtonReleasedOperator(int button) {
        return this.operatorController.getRawButtonReleased(button);
    }

    public double getAxisDriver(int axis) {
        return this.driverController.getRawAxis(axis);
    }

    public double getAxisOperator(int axis) {
        return this.operatorController.getRawAxis(axis);
    }

    public double getIntakeSpeed() {
        return this.driverController.getRawAxis(Constants.intakeAxisForward)-
            this.driverController.getRawAxis(Constants.intakeAxisBackward);
    }

    public boolean getIntakeEnabled() {
        return this.getIntakeSpeed() != 0.0;
    }

}