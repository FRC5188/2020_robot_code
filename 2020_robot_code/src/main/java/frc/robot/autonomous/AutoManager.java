/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autonomous;

import java.util.Map;
import java.util.Queue;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardComponent;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SendableRegistry;
import frc.robot.Robot;
import frc.robot.autonomous.utils.AutoTask;
import frc.robot.autonomous.utils.AutoTaskGroups;
import frc.robot.autonomous.utils.StringArraySendable;
import frc.robot.autonomous.utils.TaskState;

/**
 * Add your docs here.
 */
public class AutoManager {

    Queue<AutoTask> tasks;
    AutoRequestHandler reqHandler;
    int timeCount = 0;
    boolean started = false;

    // Shuffleboard Fields
    ShuffleboardTab tab;
    SendableChooser<AutoTaskGroups> autoSelectorSendable;
    AutoTaskGroups lastTaskGroup;
    StringArraySendable taskGroupDescriptionSendable;

    SimpleWidget delayWidget;
    //ShuffleboardComponent taskGroupImage;

    public void init(Robot robot) {
        tasks = autoSelectorSendable.getSelected().getTaskGroup().retrieveTasks(); // Currently arbitrary, but can be changed
        reqHandler = new AutoRequestHandler(robot);
        AutoTask currentTask = tasks.peek();
        currentTask.init();
    }

    public void periodic() {
        if(!this.started) {
            timeCount += 1;
            if(timeCount < delayWidget.getEntry().getNumber(0).doubleValue()/60.0)
                return;
            this.started = true;
        }
        if(!DriverStation.getInstance().isAutonomous()) {
            if(tasks.isEmpty()) return;
            if(tasks.peek().isFinished()) return;
            tasks.peek().cancel();
            return;
        }
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

    public String[] listAutos() {
        String[] arr = new String[AutoTaskGroups.values().length];
        for(int i = 0; i < arr.length; i++) {
          arr[i] = AutoTaskGroups.values()[i].getName();
        }
        return arr;
    }

    public void initShuffleboard() {
        Sendable taskGroupsSendable = new StringArraySendable(listAutos());
        this.taskGroupDescriptionSendable = new StringArraySendable(new String[] {"Select an Auto to see its description."});
        SendableRegistry.add(taskGroupsSendable, "AutoListSendable");
        SendableRegistry.add(this.taskGroupDescriptionSendable, "DescriptionSendable");
        ShuffleboardTab tab = Shuffleboard.getTab("Autonomous");
        tab.add("Available Autos",taskGroupsSendable)
        .withPosition(0, 1)
        .withSize(2, 3);
        this.autoSelectorSendable = new SendableChooser<AutoTaskGroups>();
        for(int i = 0; i < AutoTaskGroups.values().length; i++) {
            this.autoSelectorSendable.addOption( AutoTaskGroups.values()[i].getName(), AutoTaskGroups.values()[i]);
        }
        tab.add("Select Auto", this.autoSelectorSendable)
        .withPosition(0, 0)
        .withSize(2, 1);
        tab.add("Auto Description", this.taskGroupDescriptionSendable)
            .withPosition(2, 0)
            .withSize(2, 4);
        this.delayWidget = tab
            .add("Delay Until Start",0.0)
            .withPosition(4, 0)
            .withSize(1,1);
            //.withWidget(BuiltInWidgets.kNumberSlider)
            //.withProperties(Map.of("min", 0, "max", 15));
        /*
        BuiltInWidgets.kCameraStream
        this.taskGroupImage = tab
            .add("Auto Map Image", this.taskGroupDescriptionSendable)
            .withPosition(4,0)
            .withSize(4,4);
        */
            // End debugging
    }

    public void updateShuffleboard() {
        AutoTaskGroups taskGroup = (AutoTaskGroups) this.autoSelectorSendable.getSelected();
        if(this.lastTaskGroup == taskGroup || taskGroup == null) return;
        taskGroupDescriptionSendable.setStringArray(taskGroup.getDescription());
        
        this.lastTaskGroup = taskGroup;

    }
    /*
    Cut from sample code:
    // TODO: Debugging Shuffleboard, Remove this eventually
  public static String[] getStringArray() {
    String[] arr = new String[AutoTaskGroups.values().length];
    for(int i = 0; i < arr.length; i++) {
      arr[i] = AutoTaskGroups.values()[i].getName();
    }
    return arr;
  }
  private class PointSendable implements Sendable {
    @Override
    public void initSendable(SendableBuilder builder) {
      builder.setSmartDashboardType("Point2D");
      // https://www.chiefdelphi.com/t/shuffleboard-custom-data-type/377045
      builder.addStringArrayProperty("names", () -> getStringArray(), null);
    }
  }

  @Override
  public void robotInit() {
    //subsystems.add(dt);
    autoManager.init(this);

    //shuffle board entrys to update pid values

    // TODO: remove debugging stuff
    // Debugging Shuffleboard custom plugin stuff.
    this.ntInst = NetworkTableInstance.getDefault();
    this.table = ntInst.getTable("SmartDashboard");
    Sendable sendable = new PointSendable();
    SendableRegistry.add(sendable, "PointSendable");
    Shuffleboard.getTab("SmartDashboard")
      .add("Point2D",sendable);
    Shuffleboard.getTab("SmartDashboard")
      .add("Item", "Testing");
    // End debugging
    */
}
