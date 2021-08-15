package frc.robot.motorfactory

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX
import edu.wpi.first.wpilibj.SpeedController

class MotorFactoryVictor : MotorFactory {
  override fun create(channelMain: Int, channel1: Int, channel2: Int): SpeedController {
    val motorMain = WPI_VictorSPX(channelMain)
    val motor1 = WPI_VictorSPX(channel1)
    val motor2 = WPI_VictorSPX(channel2)
    motor1.follow(motorMain)
    motor2.follow(motorMain)
    return motorMain
  }
}