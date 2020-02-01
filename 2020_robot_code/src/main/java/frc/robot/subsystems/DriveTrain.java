package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Constants;
import frc.robot.Subsystem;
import frc.robot.Constants.Axis;
import frc.robot.Constants.Buttons;


public class DriveTrain implements Subsystem{

    //motor controllers
    WPI_TalonFX leftMotor1;
    WPI_TalonFX leftMotor2;
    WPI_TalonFX rightMotor1;
    WPI_TalonFX rightMotor2;
    DifferentialDrive diffDrive;

    XboxController driveCtrl;
    //constructor
    public DriveTrain(XboxController controller){
        this.driveCtrl = controller;

        this.initCANMotors();
        this.initCurrentLimit();
        this.initRamping();
        this.initDiffDrive();

    }

    private void initCANMotors() {
        this.leftMotor1 = new WPI_TalonFX(Constants.leftFalcon1);
        this.leftMotor2 = new WPI_TalonFX(Constants.leftFalcon2);
        this.rightMotor1 = new WPI_TalonFX(Constants.rightFalcon1);
        this.rightMotor2 = new WPI_TalonFX(Constants.rightFalcon2);

        //enable braking mode
        leftMotor1.setNeutralMode(NeutralMode.Brake);
        leftMotor2.setNeutralMode(NeutralMode.Brake);
        rightMotor1.setNeutralMode(NeutralMode.Brake);
        rightMotor2.setNeutralMode(NeutralMode.Brake);

        this.leftMotor2.follow(leftMotor1);
        this.rightMotor2.follow(rightMotor1);
    }
    private void initCurrentLimit(){
        //create current config, new for 2020
        //it is a little long........ :(
        SupplyCurrentLimitConfiguration supplyCurrentConfig;
        supplyCurrentConfig = new SupplyCurrentLimitConfiguration(
            true, Constants.SupplyCurrentLimit, Constants.SupplyTriggerCurremt, Constants.SupplyCurrentDuration
        );

        //apply current limits
        leftMotor1.configSupplyCurrentLimit(supplyCurrentConfig);
        rightMotor1.configSupplyCurrentLimit(supplyCurrentConfig);
        
        leftMotor2.configSupplyCurrentLimit(supplyCurrentConfig);
        rightMotor2.configSupplyCurrentLimit(supplyCurrentConfig);

    }

    private void initRamping(){
    
        leftMotor1.configOpenloopRamp(Constants.openRampDuration);
        rightMotor1.configOpenloopRamp(Constants.openRampDuration); 
        leftMotor2.configOpenloopRamp(Constants.openRampDuration);
        rightMotor2.configOpenloopRamp(Constants.openRampDuration);
    
    }

    private void initDiffDrive() {
    
        diffDrive = new DifferentialDrive(leftMotor1, rightMotor1);
    
    }
    public void resetEncoders() {

        leftMotor1.setSelectedSensorPosition(0);
        leftMotor2.setSelectedSensorPosition(0);
        rightMotor1.setSelectedSensorPosition(0);
        rightMotor2.setSelectedSensorPosition(0);
    
    }

    // default shifter to low for now
    // Throttle and Turn are put to the power of cThrottle and cTurn to have a better distribution curve
    // explore turnShifter farther..
    private static final double turnShifter = 0.65;
    private static final double cThrottle = 3;
    private static final double cTurn = 5;
    private static final double deadSpace = 0.2;
    private static final double minimumThreshold = 0.001;
    private void teleopDefaultDrive() {
        double shifterVal = 0.5;
        double throttle = driveCtrl.getRawAxis(Axis.LY);
        double turn = driveCtrl.getRawAxis(Axis.RX);
        // Map turn and throttle to be from "deadSpace" to 1.0
        // So that a small bump actually moves the robot ( < deadSpace doesn't move)
        // minimumThreshold is the minimum the controller has to move to map it. (Otherwise it'd move without user input)
        
        throttle = throttle > 0 ? Math.pow(Math.abs(throttle),cThrottle) : -Math.pow(Math.abs(throttle),cThrottle);
        turn = turn > 0 ? Math.pow(Math.abs(turn),cTurn) : -Math.pow(Math.abs(turn),cTurn);
        throttle = throttle * (1-deadSpace) + (Math.abs(throttle) < minimumThreshold ? 0.0 : (throttle > 0 ? deadSpace : -deadSpace));
        turn = turn * (1-deadSpace) + (Math.abs(turn) < minimumThreshold ? 0.0 : (turn > 0 ? deadSpace : -deadSpace));
        
        // allow for manual quick turn enable 
        boolean isQuickTurn = driveCtrl.getRawButton(Constants.Buttons.R);

        // if left bumper button pressed, activate shifter
         if(driveCtrl.getRawButton(Constants.Buttons.L)) {
             shifterVal = 1;
         } else if(shifterVal != 0.5) {
             shifterVal = 0.5;
         }
         if(Math.abs(throttle) < .02){
             isQuickTurn = true;
         }

        diffDrive.curvatureDrive(throttle * shifterVal, turn * turnShifter, isQuickTurn);

    }

    public void autonomousDefaultDrive(double throttle, double turn) {
        // TODO: If subsys is killed, don't run.
        diffDrive.curvatureDrive(Constants.AUTONOMOUS_MAX_THROTTLE*throttle, Constants.AUTONOMOUS_MAX_TURN*turn, false);
    }

    public double getEncoderTicks(){
        return this.leftMotor1.getSelectedSensorPosition();

    }
    
    public double getLeftEncoderInches(){
        return this.leftMotor1.getSelectedSensorPosition()/Constants.ENCODER_TICKS_PER_INCH;
    }

    public double getRightEncoderInches(){
        return this.rightMotor1.getSelectedSensorPosition()/Constants.ENCODER_TICKS_PER_INCH;
    }

    public void tankDrive(double left, double right){
        diffDrive.tankDrive(left, right);
    }

    public void setUpPID(){
        
    }

    @Override
    public void init() {

    }

    @Override
    public void initShuffle() {

    }

    @Override
    public void operate() {

        this.teleopDefaultDrive();

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