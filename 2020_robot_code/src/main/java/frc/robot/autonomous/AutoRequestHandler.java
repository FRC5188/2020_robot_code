package frc.robot.autonomous;

import frc.robot.Robot;

public class AutoRequestHandler {

    private static AutoRequestHandler inst;
    private Robot robot;
    
    // Output Variables
    private double throttle;
    private double turn;

    // Input Variables
    private double leftEncoderDistance;
    private double rightEncoderDistance;
    private double gyroAngle;

    public AutoRequestHandler(Robot robot) {
        inst = this;
        this.robot = robot;
    }

    public static AutoRequestHandler getInst() {
        return inst;
    }

    /** 
     * Ran first whenever the AutoManager periodic function gets called
     * Resets all values from last periodic or any other junk.
     * NOTE: If you add extra fields to this class, you must reset them here.
     * Maybe request data from sensors here? (for processing by tasks?)
     **/
    public void startPeriodic() {
        this.throttle = 0.0;
        this.turn = 0.0;
        this.gyroAngle = 0.0;
        this.leftEncoderDistance = robot.getDriveTrain().getLeftEncoderInches();
        this.rightEncoderDistance = robot.getDriveTrain().getRightEncoderInches();
        // TODO: Get Gyro Info
        // this.gyroAngle = ??????
    }

    public double getLeftEncoderDistance() {
        return this.leftEncoderDistance;
    }

    public double getRightEncoderDistance() {
        return this.rightEncoderDistance;
    }

    public void addThrottle(double amt) {
        this.throttle += amt;
    }

    public void addTurn(double amt) {
        this.turn += amt;
    }

	public double getGyroAngle() {
		return this.gyroAngle;
	}

    /** 
     * Ran at the end of when the AutoManager periodic function gets called.
     * Handles the actual output from the tasks' requests to the robot.
     * Is used in order to be able to turn/move at same time, etc.
     **/
    public void endPeriodic() {
        robot.getDriveTrain().autonomousDefaultDrive(this.throttle, this.turn);
    }

}