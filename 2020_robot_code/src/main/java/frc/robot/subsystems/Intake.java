package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants;
import frc.robot.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;

public class Intake implements Subsystem {
    Solenoid intakeSolenoid;
    VictorSPX intakeMotor;
    XboxController intakeCtrl;

    public Intake(XboxController controller){
        this.intakeCtrl = controller;
    }

    void defaultTeleop(){
        //System.out.println(intakeCtrl.getBButtonPressed());
        //System.out.println(intakeCtrl.getRawButtonPressed(Constants.intakeCtrlButtonToggle));
        if(intakeCtrl.getRawButtonPressed(Constants.intakeCtrlButtonToggle)){
            //System.out.println(!intakeSolenoid.get());
            intakeSolenoid.set(!intakeSolenoid.get());
        }
        intakeMotor.set(ControlMode.PercentOutput, intakeCtrl.getRawAxis(Constants.intakeAxisForward)-intakeCtrl.getRawAxis(Constants.intakeAxisBackward));
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