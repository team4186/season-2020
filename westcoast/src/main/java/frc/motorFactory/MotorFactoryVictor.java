package frc.motorFactory;

import edu.wpi.first.wpilibj.SpeedController;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class MotorFactoryVictor implements MotorFactory {
    public SpeedController create(int channelMain, int channel1, int channel2) {
        WPI_VictorSPX motorMain = new WPI_VictorSPX(channelMain);
        WPI_VictorSPX motor1 = new WPI_VictorSPX(channel1);
        WPI_VictorSPX motor2 = new WPI_VictorSPX(channel2);
 
        motor1.follow(motorMain);
        motor2.follow(motorMain);

        return motorMain;
    }
}