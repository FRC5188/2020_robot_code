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
    int time;
    int timer = 0;
    double startDist;
    /**
     * Distance is in Inches 
     * TODO: Determine best unit
     * @param distance
     */
    public TaskMove(double distance) {
        this.distance = distance;
        this.tolerance = Constants.TASK_MOVE_DEFAULT_TOLERANCE;
        this.time = Constants.TASK_MOVE_DEFAULT_TIME;
        this.state = TaskState.NOT_STARTED;
    }
    /**
     * Distance is in Inches
     * Tolerance is in inches away,
     * Time is ticks of time to stay in the range
     * TODO: Determine best unit
     * @param distance
     * @param tolerance
     **/
    public TaskMove(double distance, double tolerance, int time) {
        this.distance = distance;
        this.tolerance = tolerance;
        this.time = time;
        this.state = TaskState.NOT_STARTED;
    }

	@Override
    public void init() {
        this.pidController = new PIDController(Constants.TASK_MOVE_PID_P,Constants.TASK_MOVE_PID_I,Constants.TASK_MOVE_PID_D);
        this.state = TaskState.RUNNING;
        this.startDist = getAverageEncoderDist();
        this.pidController.setSetpoint(this.startDist + this.distance);
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
        reqHandler.addThrottle(this.pidController.calculate(avgDist));
        if(Math.abs(avgDist-startDist) < this.tolerance) {
            timer += 1;
            if(timer > time) {
                this.isFinished = true;
                this.state = TaskState.FINISHED;
            }
        } else if(timer > 0)
            timer = 0;
        return this.state;
    }

    @Override
    public void initShuffle() {
        // TODO Auto-generated method stub

    }

    @Override
    public void end() {
        // TODO Auto-generated method stub

    }

    @Override
    public void cancel() {
        this.state = TaskState.CANCELLED;
    }

    @Override
    public void updateShuffle() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isFinished() {
        return this.isFinished;
    }

    @Override
    public String serialize() {
        return "Move forward "  + distance + " inches.";
    }

}
