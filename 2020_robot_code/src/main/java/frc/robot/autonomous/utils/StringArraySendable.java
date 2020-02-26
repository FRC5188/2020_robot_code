package frc.robot.autonomous.utils;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class StringArraySendable implements Sendable {

    String[] arr;

    public StringArraySendable(String[] arr) {
        this.arr = arr;
    }

    public String[] getStringArray() {
        return this.arr;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
      builder.setSmartDashboardType("Point2D"); // TODO: Change Point2D to StringArray
      builder.addStringArrayProperty("names", () -> getStringArray(), null);
    }

	public void setStringArray(String[] arr) {
        this.arr = arr;
	}
    
  }