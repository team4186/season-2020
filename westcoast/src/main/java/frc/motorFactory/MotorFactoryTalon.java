package frc.motorFactory;

import edu.wpi.first.wpilibj.SpeedController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class MotorFactoryTalon implements MotorFactory {
    public SpeedController create(int channelMain, int channel1, int channel2) {
        WPI_TalonSRX motorMain = new WPI_TalonSRX(channelMain);
        WPI_TalonSRX motor1 = new WPI_TalonSRX(channel1);
        WPI_TalonSRX motor2 = new WPI_TalonSRX(channel2);
 
        motor1.follow(motorMain);
        motor2.follow(motorMain);

        return motorMain;
    }    
}