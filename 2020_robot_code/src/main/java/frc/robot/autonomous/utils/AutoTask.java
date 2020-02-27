package frc.robot.autonomous.utils;

public abstract class AutoTask {

    public TaskState state = TaskState.UNKNOWN;
    protected boolean isFinished = false;

    public abstract void init();
    public abstract TaskState periodic();
    public abstract void initShuffle();
    public abstract void end();
    public abstract void cancel();
    public abstract void updateShuffle();
    public abstract boolean isFinished();
    public abstract String serialize();
}
