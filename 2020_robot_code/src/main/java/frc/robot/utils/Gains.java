package frc.robot.utils;

public class Gains {

    public double kF;
    public double kP;
    public double kI;
    public double kD;
    public int kIzone;
    public double kPeakOutput;

    public Gains(double kF, double kP, double kI, double kD, int kIzone, double kPeakOutput) {
        this.kF = kF;
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kIzone = kIzone;
        this.kPeakOutput = kPeakOutput;
    }

    public Gains(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }

}