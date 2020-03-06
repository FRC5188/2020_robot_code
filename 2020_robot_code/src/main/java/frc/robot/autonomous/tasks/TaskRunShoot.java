/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autonomous.tasks;

import frc.robot.Constants;
import frc.robot.autonomous.AutoRequestHandler;
import frc.robot.autonomous.utils.AutoTask;
import frc.robot.autonomous.utils.TaskState;

/**
 * Add your docs here.
 */
public class TaskRunShoot extends AutoTask {

    boolean solenoidToggled;
    long solenoidTime;
    long endTime;
    int tickTime;
    public TaskRunShoot() {
        this.tickTime = Constants.TASK_SHOOT_RUN_DEFAULT_TICKTIME_RUN;
    }

    /**
     * Time to run shooter in milliseconds
     * @param tickTime - In Milliseconds
     */
    public TaskRunShoot(int tickTime) {
        this.tickTime = tickTime;
    }

	@Override
    public void init() {
        this.state = TaskState.RUNNING;
        this.endTime = 0;
        this.solenoidToggled = false;
        this.solenoidTime = 0;
        //this.pidController.setTolerance(this.tolerance*0.75);
    }

    @Override
    public TaskState periodic() {
        if(this.state == TaskState.CANCELLED || this.state == TaskState.FINISHED) return this.state;
        if(this.endTime != 0 && System.currentTimeMillis() > this.endTime) {
            this.state = TaskState.FINISHED;
            return this.state;
        }
        if(!AutoRequestHandler.getInst().getShooterSolenoidUp() && !this.solenoidToggled) {
            // Put solenoid up if isn't up, and wait
            AutoRequestHandler.getInst().toggleShooterSolenoid();
            this.solenoidToggled = true; 
            this.solenoidTime = System.currentTimeMillis() + 1000;
        }
        if(this.solenoidToggled) {
            if(System.currentTimeMillis() < this.solenoidTime)
                return this.state; // If solenoid isn't up yet, return
        }
        if(this.endTime == 0)
            this.endTime = System.currentTimeMillis() + this.tickTime;
        AutoRequestHandler.getInst().runShooter();
        return this.state;
    }

    @Override
    public void initShuffle() {

    }

    @Override
    public void end() {
        AutoRequestHandler.getInst().setBeltOff();
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
        return "Run shooter for " + Math.round(this.tickTime/1000.0) + " seconds.";
    }

}
