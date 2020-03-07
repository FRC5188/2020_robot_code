/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autonomous.utils;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Add your docs here.
 */
public abstract class TaskGroup {

    protected Queue<AutoTask> tasks = new LinkedList<AutoTask>();

    public abstract Queue<AutoTask> retrieveTasks();

	public Queue<AutoTask> resetAndRetrieveTask() {
        tasks = new LinkedList<AutoTask>();
        return this.retrieveTasks();
	}

}
