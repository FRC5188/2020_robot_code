package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import frc.robot.Constants;
import frc.robot.ControllerManager;
import frc.robot.Robot;
import frc.robot.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;

public class Intake implements Subsystem {
    Solenoid intakeSolenoid;
    VictorSPX intakeMotor;
    ControllerManager ctrlManager;
    Robot robot;

    public Intake(Robot robot){
        this.robot = robot;
        this.ctrlManager = Robot.getControllerManager();
    }

    void defaultTeleop() {
        if(this.robot.getShooter().lifterSolenoid.get()) return;
        if(ctrlManager.getButtonPressed(Constants.intakeCtrlButtonToggle)){
            intakeSolenoid.set(!intakeSolenoid.get());
        }
        if(!intakeSolenoid.get())
            intakeMotor.set(ControlMode.PercentOutput, ctrlManager.getIntakeSpeed());
    }

    @Override
    public void init() {
        intakeSolenoid = new Solenoid(Constants.intakeSolenoid);
        intakeMotor = new VictorSPX(Constants.intakeMotor);
    }

    @Override
    public void initShuffle() {
        // TODO Auto-generated method stub

    }

    @Override
    public void operate() {
        // TODO Auto-generated method stub
        defaultTeleop();
    }

    @Override
    public void test() {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateShuffle() {
        // TODO Auto-generated method stub

    }

    @Override
    public void kill() {
        // TODO Auto-generated method stub

    }

}