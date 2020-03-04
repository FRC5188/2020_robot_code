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

    DEFAULT_MOVE(new TaskGroupDefaultMove(2000, 0.15, true), "Default Move (Timed Move)", new String[]{ 
        "Default Auto",
        "Moves forward at 0.15 throttle",
        "for 2 seconds."
    }), 
    TIMED_MOVE_SEMIFAST_QUICK(new TaskGroupDefaultMove(1000,0.3, true), "Move Semi-Fast Quick", new String[]{ 
        "Timed Move Fast Quick",
        "Moves forward at 0.3 throttle",
        "for 1 second."
    }), 
    TIMED_MOVE_SLOW_QUICK(new TaskGroupDefaultMove(1000, 0.15, true), "Move Slow Quick", new String[]{ 
        "Timed Move Slow Quick",
        "Moves forward at 0.15 throttle",
        "for 1 second."
    }), 
    TIMED_MOVE_SEMIFAST(new TaskGroupDefaultMove(2000, 0.3, true), "Move Semi-Fast", new String[]{ 
        "Timed Move Semi-Fast",
        "Moves forward at 0.3 throttle",
        "for 2 seconds."
    }), 
    TIMED_MOVE_SEMIFAST_LONG(new TaskGroupDefaultMove(3000, 0.3, true), "Move Semi-Fast Long", new String[]{ 
        "Timed Move Semi-Fast Long",
        "Moves forward at 0.3 throttle",
        "for 3 seconds."
    }), 
    TIMED_MOVE_SLOW_LONG(new TaskGroupDefaultMove(3000, 0.15, true), "Move Slow Long", new String[]{ 
        "Timed Move Slow Long",
        "Moves forward at 0.15 throttle",
        "for 3 seconds."
    }),
    TIMED_MOVE_SEMIFAST_QUICK_BACKWARDS(new TaskGroupDefaultMove(1000,0.3, false), "Move Semi-Fast Quick Backwards", new String[]{ 
        "Timed Move Fast Quick Backwards",
        "Moves backwards at 0.3 throttle",
        "for 1 second."
    }), 
    TIMED_MOVE_SLOW_QUICK_BACKWARDS(new TaskGroupDefaultMove(1000, 0.15, false), "Move Slow Quick Backwards", new String[]{ 
        "Timed Move Slow Quick Backwards",
        "Moves backwards at 0.15 throttle",
        "for 1 second."
    }), 
    TIMED_MOVE_SEMIFAST_BACKWARDS(new TaskGroupDefaultMove(2000, 0.3, false), "Move Semi-Fast Backwards", new String[]{ 
        "Timed Move Semi-Fast Backwards",
        "Moves backwards at 0.3 throttle",
        "for 2 second."
    }), 
    TIMED_MOVE_SEMIFAST_LONG_BACKWARDS(new TaskGroupDefaultMove(3000, 0.3, false), "Move Semi-Fast Long Backwards", new String[]{ 
        "Timed Move Semi-Fast Long",
        "Moves backwards at 0.3 throttle",
        "for 3 second."
    }), 
    TIMED_MOVE_SLOW_LONG_BACKWARDS(new TaskGroupDefaultMove(3000, 0.15, false), "Move Slow Long Backwards", new String[]{ 
        "Timed Move Slow Long Backwards",
        "Moves backwards at 0.15 throttle",
        "for 3 second."
    }), 
    TIMED_MOVE_SLOW_BACKWARDS(new TaskGroupDefaultMove(2000, 0.15, false), "Move Slow Backwards", new String[]{ 
        "Timed Move Slow Long Backwards",
        "Moves backwards at 0.15 throttle",
        "for 2 second."
    }),
    RUN_SHOOTER(new TaskGroupShoot(5000), "Run Shooter", new String[] {
        "Run Shooter",
        "Run the shooter for 5 seconds",
        "If shooter isn't up,",
        "it will auto lift up."
    });
    ;
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
