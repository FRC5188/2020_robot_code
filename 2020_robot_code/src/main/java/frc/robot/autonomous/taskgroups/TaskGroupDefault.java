/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autonomous.taskgroups;

import java.util.Queue;

import frc.robot.autonomous.tasks.TaskTurn;
import frc.robot.autonomous.utils.AutoTask;
import frc.robot.autonomous.utils.TaskGroup;

/**
 * Add your docs here.
 */
public class TaskGroupDefault  extends TaskGroup {

    @Override
    public Queue<AutoTask> retrieveTasks() {
        tasks.add(new TaskTurn(135.0));
        return tasks;
    }

}