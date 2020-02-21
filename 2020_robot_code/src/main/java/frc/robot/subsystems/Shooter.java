package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants;
import frc.robot.Subsystem;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;

public class Shooter implements Subsystem {

    NetworkTableInstance inst;

    WPI_TalonFX shooterTop;
    WPI_TalonFX shooterBottom;
    TalonSRX beltTop;
    TalonSRX beltBottom;

    DigitalInput frontLBsensor;
    DigitalInput backLBsensor;

    double shooterSpeed;
    double beltSpeed;
    double shooterSpeedError;

    Solenoid lifterSolenoid;

    private final boolean down = false;
    private final boolean up = true;

    XboxController shooterCtrl;

    // constructor
    public Shooter(XboxController controller) {
        this.shooterCtrl = controller;
        lifterSolenoid = new Solenoid(Constants.lifterSolenoid);
        lifterSolenoid.set(down);
        this.initCANMotors();
        this.initCurrentLimit();

    }

    private void initCANMotors() {
        // TODO: Test if will fail with motor not connected?
        this.shooterTop = new WPI_TalonFX(Constants.shooterTopFalcon);
        this.shooterBottom = new WPI_TalonFX(Constants.shooterBottomFalcon);
        this.beltTop = new TalonSRX(Constants.beltTop775Pro);
        this.beltBottom = new TalonSRX(Constants.beltBottom775Pro);

        //enable braking mode
        shooterTop.setNeutralMode(NeutralMode.Brake);
        shooterBottom.setNeutralMode(NeutralMode.Brake);
        beltTop.setNeutralMode(NeutralMode.Brake);
        beltBottom.setNeutralMode(NeutralMode.Brake);

        this.shooterTop.follow(shooterBottom);
        this.beltTop.follow(beltBottom);
    }
    private void initCurrentLimit(){
        //create current config, new for 2020
        //it is a little long........ :(
        SupplyCurrentLimitConfiguration supplyCurrentConfig;
        supplyCurrentConfig = new SupplyCurrentLimitConfiguration(
            true, Constants.SupplyCurrentLimit, Constants.SupplyTriggerCurremt, Constants.SupplyCurrentDuration
        );

        //apply current limits
        shooterTop.configSupplyCurrentLimit(supplyCurrentConfig);
        shooterBottom.configSupplyCurrentLimit(supplyCurrentConfig);
        
        //beltTop.configSupplyCurrentLimit(supplyCurrentConfig);
        //beltBottom.configSupplyCurrentLimit(supplyCurrentConfig);

    }

    private void teleopDefaultShooter() {
        shooterSpeed = inst.getEntry("Shooter_Speed").getNumber(0.0f).doubleValue();
        beltSpeed = inst.getEntry("Belt_Speed").getNumber(0.0f).doubleValue();

        if(shooterCtrl.getRawButton(Constants.Buttons.R))
        {
            shooterBottom.set(ControlMode.Velocity, shooterSpeed);
            if((shooterSpeed + shooterSpeedError) > shooterBottom.getSelectedSensorVelocity() & (shooterSpeed - shooterSpeedError) < shooterBottom.getSelectedSensorVelocity()){
                beltBottom.set(ControlMode.PercentOutput, beltSpeed);
            }
        }
        if(shooterCtrl.getRawButton(Constants.Buttons.L))
        {

            if(frontLBsensor.get() & !backLBsensor.get()){
                beltBottom.set(ControlMode.PercentOutput, -beltSpeed);
            }

        }
        if(shooterCtrl.getXButtonPressed())
        {
            lifterSolenoid.set(!lifterSolenoid.get());
        }
    }

    public void resetEncoders() {

        beltTop.setSelectedSensorPosition(0);
        beltBottom.setSelectedSensorPosition(0);
    }
    public double getTopEncoderTicks(){
        return this.beltTop.getSelectedSensorPosition();
    }
    
    public double getBottomEncoderTicks(){
        return this.beltBottom.getSelectedSensorPosition();
    }

    public double getTopEncoderInches(){
        return this.beltTop.getSelectedSensorPosition()/Constants.ENCODER_TICKS_PER_INCH;
    }

    public double getBottomEncoderInches(){
        return this.beltBottom.getSelectedSensorPosition()/Constants.ENCODER_TICKS_PER_INCH;
    }
    
    @Override
    public void init() {
        frontLBsensor = new DigitalInput(Constants.frontLBsensor);
        backLBsensor = new DigitalInput(Constants.backLBsensor);
    }

    @Override
    public void initShuffle() {
        this.inst = NetworkTableInstance.getDefault();
        inst.getEntry("Shooter_Speed").setNumber(0.0f);
        inst.getEntry("Belt_Speed").setNumber(0.0f);
    }

    @Override
    public void operate() {
        teleopDefaultShooter();
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