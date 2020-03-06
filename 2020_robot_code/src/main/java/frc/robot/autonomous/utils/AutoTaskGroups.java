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

    DEFAULT_MOVE(new TaskGroupDefaultMove(2000, 0.15, true), "Default Move", new String[]{ 
        "Default Auto",
        "Moves forward at 0.15 throttle",
        "for 2 seconds.",
        "Max delay: 13s"
    }), 
    MOVE_EXTRLONG(new TaskGroupDefaultMove(4000, 0.15, true), "Move ExtrLong", new String[]{ 
        "Move Long",
        "Moves forward for 4 seconds",
        "Max delay: 11s"
    }),
    MOVE_BACK(new TaskGroupDefaultMove(2000, 0.15, false), "Move Back", new String[]{ 
        "Move Back",
        "Moves backwards for 2 seconds",
        "Max delay: 13s"
    }),
    MOVE_EXTRLONG_BACK(new TaskGroupDefaultMove(4000, 0.15, false), "Move Back ExtrLong", new String[]{ 
        "Move Back Long",
        "Moves backwards for 4 seconds",
        "Max delay: 11s"
    }),
    DEFAULT_SHOOT(new TaskGroupMoveShoot(2000, 5000, 2000), "Default Move & Shoot", new String[]{ 
        "Default Shoot",
        "Moves forward, shoot,",
        "move backward.",
        "2s forward, 6s shoot, 2s back",
        "Max delay: 5s"
    }),
    SHOOT_MOVE_BACK(new TaskGroupMoveShoot(0, 5000, 3000), "Shoot Move Back", new String[] {
        "Shoot Move Back",
        "Shoot for 6 seconds, move back 3s",
        "Max delay: 6s"
    }),
    SHOOT_LONG(new TaskGroupMoveShoot(3000, 5000, 3000), "Move Long & Shoot", new String[]{ 
        "Move Long & Shoot",
        "Moves forward, shoot,",
        "move backward.",
        "Note: 3s forward/back",
        "3s forward, 6s shoot, 3s back",
        "Max delay: 3s"
    }),
    SHOOT_LONG_NO_BACK(new TaskGroupMoveShoot(3000, 5000, 0), "Move Long & Shoot (No Back)", new String[]{ 
        "Move Long & Shoot",
        "Moves forward and shoot.",
        "Note: 3s forward, no back",
        "3s forward, 6s shoot",
        "max delay: 6s"
    });
    
    /*
    SHOOT_TURN(new TaskGroupShoot(), "Shoot Turn", new String[] {
        "Shoot Turn",
        "Moves off the line by 1 foot",
        "Turns toward the target",
        "Shoots the balls"
    });*/

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
