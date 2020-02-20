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

public class ColorWheel implements Subsystem {
    VictorSPX wheelSpinner;
    Solenoid wheelSolenoid;
    private final boolean down = false;
    private final boolean up = true;
    XboxController colorCtrl;
    
    //constructor
    public ColorWheel(XboxController controller){
        colorCtrl = controller;
        initCANMotors();
    }
    private void initCANMotors(){
        this.wheelSpinner = new VictorSPX(Constants.wheelSpinner);
         
 
        //enable braking mode
        wheelSpinner.setNeutralMode(NeutralMode.Brake);
    }
    public void resetEncoders() {

        wheelSpinner.setSelectedSensorPosition(0);
    }
    private void teleopDefaultColorWheel() {
        if(colorCtrl.getYButtonPressed())
        {
            wheelSolenoid.set(!wheelSolenoid.get());
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