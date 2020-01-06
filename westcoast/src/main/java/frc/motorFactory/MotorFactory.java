package frc.motorFactory;

import edu.wpi.first.wpilibj.SpeedController;

public interface MotorFactory {
    SpeedController create(int channelMain, int channel1, int channel2);
}