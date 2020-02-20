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

public class Intake implements Subsystem {
    Solenoid leftIntakeSolenoid;
    Solenoid rightIntakeSolenoid;
    VictorSPX intakeMotor;
    XboxController intakeCtrl;

    public Intake(XboxController controller){
        this.intakeCtrl = controller;
    }

    void defaultTeleop(){
        if(intakeCtrl.getBButtonPressed()){
            leftIntakeSolenoid.set(!leftIntakeSolenoid.get());
            rightIntakeSolenoid.set(!rightIntakeSolenoid.get());
        }
        intakeMotor.set(ControlMode.PercentOutput, intakeCtrl.getRawAxis(Constants.Axis.LTrigger)-intakeCtrl.getRawAxis(Constants.Axis.RTrigger));
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub
        leftIntakeSolenoid = new Solenoid(Constants.leftIntakeSolenoid);
        rightIntakeSolenoid = new Solenoid(Constants.rightIntakeSolenoid);
        intakeMotor = new VictorSPX(Constants.intakeMotor);
    }

    @Override
    public void initShuffle() {
        // TODO Auto-generated method stub

    }

    @Override
    public void operate() {
        // TODO Auto-generated method stub

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