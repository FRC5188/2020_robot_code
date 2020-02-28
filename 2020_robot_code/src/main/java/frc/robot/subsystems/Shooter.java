package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants;
import frc.robot.Subsystem;
import frc.robot.Constants.Buttons;
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
    double shooterSpeedError = 0.05;

    Solenoid lifterSolenoid;

    XboxController shooterCtrl;

    // constructor
    public Shooter(XboxController controller) {
        this.shooterCtrl = controller;
        lifterSolenoid = new Solenoid(Constants.lifterSolenoid);
        lifterSolenoid.set(Constants.SOLENOID_DOWN);
        this.initCANMotors();
        this.initCurrentLimit();

    }

    private void initCANMotors() {
        // TODO: Test if will fail with motor not connected?
        this.shooterTop = new WPI_TalonFX(Constants.shooterTopFalcon);
        this.shooterBottom = new WPI_TalonFX(Constants.shooterBottomFalcon);
        this.beltTop = new TalonSRX(Constants.beltTop775Pro);
        this.beltBottom = new TalonSRX(Constants.beltBottom775Pro);
        this.shooterTop.setInverted(InvertType.InvertMotorOutput);
        //enable braking mode
        shooterTop.setNeutralMode(NeutralMode.Coast);
        shooterBottom.setNeutralMode(NeutralMode.Coast);
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

    /*

        TODO: 
        Add secondary controller
        Seperate belt and shooter
        Remove temp solution for ^
        Interface with other subsystems better?
        Make solenoid less destructive?






    */

    private void teleopDefaultShooter() {
        // Values need to be set in Network Tables, but defaults are in Constants
        shooterSpeed = Constants.shooterShooterSpeed;//inst.getEntry("Shooter_Speed").getNumber(Constants.shooterShooterSpeed).doubleValue();
        beltSpeed = Constants.shooterBeltSpeed;//.getEntry("Belt_Speed").getNumber(Constants.shooterBeltSpeed).doubleValue();
        /*
         If you change something in here, see if it should be changed in this.autonomousShoot also.
        */
        //System.out.println(shooterSpeed + " " + beltSpeed);
        if(shooterCtrl.getRawButton(Constants.shooterCtrlShoot))
        {
            
            //System.out.println("Moving");
            shooterBottom.set(ControlMode.Velocity, shooterSpeed);
            if((shooterSpeed + shooterSpeedError) > shooterBottom.getSelectedSensorVelocity() & (shooterSpeed - shooterSpeedError) < shooterBottom.getSelectedSensorVelocity()){
                beltBottom.set(ControlMode.PercentOutput, beltSpeed);
            } else {
                beltBottom.set(ControlMode.PercentOutput, 0.0);
            }
        } else 
        if(shooterCtrl.getRawButton(Constants.shooterCtrlReverse) || contrManager.getIntakeBeltSpeed())
        {
            //if(frontLBsensor.get() & !backLBsensor.get()){
                // TODO: This is temp. Make a perm solution
                if(shooterCtrl.getRawButton(Buttons.START)) {
                    beltBottom.set(ControlMode.PercentOutput, -beltSpeed);
                } else {
                    beltBottom.set(ControlMode.PercentOutput, 0.0);
                }
                shooterBottom.set(ControlMode.PercentOutput, -Constants.intakeShooterSpeed);
            //}
        } else {
            shooterBottom.set(ControlMode.PercentOutput, 0.0);
            beltBottom.set(ControlMode.PercentOutput, 0.0);
        }
        if(shooterCtrl.getRawButtonPressed(Constants.shooterCtrlLiftToggle))
        {
            lifterSolenoid.set(!lifterSolenoid.get());
        }
    }
    
	public void autonomousShoot(boolean runShooter, boolean reverserShooter) {
        shooterSpeed = inst.getEntry("Shooter_Speed").getNumber(Constants.AUTO_SHOOTER_SHOOTER_SPEED).doubleValue();
        beltSpeed = inst.getEntry("Belt_Speed").getNumber(Constants.AUTO_SHOOTER_BELT_SPEED).doubleValue();
        if(runShooter)
        {
            shooterBottom.set(ControlMode.PercentOutput, shooterSpeed);
            //if((shooterSpeed + shooterSpeedError) > shooterBottom.getSelectedSensorVelocity() & (shooterSpeed - shooterSpeedError) < shooterBottom.getSelectedSensorVelocity()){
            beltBottom.set(ControlMode.PercentOutput, beltSpeed);
            //}
        } else if(shooterCtrl.getRawButton(Constants.shooterCtrlReverse))
        {
            // TODO: Does auto mode need to test sensors?
            //if(frontLBsensor.get() & !backLBsensor.get()){
            beltBottom.set(ControlMode.PercentOutput, -beltSpeed);
            shooterBottom.set(ControlMode.PercentOutput, -shooterSpeed);
            //}
            //intakeCtrl.getRawAxis(Constants.intakeAxisForward)-intakeCtrl.getRawAxis(Constants.intakeAxisBackward)
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
        this.initCANMotors();
        this.initCurrentLimit();
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