package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Constants;
import frc.robot.ControllerManager;
import frc.robot.Robot;
import frc.robot.Subsystem;
import frc.robot.utils.Gains;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;

public class Shooter implements Subsystem {

    NetworkTableInstance inst;

    WPI_TalonFX shooterTop;
    WPI_TalonFX shooterBottom;
    VictorSPX beltTop;
    TalonSRX beltBottom;

    DigitalInput frontLBsensor;
    DigitalInput backLBsensor;

    double shooterSpeed;
    double beltSpeed;
    double shooterSpeedError = 300.0;
    boolean beltEnabled = false;

    private int frontLineBreakTimer = 0;

    Solenoid lifterSolenoid;

    ControllerManager ctrlManager;
    Robot robot;

    // constructor
    public Shooter(Robot robot) {
        this.robot = robot;
        this.ctrlManager = Robot.getControllerManager();
        lifterSolenoid = new Solenoid(Constants.lifterSolenoid);
        lifterSolenoid.set(Constants.SOLENOID_DOWN);
        this.initCANMotors();
        this.initCurrentLimit();

    }

    private void initCANMotors() {
        // TODO: Test if will fail with motor not connected?
        this.shooterTop = new WPI_TalonFX(Constants.shooterTopFalcon);
        this.shooterBottom = new WPI_TalonFX(Constants.shooterBottomFalcon);
        this.beltTop = new VictorSPX(Constants.beltTop775Pro);
        this.beltBottom = new TalonSRX(Constants.beltBottom775Pro);
        this.shooterBottom.setInverted(InvertType.InvertMotorOutput);
        //enable braking mode
        beltTop.setNeutralMode(NeutralMode.Brake);
        beltBottom.setNeutralMode(NeutralMode.Brake);
        this.beltTop.follow(beltBottom);
        beltTop.setInverted(InvertType.InvertMotorOutput);

        int idx = 0;
        int timeout = 30;
        
        this.shooterBottom.configFactoryDefault();

        this.shooterBottom.configNeutralDeadband(0.001);
        this.shooterBottom.configSelectedFeedbackSensor(
            TalonFXFeedbackDevice.IntegratedSensor,idx,timeout);

        this.shooterBottom.configNominalOutputForward(0.0, timeout);
        this.shooterBottom.configNominalOutputReverse(0.0, timeout);
        this.shooterBottom.configPeakOutputForward(1.0, timeout);
        this.shooterBottom.configPeakOutputReverse(-1.0, timeout);

        Gains gains = Constants.SHOOTER_CONFIG;
        this.shooterBottom.config_kF(idx, gains.kF, timeout);
        this.shooterBottom.config_kP(idx, gains.kP, timeout);
        this.shooterBottom.config_kI(idx, gains.kI, timeout);
        this.shooterBottom.config_kD(idx, gains.kD, timeout);

        this.shooterTop.configFactoryDefault();

        this.shooterTop.configNeutralDeadband(0.001);
        this.shooterTop.configSelectedFeedbackSensor(
            TalonFXFeedbackDevice.IntegratedSensor,idx,timeout);

        this.shooterTop.configNominalOutputForward(0.0, timeout);
        this.shooterTop.configNominalOutputReverse(0.0, timeout);
        this.shooterTop.configPeakOutputForward(1.0, timeout);
        this.shooterTop.configPeakOutputReverse(-1.0, timeout);

        this.shooterTop.config_kF(idx, gains.kF, timeout);
        this.shooterTop.config_kP(idx, gains.kP, timeout);
        this.shooterTop.config_kI(idx, gains.kI, timeout);
        this.shooterTop.config_kD(idx, gains.kD, timeout);

        shooterTop.setNeutralMode(NeutralMode.Coast);
        shooterBottom.setNeutralMode(NeutralMode.Coast);
        
        this.shooterTop.follow(shooterBottom);

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
        
        shooterBottom.configOpenloopRamp(Constants.shooterOpenRampDuration);
        shooterTop.configOpenloopRamp(Constants.shooterOpenRampDuration);

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
        /*
        shooterBottom.set(ControlMode.Velocity, 0.0);
        beltBottom.set(ControlMode.PercentOutput, 0.0);
        */
        /*
        if(ctrlManager.getButtonPressedOperator(Constants.shooterBeltIntake))
            beltEnabled = !beltEnabled;
        */
        if(ctrlManager.getAxisOperator(Constants.shooterCtrlShoot) > 0.5)
        {
            if(!lifterSolenoid.get())
                lifterSolenoid.set(true);
            //shooterBottom.set(ControlMode.Velocity, -shooterSpeed);
            shooterBottom.set(ControlMode.PercentOutput, -1.0);
            //System.out.println(shooterSpeed + " " + shooterBottom.getSelectedSensorVelocity());
            // (shooterSpeed + shooterSpeedError) > shooterBottom.getSelectedSensorVelocity() & 
            //System.out.println((shooterSpeed-shooterSpeedError) + " " + shooterBottom.getSelectedSensorVelocity());
            if((shooterSpeed - shooterSpeedError) < -shooterBottom.getSelectedSensorVelocity()){
                beltBottom.set(ControlMode.PercentOutput, beltSpeed);
            } else {
                beltBottom.set(ControlMode.PercentOutput, 0.0);
            }
        } else /*
        if(ctrlManager.getButtonOperator(Constants.shooterCtrlReverse))
        {
            //if(frontLBsensor.get() & !backLBsensor.get()){
                // TODO: This is temp. Make a perm solution
                if(beltEnabled) {
                    beltBottom.set(ControlMode.PercentOutput, -ctrlManager.getIntakeSpeed());
                } else {
                    beltBottom.set(ControlMode.PercentOutput, 0.0);
                }
                //shooterBottom.set(ControlMode.PercentOutput, -Constants.intakeShooterSpeed);
            //}
        } else*/ if(ctrlManager.getIntakeEnabled()) {
            if(ctrlManager.getIntakeSpeed() > 0.0) {
                shooterBottom.set(ControlMode.PercentOutput, 0.5);
                //shooterBottom.set(ControlMode.Velocity, Constants.intakeShooterSpeed);
            }
            //System.out.println(frontLineBreakTimer + " " + frontLBsensor.get());
            if(!frontLBsensor.get()) {
                frontLineBreakTimer += 1; 
                if(frontLineBreakTimer > 8) { // 25 Ticks, 50 ticks per second
                    beltBottom.set(ControlMode.PercentOutput, -beltSpeed);
                }
            } else {
                if(frontLineBreakTimer > 3)
                    frontLineBreakTimer = 3;
                else if(frontLineBreakTimer > 0)
                    frontLineBreakTimer -= 1;
                else 
                    beltBottom.set(ControlMode.PercentOutput, 0.0);
            }
        } else {
            shooterBottom.set(ControlMode.PercentOutput, 0.0);
            //shooterBottom.set(ControlMode.Velocity, 0.0);
            beltBottom.set(ControlMode.PercentOutput, 0.0);
        }
        if(ctrlManager.getButtonPressedOperator(Constants.shooterCtrlLiftToggle))
        {
            lifterSolenoid.set(!lifterSolenoid.get());
        }
    }
    
	public void autonomousShoot(boolean runShooter, boolean reverserShooter) {
        shooterSpeed = inst.getEntry("Shooter_Speed").getNumber(Constants.AUTO_SHOOTER_SHOOTER_SPEED).doubleValue();
        beltSpeed = inst.getEntry("Belt_Speed").getNumber(Constants.AUTO_SHOOTER_BELT_SPEED).doubleValue();
        if(runShooter)
        {
            //shooterBottom.set(ControlMode.Velocity, shooterSpeed);
            //if((shooterSpeed + shooterSpeedError) > shooterBottom.getSelectedSensorVelocity() & (shooterSpeed - shooterSpeedError) < shooterBottom.getSelectedSensorVelocity()){
            beltBottom.set(ControlMode.PercentOutput, beltSpeed);
            //}
        }
        // TODO: Autonomous reverse shooter
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