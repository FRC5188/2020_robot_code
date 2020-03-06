package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.Constants;
import frc.robot.ControllerManager;
import frc.robot.Robot;
import frc.robot.Subsystem;
import frc.robot.utils.InputButton;

public class DriveTrain implements Subsystem {

    // motor controllers
    WPI_TalonFX leftMotor1;
    WPI_TalonFX leftMotor2;
    WPI_TalonFX rightMotor1;
    WPI_TalonFX rightMotor2;
    DifferentialDrive diffDrive;
    DifferentialDriveOdometry odometry;
    AHRS ahrsGyro;

    PIDController visionPid;
    NetworkTableEntry targetX;
    NetworkTableEntry pidP;
    NetworkTableEntry pidI;
    NetworkTableEntry pidD;

    ControllerManager ctrlManager;
    Robot robot;

    // constructor
    public DriveTrain(Robot robot) {
        this.robot = robot;
        this.ctrlManager = Robot.getControllerManager();

        this.initCANMotors();
        this.initCurrentLimit();
        this.initRamping();
        this.initDiffDrive();
        this.initGyro();
        this.initOdometry();
        this.initVision();

        // TODO: Remove Testing Code
        ShuffleboardTab tab = Shuffleboard.getTab("Drive Variables");
        cThrottleEnt = tab.add("Throttle Distribution Curve", cThrottle).getEntry();
        cTurnEnt = tab.add("Turn Distribution Curve", cTurn).getEntry();
        minimumThresholdEnt = tab.add("Minimum Joystick Threshold", minimumThreshold).getEntry();
        deadSpaceThrottleEnt = tab.add("Minimum Power Throttle", deadSpaceThrottle).getEntry();
        deadSpaceTurnEnt = tab.add("Minimum Power Turn", deadSpaceTurn).getEntry();
        turnShifterEnt = tab.add("Turn Coefficient", turnShifter).getEntry();
        throttleShifterEnt = tab.add("Throttle Coefficient", throttleShifter).getEntry();
    }

    private void initVision() {
        this.visionPid = new PIDController(Constants.VISION_PID_P, Constants.VISION_PID_I, Constants.VISION_PID_D);
        this.targetX = NetworkTableInstance.getDefault().getTable("SmartDashboard").getEntry("tape-x");
        this.visionPid.setSetpoint(160.0);
        ShuffleboardTab tab = Shuffleboard.getTab("Vision PID Test");
        pidP = tab.add("P",0.0).getEntry();
        pidI = tab.add("I",0.0).getEntry();
        pidD = tab.add("D",0.0).getEntry();
    }

    private void initCANMotors() {
        this.leftMotor1 = new WPI_TalonFX(Constants.leftFalcon1);
        this.leftMotor2 = new WPI_TalonFX(Constants.leftFalcon2);
        this.rightMotor1 = new WPI_TalonFX(Constants.rightFalcon1);
        this.rightMotor2 = new WPI_TalonFX(Constants.rightFalcon2);

        // enable braking mode
        leftMotor1.setNeutralMode(NeutralMode.Brake);
        leftMotor2.setNeutralMode(NeutralMode.Brake);
        rightMotor1.setNeutralMode(NeutralMode.Brake);
        rightMotor2.setNeutralMode(NeutralMode.Brake);

        this.rightMotor1.setInverted(InvertType.InvertMotorOutput);
        this.rightMotor2.setInverted(InvertType.FollowMaster);

        this.leftMotor1.setInverted(InvertType.InvertMotorOutput);
        this.leftMotor2.setInverted(InvertType.FollowMaster);

        this.leftMotor2.follow(leftMotor1);
        this.rightMotor2.follow(rightMotor1);
    }

    private void initCurrentLimit() {
        // create current config, new for 2020
        // it is a little long........ :(
        SupplyCurrentLimitConfiguration supplyCurrentConfig;
        supplyCurrentConfig = new SupplyCurrentLimitConfiguration(true, Constants.DriveTrainSupplyCurrentLimit,
                Constants.DriveTrainSupplyTriggerCurremt, Constants.DriveTrainSupplyCurrentDuration);

        // apply current limits
        leftMotor1.configSupplyCurrentLimit(supplyCurrentConfig);
        rightMotor1.configSupplyCurrentLimit(supplyCurrentConfig);

        leftMotor2.configSupplyCurrentLimit(supplyCurrentConfig);
        rightMotor2.configSupplyCurrentLimit(supplyCurrentConfig);

    }

    private void initRamping() {

        leftMotor1.configOpenloopRamp(Constants.openRampDuration);
        rightMotor1.configOpenloopRamp(Constants.openRampDuration);
        leftMotor2.configOpenloopRamp(Constants.openRampDuration);
        rightMotor2.configOpenloopRamp(Constants.openRampDuration);

    }

    private void initDiffDrive() {

        diffDrive = new DifferentialDrive(leftMotor1, rightMotor1);
        diffDrive.setSafetyEnabled(false);
    }

    private void initGyro() {

        ahrsGyro = new AHRS(Port.kMXP);

    }

    private void initOdometry() {

        odometry = new DifferentialDriveOdometry(new Rotation2d(Math.toRadians(ahrsGyro.getYaw())));

    }

