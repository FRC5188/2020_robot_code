/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autonomous.tasks;

import frc.robot.autonomous.utils.AutoTask;
import frc.robot.autonomous.utils.TaskState;

/**
 * Use to intake balls
 */
public class TaskWait extends AutoTask {

    int tickTime;
    long endTime;

    public TaskWait(int waitTime) {
        this.tickTime = waitTime;
    }

	@Override
    public void init() {
        this.state = TaskState.RUNNING;
        this.endTime = System.currentTimeMillis()+tickTime;
    }

    @Override
    public TaskState periodic() {
        if(this.state == TaskState.CANCELLED || this.state == TaskState.FINISHED) return this.state;
        if(this.endTime != 0 && System.currentTimeMillis() > this.endTime) {
            this.state = TaskState.FINISHED;
            return this.state;
        }
        if(System.currentTimeMillis() < this.endTime)
            return this.state;
        this.state = TaskState.FINISHED;
        return this.state;
    }

    @Override
    public void initShuffle() {

    }

    @Override
    public void end() {

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
        return "Wait " + (this.tickTime/1000.0) + " seconds.";
    }

}
