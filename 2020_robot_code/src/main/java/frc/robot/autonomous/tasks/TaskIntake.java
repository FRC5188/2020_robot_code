/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autonomous.tasks;

import frc.robot.Constants;
import frc.robot.autonomous.AutoManager;
import frc.robot.autonomous.AutoRequestHandler;
import frc.robot.autonomous.utils.AutoTask;
import frc.robot.autonomous.utils.TaskState;

/**
 * Run after done shooting. Reverses belt to push balls to back
 */
public class TaskIntake extends AutoTask {

    int currentTime;
    int tickTime;
    long solenoidTime;
    long endTime;
    boolean solenoidToggled;
    public TaskIntake() {
        this.tickTime = Constants.TASK_INTAKE_TIME;
    }

    public TaskIntake(int tickTime) {
        this.tickTime = tickTime;
    }

	@Override
    public void init() {
        this.state = TaskState.RUNNING;
        this.currentTime = 0;
        this.solenoidTime = 0;
        this.solenoidToggled = false;
        this.endTime = 0;
        //this.pidController.setTolerance(this.tolerance*0.75);
    }

    @Override
    public TaskState periodic() {
        if(this.state == TaskState.CANCELLED || this.state == TaskState.FINISHED) return this.state;
        if(this.endTime != 0 && System.currentTimeMillis() > this.endTime) {
            this.state = TaskState.FINISHED;
            return this.state;
        }
        if(!AutoRequestHandler.getInst().getIntakeSolenoidUp() && !this.solenoidToggled) {
            AutoRequestHandler.getInst().toggleIntakeSolenoid();
            this.solenoidToggled = true;
            this.solenoidTime = System.currentTimeMillis() + 1000;
        }
        if(this.solenoidToggled) {
            if(System.currentTimeMillis() < this.solenoidTime)
                return this.state;
        }
        if(this.endTime == 0) 
            this.endTime = System.currentTimeMillis() + tickTime;
        AutoRequestHandler.getInst().runIntake();
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
        return "Reverse shooter for " + (Math.round(this.tickTime/60.0*1000)/1000.0) + " seconds.";
    }

}