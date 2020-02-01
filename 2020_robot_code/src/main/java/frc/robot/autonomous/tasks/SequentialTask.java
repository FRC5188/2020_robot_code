/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autonomous.tasks;

import java.util.LinkedList;
import java.util.Queue;

import frc.robot.autonomous.utils.AutoTask;
import frc.robot.autonomous.utils.TaskState;

/**
 * Add your docs here.
 */
public class SequentialTask extends AutoTask {
    
    Queue<AutoTask> tasks = new LinkedList<AutoTask>();

    public SequentialTask(AutoTask... tasks) {
        for(AutoTask t: tasks) {
            this.tasks.add(t);
        }
    }

    @Override
    public void init() {
        tasks.peek().init();
    }

    @Override
    public TaskState periodic() {
        if(tasks.size() == 0)
            return TaskState.FINISHED;
        AutoTask task = tasks.peek();

        if(task.isFinished()) {
            tasks.poll().end();
            if(tasks.size() == 0)
                return TaskState.FINISHED;
            tasks.peek().init();
        }
        return tasks.peek().periodic();
    }

    @Override
    public void initShuffle() {
        tasks.peek().initShuffle();
    }

    @Override
    public void end() {
        tasks.poll().end();
    }

    @Override
    public void cancel() {
        tasks.poll().cancel();
    }

    @Override
    public void updateShuffle() {
        tasks.peek().updateShuffle();
    }

    @Override
	public boolean isFinished() {
        return tasks.size() == 0; 
    }
    
}
