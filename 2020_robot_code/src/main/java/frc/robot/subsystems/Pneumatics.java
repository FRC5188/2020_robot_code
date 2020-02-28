package frc.robot.subsystems;

import frc.robot.Subsystem;
import edu.wpi.first.wpilibj.Compressor;

public class Pneumatics implements Subsystem{
    private Compressor compressor;
    
    //constructor
    public Pneumatics(){
        compressor = new Compressor();
        compressor.setClosedLoopControl(true);
        compressor.start();
    }
    public void init() {

    }

    @Override
    public void initShuffle() {

    }

    @Override
    public void operate() {
       
    }

    @Override
    public void test() {
        
    }

    @Override
    public void updateShuffle() {
        
    }

    @Override
    public void kill() {
        
    }

}
