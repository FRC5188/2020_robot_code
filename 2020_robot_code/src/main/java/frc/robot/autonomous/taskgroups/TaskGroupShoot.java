/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autonomous.taskgroups;

import java.util.Queue;

import frc.robot.autonomous.tasks.ParallelTask;
import frc.robot.autonomous.tasks.TaskMove;
import frc.robot.autonomous.tasks.TaskTurn;
import frc.robot.autonomous.utils.AutoTask;
import frc.robot.autonomous.utils.TaskGroup;

/**
 * Add your docs here.
 */
public class TaskGroupShoot extends TaskGroup {

    @Override
    public Queue<AutoTask> retrieveTasks() {
        tasks.add(new TaskTurn(90.0));
        tasks.add(new TaskMove(0.5));
        tasks.add(new TaskTurn(-80.0));
        tasks.add(new ParallelTask(
            new TaskMove(1.0),
            new TaskTurn(90) 
        ));
        
        return tasks;
    }

}
