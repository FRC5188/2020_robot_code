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
public class TaskGroupMoveShoot  extends TaskGroup {

    int time;
    int shootTime;
    int timeBack;

    public TaskGroupMoveShoot(int time, int shootTime, int timeBack) {
        this.time = time;
        this.shootTime = shootTime;
        this.timeBack = timeBack;
    }

    @Override
    public Queue<AutoTask> retrieveTasks() {
        if(!tasks.isEmpty()) return tasks;
        if(time > 0)
            tasks.add(new TaskMove(time, true, 0.15));
        tasks.add(new TaskRunShoot(shootTime));
        if(timeBack > 0)
            tasks.add(new TaskMove(timeBack, false, 0.15));
        return tasks;
    }

}
