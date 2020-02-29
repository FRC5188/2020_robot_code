package frc.robot;

public interface Subsystem {

    public void init();

    public void initShuffle();

    public void operate();

    public void test();

    public void updateShuffle();

    public void kill();

}