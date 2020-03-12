package frc.robot.autonomous.taskgroups;

import java.util.Queue;

import frc.robot.autonomous.tasks.TaskTurn;
import frc.robot.autonomous.utils.AutoTask;
import frc.robot.autonomous.utils.TaskGroup;

public class TaskGroupTurn extends TaskGroup{

private double angle;

    public TaskGroupTurn(double angle) {
        this.angle = angle;
	}

	@Override
    public Queue<AutoTask> retrieveTasks() {
    if(!tasks.isEmpty()) return tasks;
    this.tasks.add(new TaskTurn(angle));
    return tasks;
    }
}