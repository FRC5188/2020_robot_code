/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autonomous.tasks;

import frc.robot.autonomous.utils.AutoTask;
import frc.robot.autonomous.utils.TaskState;

/**
 * Add your docs here.
 */
public class TaskTurn extends AutoTask {

	double angle;
	Runnable callbackFinished;

	public TaskTurn(double ang) {
		this.angle = ang;
	}

	public TaskTurn(double ang, Runnable callbackFinished) {
		this.angle = ang;
		this.callbackFinished = callbackFinished;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TaskState periodic() {
		// TODO Auto-generated method stub
		return this.state;
	}

	@Override
	public void initShuffle() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		if(this.callbackFinished != null)
		this.callbackFinished.run();
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateShuffle() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
}
