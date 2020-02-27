/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autonomous.utils;

import frc.robot.autonomous.taskgroups.*;

/**
 * Add your docs here.
 */
public enum AutoTaskGroups {

    DEFAULT(new TaskGroupDefault(), "Default", new String[]{ 
        "Default Auto",
        "Moves off the line by 1 foot"
    }), 
    SHOOT_TURN(new TaskGroupShoot(), "Shoot Turn", new String[] {
        "Shoot Turn",
        "Moves off the line by 1 foot",
        "Turns toward the target",
        "Shoots the balls"
    });

    private TaskGroup taskGroup;
    private String friendlyName;
    private String[] description;

    private AutoTaskGroups(TaskGroup taskGroup, String friendlyName, String[] description) {
        this.taskGroup = taskGroup;
        this.friendlyName = friendlyName;
        this.description = description;
    }

	public TaskGroup getTaskGroup() {
		return this.taskGroup;
    }
    
    public String getName() {
        return this.friendlyName;
    }

	public String[] getDescription() {
		return this.description;
	}

	public String[] getSerializedTaskList() {
        StringBuilder builder = new StringBuilder();
        for(AutoTask task: this.taskGroup.retrieveTasks()) {
            builder.append(task.serialize() + "\n");
        }
        return builder.toString().split("\n");
	}

}
