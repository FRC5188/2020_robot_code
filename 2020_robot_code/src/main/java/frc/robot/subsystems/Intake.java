package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import frc.robot.Constants;
import frc.robot.ControllerManager;
import frc.robot.Robot;
import frc.robot.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Intake implements Subsystem {
    DoubleSolenoid intakeSolenoid;
    VictorSPX intakeMotor;
    ControllerManager ctrlManager;
    Robot robot;

    public Intake(Robot robot) {
        this.robot = robot;
        this.ctrlManager = Robot.getControllerManager();
    }

    void defaultTeleop() {
        //if shooter is up, dont
        //if(this.robot.getShooter().isSolenoidUp()) return;
        if(ctrlManager.getButtonPressed(Constants.intakeCtrlButtonToggle)){
            if(this.getIntakeSolenoidUp() && this.robot.getShooter().isSolenoidUp())
                this.robot.getShooter().toggleSolenoid();
            this.toggleSolenoid();
        }
        if(!this.getIntakeSolenoidUp())
            intakeMotor.set(ControlMode.PercentOutput, ctrlManager.getIntakeSpeed());
    }

	public void autonomousIntake(boolean runIntake) {
        if(runIntake && !this.getIntakeSolenoidUp() && !this.robot.getShooter().isSolenoidUp())
            intakeMotor.set(ControlMode.PercentOutput, Constants.TASK_INTAKE_SPEED);
        else
            intakeMotor.set(ControlMode.PercentOutput, 0.0);
	}

    public boolean getIntakeSolenoidUp() {
        return this.intakeSolenoid.get() == Value.kReverse;
    }
    public void toggleSolenoid() {
        if(this.intakeSolenoid.get() == Value.kForward){
            this.intakeSolenoid.set(Value.kReverse);
        } else {
            this.intakeSolenoid.set(Value.kForward);
        }
    }

    @Override
    public void init() {
        intakeSolenoid = new DoubleSolenoid(Constants.intakeSolenoid, Constants.intakeSolenoid2);
        intakeMotor = new VictorSPX(Constants.intakeMotor);
    }

    @Override
    public void initShuffle() {

    }

    @Override
    public void operate() {
        defaultTeleop();
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