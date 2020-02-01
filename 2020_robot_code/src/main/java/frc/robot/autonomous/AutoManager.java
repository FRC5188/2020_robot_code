/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autonomous;

import java.util.Queue;

import frc.robot.Robot;
import frc.robot.autonomous.utils.AutoTask;
import frc.robot.autonomous.utils.AutoTaskGroups;
import frc.robot.autonomous.utils.TaskState;

/**
 * Add your docs here.
 */
public class AutoManager {

    Queue<AutoTask> tasks;
    AutoRequestHandler reqHandler;

    public void init(Robot robot) {
        tasks = AutoTaskGroups.DEFAULT.getTaskGroup().retrieveTasks(); // Currently arbitrary, but can be changed
        reqHandler = new AutoRequestHandler(robot);
        AutoTask currentTask = tasks.peek();
        currentTask.init();
    }

    public void periodic() {
        reqHandler.startPeriodic();
        AutoTask currentTask = tasks.peek(); // Get task, but don't remove it
        if(currentTask == null) return; // Error, no tasks left to do
        TaskState state = currentTask.periodic();
        if(state.equals(TaskState.FINISHED) || state.equals(TaskState.CANCELLED)) {
            tasks.poll().end(); // poll = get task and remove it. end is just an AutoTask method
            AutoTask nextTask = tasks.peek();
            if(nextTask != null)
                nextTask.init();
        }
        reqHandler.endPeriodic();
    }

}
