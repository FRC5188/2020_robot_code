/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autonomous.taskgroups;

import java.util.Queue;

import frc.robot.autonomous.tasks.*;
import frc.robot.autonomous.utils.AutoTask;
import frc.robot.autonomous.utils.TaskGroup;

/**
 * Add your docs here.
 */
public class TaskGroupIntake extends TaskGroup {

    int time;
    public TaskGroupIntake(int time) {
        this.time = time;
    }

    @Override
    public Queue<AutoTask> retrieveTasks() {
        if(!tasks.isEmpty()) return tasks;
        tasks.add(new ParallelTask(
                new TaskIntake(this.time), 
                new SequentialTask(
                    new TaskWait(1000),
                    new TaskMove(this.time, true, 0.15)
                )
            )
        );
        tasks.add(new TaskRunShoot(2000));
        
        return tasks;
    }

}
