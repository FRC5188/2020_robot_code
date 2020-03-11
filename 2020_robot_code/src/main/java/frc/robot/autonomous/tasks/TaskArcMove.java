/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autonomous.tasks;

import edu.wpi.first.wpilibj.controller.PIDController;
import frc.robot.Constants;
import frc.robot.autonomous.AutoRequestHandler;
import frc.robot.autonomous.utils.AutoTask;
import frc.robot.autonomous.utils.TaskState;

/**
 * Add your docs here.
 */
public class TaskArcMove extends AutoTask {

    PIDController throttlePIDController;
    PIDController turnPIDController;
    double tolerance;
    int toleranceTime;
    double finalX;
    double finalY;
    double startX;
    double startY;
    long endTime;

    /**
     * Distance is in Inches 
     * TODO: Determine best unit
     * @param distance
     */
    public TaskArcMove(double dxPosition, double dyPosition) {
        this.finalX = dxPosition;
        this.finalY = dyPosition;
        this.tolerance = Constants.TASK_MOVE_DEFAULT_TOLERANCE;
        this.toleranceTime = Constants.TASK_MOVE_DEFAULT_TIME;
        this.state = TaskState.NOT_STARTED;
    }
    /**
     * Distance is in Inches
     * Tolerance is in inches away,
     * Time is ticks of time to stay in the range
     * TODO: Determine best unit
     * @param distance
     * @param tolerance
     * @param time
     **/
    public TaskArcMove(double dxPosition, double dyPosition, double tolerance, int time) {
        this.finalX = dxPosition;
        this.finalY = dyPosition;
        this.tolerance = tolerance;
        this.toleranceTime = time;
        this.state = TaskState.NOT_STARTED;
    }

	@Override
    public void init() {
        this.throttlePIDController = new PIDController(Constants.TASK_MOVE_THROTTLE_PID_P,Constants.TASK_MOVE_THROTTLE_PID_I,Constants.TASK_MOVE_THROTTLE_PID_D);
        this.turnPIDController = new PIDController(Constants.TASK_MOVE_TURN_PID_P,Constants.TASK_MOVE_TURN_PID_I,Constants.TASK_MOVE_TURN_PID_D);
        this.state = TaskState.RUNNING;
        AutoRequestHandler reqHandler = AutoRequestHandler.getInst();
        this.startX = reqHandler.getOdometryX();
        this.startY = reqHandler.getOdometryY();
        this.throttlePIDController.setSetpoint(0.0);
        this.turnPIDController.setSetpoint(0.0);
        this.endTime = System.currentTimeMillis()+toleranceTime;
    }

    @Override
    public TaskState periodic() {
        if(this.state == TaskState.CANCELLED || this.state == TaskState.FINISHED) return this.state;
        AutoRequestHandler reqHandler = AutoRequestHandler.getInst();
        double dX = finalX-reqHandler.getOdometryX();
        double dY = finalY-reqHandler.getOdometryY();
        double curGyroAng = reqHandler.getGyroAngleRadians();
        // Calculate distance from current location to final location
        double wantAngle = Math.atan(dY/dX);
        if(dX < 0)
            wantAngle = Math.PI-wantAngle;
        double angleDif = (curGyroAng - wantAngle) % (2*Math.PI);
        if(angleDif > Math.PI) // If its wanting to go > 180 deg. Go backwards < 180 deg. Basically make it faster
            angleDif = angleDif - 2*Math.PI;
        double dist = Math.sqrt(dX*dX+dY*dY);
        // Use dot product equation to find cos of angle between vectors difference in position and current angle 
        double cosAngleBetween = (dX*Math.cos(curGyroAng)+dY*Math.sin(curGyroAng))/dist;
        reqHandler.addThrottle(cosAngleBetween > 0 ? (cosAngleBetween * this.throttlePIDController.calculate(dist)) : 0);
        reqHandler.addTurn(this.turnPIDController.calculate(angleDif));
        // If within tolerance, see if it'll stay in. If so, exit task
        if(dist < this.tolerance) {
            if(System.currentTimeMillis() > this.endTime) {
                this.isFinished = true;
                this.state = TaskState.FINISHED;
            }
        } else 
            this.endTime = System.currentTimeMillis()+this.toleranceTime;
        return this.state;
    }

    @Override
    public void initShuffle() {

    }

    @Override
    public void end() {
        AutoRequestHandler.getInst().addThrottle(0);
    }

    @Override
    public void cancel() {
        this.state = TaskState.CANCELLED;
    }

    @Override
    public void updateShuffle() {

    }

    @Override
    public boolean isFinished() {
        return this.isFinished;
    }

    @Override
    public String serialize() {
        return "ERROR ARC MOVE NO SERIALIZE";
    }

}
