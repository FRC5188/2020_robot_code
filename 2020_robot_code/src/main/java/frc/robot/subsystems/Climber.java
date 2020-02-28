package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants;
import frc.robot.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;

public class Climber implements Subsystem {
    VictorSPX climber;
    Solenoid climberSolenoid;
    /*
    Available in solenoid.get()?
    private final boolean down = false;
    private final boolean up = true;
    */
    XboxController climberCtrl;

    public Climber(XboxController controller){
        climberCtrl = controller;
        climberSolenoid = new Solenoid(Constants.climbSolenoid);
        climberSolenoid.set(Constants.SOLENOID_DOWN);
        initCANMotors();
    }
    private void initCANMotors(){
        this.climber = new VictorSPX(Constants.climberMotor);
         
 
        //enable braking mode
        climber.setNeutralMode(NeutralMode.Brake);
    }
    public void resetEncoders() {

        climber.setSelectedSensorPosition(0);
    }
    private void teleopDefaultClimber() {

        if(climberCtrl.getRawButtonPressed(Constants.climberButtonToggle))
        {
            climberSolenoid.set(!climberSolenoid.get());
        }

        if (climberCtrl.getRawAxis(Constants.climberCtrlAxis) < 0){
            climber.set(ControlMode.PercentOutput, climberCtrl.getRawAxis(Constants.climberCtrlAxis));
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
