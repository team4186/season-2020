package frc.motorFactory;

import edu.wpi.first.wpilibj.SpeedController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class MotorFactoryHybrid implements MotorFactory {
    public SpeedController create(int channelMain, int channel1, int channel2) {
        WPI_TalonSRX motorMain = new WPI_TalonSRX(channelMain);
        WPI_VictorSPX motor1 = new WPI_VictorSPX(channel1);
        WPI_VictorSPX motor2 = new WPI_VictorSPX(channel2);
 
        motor1.follow(motorMain);
        motor2.follow(motorMain);

        motorMain.configPeakCurrentLimit(20);
        motorMain.configContinuousCurrentLimit(18);
        motorMain.configPeakCurrentDuration(45);
        motorMain.enableCurrentLimit(true);     

        return motorMain;
    }    
}