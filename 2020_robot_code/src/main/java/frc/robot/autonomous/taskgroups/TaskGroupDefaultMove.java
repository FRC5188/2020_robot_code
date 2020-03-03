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
public class TaskGroupDefaultMove  extends TaskGroup {

    int time;
    double speed;

    public TaskGroupDefaultMove() {
        this.time = 50;
        this.speed = 0.3;
    }

    public TaskGroupDefaultMove(int time, double speed) {
        this.time = time;
        this.speed = speed;
    }

    @Override
    public Queue<AutoTask> retrieveTasks() {
        if(!tasks.isEmpty()) return tasks;
        tasks.add(new TaskMove(time, true, speed));
        return tasks;
    }

}
