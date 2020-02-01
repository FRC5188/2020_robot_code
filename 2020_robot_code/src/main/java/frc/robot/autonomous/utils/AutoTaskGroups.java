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

    DEFAULT(new TaskGroupDefault(), "Default"), 
    SHOOT_TURN(new TaskGroupShoot(), "Shoot Turn");

    private TaskGroup taskGroup;
    private String friendlyName;

    private AutoTaskGroups(TaskGroup taskGroup, String friendlyName) {
        this.taskGroup = taskGroup;
        this.friendlyName = friendlyName;
    }

	public TaskGroup getTaskGroup() {
		return this.taskGroup;
    }
    
    public String getName() {
        return friendlyName;
    }

}