    public void resetEncoders() {

        leftMotor1.setSelectedSensorPosition(0);
        leftMotor2.setSelectedSensorPosition(0);
        rightMotor1.setSelectedSensorPosition(0);
        rightMotor2.setSelectedSensorPosition(0);

    }

    // default shifter to low for now
    // Throttle and Turn are put to the power of cThrottle and cTurn to have a
    // better distribution curve
    // explore turnShifter farther..

    NetworkTableEntry turnShifterEnt;
    NetworkTableEntry cThrottleEnt;
    NetworkTableEntry cTurnEnt;
    NetworkTableEntry deadSpaceThrottleEnt;
    NetworkTableEntry deadSpaceTurnEnt;
    NetworkTableEntry minimumThresholdEnt;
    NetworkTableEntry throttleShifterEnt;

    private static double turnShifter = Constants.driveTrainTurnShifter;
    private static double cThrottle = Constants.driveTrainCThrottle;
    private static double cTurn = Constants.driveTrainCTurn;//5;
    private static double deadSpaceThrottle = Constants.driveTrainDeadSpaceThrottle;//0.2;
    private static double deadSpaceTurn = Constants.driveTrainDeadSpaceTurn;//0.2;
    private static double minimumThreshold = Constants.driveTrainMinimumThreshold;//0.001;
    private static double throttleShifter = Constants.driveTrainThrottleShifter;//0.3;
    
    private void teleopDefaultDrive() {
        /*
        turnShifter = turnShifterEnt.getDouble(Constants.driveTrainTurnShifter);
        cThrottle = cThrottleEnt.getDouble(Constants.driveTrainCThrottle);
        cTurn = cTurnEnt.getDouble(Constants.driveTrainCTurn);
        deadSpaceThrottle = deadSpaceThrottleEnt.getDouble(Constants.driveTrainDeadSpaceThrottle);
        deadSpaceTurn = deadSpaceTurnEnt.getDouble(Constants.driveTrainDeadSpaceTurn);
        minimumThreshold = minimumThresholdEnt.getDouble(Constants.driveTrainMinimumThreshold);
        throttleShifter = throttleShifterEnt.getDouble(Constants.driveTrainThrottleShifter);
        */
        double throttle = ctrlManager.getAxis(InputButton.DRIVER_LY);
        double turn = ctrlManager.getAxis(InputButton.DRIVER_RX);
        // Map turn and throttle to be from "deadSpace" to 1.0
        // So that a small bump actually moves the robot ( < deadSpace doesn't move)
        // minimumThreshold is the minimum the controller has to move to map it. (Otherwise it'd move without user input)
        
        if(ctrlManager.getButton(Constants.throttleShiftButton))
            throttleShifter = 1.0;
        
        throttle = throttle > 0 ? Math.pow(Math.abs(throttle),cThrottle) : -Math.pow(Math.abs(throttle),cThrottle);
        turn = turn > 0 ? Math.pow(Math.abs(turn),cTurn) : -Math.pow(Math.abs(turn),cTurn);
        throttle = throttle * throttleShifter * (1-deadSpaceThrottle) + (Math.abs(throttle) < minimumThreshold ? 0.0 : (throttle > 0 ? deadSpaceThrottle : -deadSpaceThrottle));
        turn = turn * turnShifter * (1-deadSpaceTurn) + (Math.abs(turn) < minimumThreshold ? 0.0 : (turn > 0 ? deadSpaceTurn : -deadSpaceTurn));
        /*
        if(ctrlManager.getButton(Constants.shooterAlignVisionButton)) {
            visionPid.setPID(pidP.getDouble(0.0),pidI.getDouble(0.0),pidD.getDouble(0.0));
            if(targetX.getDouble(-1.0) >= 0)
                turn = visionPid.calculate(targetX.getDouble(160.0));
            System.out.println(targetX.getDouble(160.0) + " " + turn);
        }*/

        // allow for manual quick turn enable 
        //boolean isQuickTurn = ctrlManager.getButtonDriver(Constants.Buttons.R);

        // if left bumper button pressed, activate shifter
        
        //if(Math.abs(throttle) < .5){
        //isQuickTurn = true;
        //}
        // NOTE: Turning is negative, in order to be correct
        diffDrive.arcadeDrive(throttle, -turn);

    }

    public void autonomousDefaultDrive(double throttle, double turn) {
        // TODO: If subsys is killed, don't run.
        diffDrive.curvatureDrive(-Math.max(Math.min(Constants.AUTONOMOUS_MAX_THROTTLE, throttle), -Constants.AUTONOMOUS_MAX_THROTTLE), Math.max(Math.min(Constants.AUTONOMOUS_MAX_TURN, turn), -Constants.AUTONOMOUS_MAX_TURN), false);
    }

    public void updateOdometry() {
        odometry.update(new Rotation2d(Math.toRadians(ahrsGyro.getYaw())), getLeftEncoderTicks(), getRightEncoderTicks());
        
    }

    public double getGyroAngle() {
        return ahrsGyro.getYaw();
    }

    public Pose2d getOdometryPosition() {
        return odometry.getPoseMeters();
    }

    public double getLeftEncoderTicks(){
        return this.leftMotor1.getSelectedSensorPosition();
    }
    
    public double getRightEncoderTicks(){
        return this.rightMotor1.getSelectedSensorPosition();
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