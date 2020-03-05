package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import frc.robot.Constants;
import frc.robot.ControllerManager;
import frc.robot.Robot;
import frc.robot.Subsystem;
import frc.robot.utils.InputButton;
import edu.wpi.first.wpilibj.Solenoid;

public class Climber implements Subsystem {
    VictorSPX climber;
    Solenoid climberSolenoid;
    
    ControllerManager ctrlManager;
    Robot robot;

    public Climber(Robot robot){
        this.robot = robot;
        this.ctrlManager = Robot.getControllerManager();
        climberSolenoid = new Solenoid(Constants.climbSolenoid);
        climberSolenoid.set(Constants.SOLENOID_DOWN);
        initCANMotors();
    }
    private void initCANMotors(){
        this.climber = new VictorSPX(Constants.climberMotor);
         
        climber.setNeutralMode(NeutralMode.Brake);
    }
    public void resetEncoders() {

        climber.setSelectedSensorPosition(0);
    }
    private void teleopDefaultClimber() {
        if(ctrlManager.getButtonPressed(Constants.climberButtonToggle) && this.robot.getIntake().intakeSolenoid.get())
        {
            climberSolenoid.set(!climberSolenoid.get());
        }

        if (((climberSolenoid.get()) && ctrlManager.getAxis(Constants.climberCtrlAxis) < 0) || (ctrlManager.getButton(InputButton.OPERATOR_START))){
            climber.set(ControlMode.PercentOutput, ctrlManager.getAxis(Constants.climberCtrlAxis));
        } else{
            climber.set(ControlMode.PercentOutput, 0);
        }
    }

    public void init() {

    }

    @Override
    public void initShuffle() {

    }

    @Override
    public void operate() {

        this.teleopDefaultClimber();

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
