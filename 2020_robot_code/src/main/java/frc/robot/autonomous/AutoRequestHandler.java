package frc.robot.autonomous;

import frc.robot.Robot;

public class AutoRequestHandler {

    private static AutoRequestHandler inst;
    private Robot robot;
    
    // Output Variables
    private double throttle;
    private double turn;
    private boolean runShooter;
    private boolean toggleIntakeSolenoid;
    private boolean toggleShooterSolenoid;
    private boolean runIntake;

    // Input Variables
    private double leftEncoderDistance;
    private double rightEncoderDistance;
    private double gyroAngle;
    private boolean intakeSolenoidUp;
    private boolean shooterSolenoidUp;

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
    public void startPeriodic() {//should this be protected???
        this.throttle = 0.0;
        this.turn = 0.0;
        this.gyroAngle = 0.0;
        this.runShooter = false;
        this.runIntake = false;
        this.toggleIntakeSolenoid = false;
        this.toggleShooterSolenoid = false;
        this.intakeSolenoidUp = robot.getIntake().getIntakeSolenoidUp();
        this.shooterSolenoidUp = robot.getShooter().isSolenoidUp();//changed
        this.leftEncoderDistance = robot.getDriveTrain().getLeftEncoderInches();
        this.rightEncoderDistance = robot.getDriveTrain().getRightEncoderInches();
        
        this.gyroAngle = robot.getDriveTrain().getGyroAngle();
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
    
    public void toggleShooterSolenoid() {
        this.toggleShooterSolenoid = true;
    }
    public void toggleIntakeSolenoid() {
        this.toggleIntakeSolenoid = true;
    }

    /** 
     * Ran at the end of when the AutoManager periodic function gets called.
     * Handles the actual output from the tasks' requests to the robot.
     * Is used in order to be able to turn/move at same time, etc.
     **/
    public void endPeriodic() {
        robot.getDriveTrain().autonomousDefaultDrive(this.throttle, this.turn);
        robot.getShooter().autonomousShoot(this.runShooter, this.runIntake);
        robot.getIntake().autonomousIntake(this.runIntake);
        if(this.toggleIntakeSolenoid)
            robot.getIntake().toggleSolenoid();
        if(this.toggleShooterSolenoid)
            robot.getShooter().toggleSolenoid();
    }

	public void runShooter() {
        this.runShooter = true;
    }
    
    public void runIntake() {
        this.runIntake = true;
    }

	public boolean getIntakeSolenoidUp() {
		return this.intakeSolenoidUp;
	}

	public boolean getShooterSolenoidUp() {
		return this.shooterSolenoidUp;
	}

	public void init() {
        if(robot.getIntake().getIntakeSolenoidUp())
            robot.getIntake().toggleSolenoid();
        if(!robot.getShooter().isSolenoidUp())//changed this, might need 
            robot.getShooter().toggleSolenoid();
	}

	public void setBeltOff() {
        this.robot.getShooter().runningBelt = false;
	}

}