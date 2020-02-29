package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.Constants;
import frc.robot.ControllerManager;
import frc.robot.Robot;
import frc.robot.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;

public class ColorWheel implements Subsystem {
    TalonSRX wheelSpinner;
    Solenoid wheelSolenoid;
    ControllerManager ctrlManager;
    Robot robot;
    
    //constructor
    public ColorWheel(Robot robot){
        this.robot = robot;
        this.ctrlManager = Robot.getControllerManager();
        this.wheelSolenoid = new Solenoid(Constants.colorWheelSolenoid);
        initCANMotors();
    }
    private void initCANMotors(){
        this.wheelSpinner = new TalonSRX(Constants.wheelSpinner);
        
        //enable braking mode
        wheelSpinner.setNeutralMode(NeutralMode.Brake);
    }
    public void resetEncoders() {
        wheelSpinner.setSelectedSensorPosition(0);
    }
    private void teleopDefaultColorWheel() {
        if(ctrlManager.getButtonPressedOperator(Constants.colorWheelPneumaticButton))
        {
            wheelSolenoid.set(!wheelSolenoid.get());
        }
        
        if(ctrlManager.getButtonOperator(Constants.colorWheelSpinButton)) {
            this.wheelSpinner.set(ControlMode.PercentOutput, 0.5);
        } else {
            this.wheelSpinner.set(ControlMode.PercentOutput, 0.0);
        }
        
    }
    public void init() {

    }

    @Override
    public void initShuffle() {

    }

    @Override
    public void operate() {

        this.teleopDefaultColorWheel();

    }

    @Override
    public void test() {
        
    }

    @Override
    public void updateShuffle() {
        
    }

    @Override
    public void kill() {
        
    }

}