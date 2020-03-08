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
public class TaskMove extends AutoTask {

    PIDController pidController;
    double distance;
    double tolerance;
    long endTime;
    double startDist;
    boolean useQuickHack = false;
    int timeToMove;
    boolean forwardOrBackward;
    double speed;
    /**
     * Distance is in Inches 
     * TODO: Determine best unit
     * @param distance
     */
    public TaskMove(double distance) {
        this.distance = distance;
        this.tolerance = Constants.TASK_MOVE_DEFAULT_TOLERANCE;
        this.timeToMove = Constants.TASK_MOVE_DEFAULT_TIME;
        this.state = TaskState.NOT_STARTED;
    }
    public TaskMove(double distance, double speed) {
        this.distance = distance;
        this.speed = speed;
    }
    /*
    * Quick task to make sure we have atleast one working auto before comp
    * Move for a certain number of ticks
    */
    public TaskMove(int time, boolean forwardOrBackward, double speed) {
        this.useQuickHack = true;
        this.timeToMove = time;
        this.forwardOrBackward = forwardOrBackward;
        this.speed = speed;
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
    public TaskMove(double distance, double tolerance, int time) {
        this.distance = distance;
        this.tolerance = tolerance;
        this.timeToMove = time;
        this.state = TaskState.NOT_STARTED;
    }

	@Override
    public void init() {
        this.pidController = new PIDController(Constants.TASK_MOVE_PID_P,Constants.TASK_MOVE_PID_I,Constants.TASK_MOVE_PID_D);
        this.state = TaskState.RUNNING;
        this.startDist = getAverageEncoderDist();
        this.pidController.setSetpoint(this.startDist + this.distance);
        if(this.useQuickHack)
            this.endTime = System.currentTimeMillis()+timeToMove;
        //this.pidController.setTolerance(this.tolerance*0.75);
    }

    public double getAverageEncoderDist() {
        AutoRequestHandler reqHandler = AutoRequestHandler.getInst();
        return 0.5*(reqHandler.getLeftEncoderDistance()+reqHandler.getRightEncoderDistance());
    }
    @Override
    public TaskState periodic() {
        if(this.state == TaskState.CANCELLED || this.state == TaskState.FINISHED) return this.state;
        double avgDist = getAverageEncoderDist();
        AutoRequestHandler reqHandler = AutoRequestHandler.getInst();
        System.out.println("dist " + getAverageEncoderDist());
        if(this.useQuickHack) {
            reqHandler.addThrottle(forwardOrBackward ? this.speed : -this.speed);
            if(System.currentTimeMillis() > this.endTime) {
                this.isFinished = true;
                this.state = TaskState.FINISHED;
            }
        } else {
            if(Math.abs(this.speed) > 0 && Math.abs(avgDist-startDist) >= this.distance) {
                this.isFinished = true;
                this.state = TaskState.FINISHED;
                return this.state;
            }
            System.out.println("Speed: " + this.speed + " " + avgDist + " " + startDist + " " + (avgDist-startDist));
            reqHandler.addThrottle(Math.abs(this.speed) > 0 ? this.speed : this.pidController.calculate(avgDist));
            if(Math.abs(avgDist-startDist) < this.tolerance) {
                if(System.currentTimeMillis() > this.endTime) { //think this is broken for just dist and speed
                    this.isFinished = true;
                    this.state = TaskState.FINISHED;
                }
                // this.isFinished = true;
                // this.state = TaskState.FINISHED;
            } else 
                this.endTime = System.currentTimeMillis()+timeToMove;
        }
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
        if(this.useQuickHack) 
            return "Move " + (this.forwardOrBackward ? "forward" : "backward") + " " + (timeToMove/1000.0) + " seconds\n at " + this.speed + " throttle.";
        return "Move forward "  + distance + " inches.";
    }

}
