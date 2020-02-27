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
 * Add your docs here.
 */
public class ParallelTask extends AutoTask {

    AutoTask[] tasks;

    public ParallelTask(AutoTask... tasks) {
        this.tasks = tasks;
    }

    @Override
    public void init() {
        for(AutoTask t: tasks) t.init();
    }

    @Override
    public TaskState periodic() {
        for(AutoTask t: tasks) {
            TaskState indState = t.periodic();
            switch(indState) {
                case CANCELLED:
                    break;
                case FINISHED:
                    break;
                case NOT_STARTED:
                    break; // Should never happen?
                case RUNNING:
                    return TaskState.RUNNING;
                case UNKNOWN:
                    return TaskState.UNKNOWN;
            }
        }
        return TaskState.FINISHED;
    }

    @Override
    public void initShuffle() {
        for(AutoTask t: tasks) t.initShuffle();
    }

    @Override
    public void end() {
        for(AutoTask t: tasks) t.end();
    }

    @Override
    public void cancel() {
        for(AutoTask t: tasks) t.cancel();
    }

    @Override
    public void updateShuffle() {
        for(AutoTask t: tasks) t.updateShuffle();
    }

    @Override
	public boolean isFinished() {
        for(AutoTask t: tasks) {
            if(!t.isFinished()) return false;
        }
		return true;
    }

    @Override
    public String serialize() {
        StringBuilder builder = new StringBuilder();
        builder.append("Do the following at the same time:");
        for(AutoTask t: tasks) {
            builder.append("\n  ");
            builder.append(t.serialize());
        }
        return builder.toString();
    }
    
}
