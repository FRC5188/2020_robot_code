package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.Constants;
import frc.robot.Subsystem;
import frc.robot.Constants.Axis;
import edu.wpi.first.wpilibj.Solenoid;

public class Climber implements Subsystem {
    VictorSPX climber;
    Solenoid climberSolenoid;
    private final boolean down = false;
    private final boolean up = true;
    XboxController climberCtrl;

    public Climber(XboxController controller){
        climberCtrl = controller;
        climberSolenoid = new Solenoid(Constants.climbSolenoid);
        initCANMotors();
    }
    private void initCANMotors(){
        this.climber = new VictorSPX(Constants.wheelSpinner);
         
 
        //enable braking mode
        climber.setNeutralMode(NeutralMode.Brake);
    }
    public void resetEncoders() {

        climber.setSelectedSensorPosition(0);
    }
    private void teleopDefaultClimber() {

        if(climberCtrl.getAButtonPressed())
        {
            climberSolenoid.set(!climberSolenoid.get());
        }

        if (climberCtrl.getRawAxis(Constants.Axis.RY) > 0){
            climber.set(ControlMode.PercentOutput, climberCtrl.getRawAxis(Constants.Axis.RY));
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
