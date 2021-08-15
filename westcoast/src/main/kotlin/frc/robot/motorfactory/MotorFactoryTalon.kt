package frc.robot.motorfactory

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import edu.wpi.first.wpilibj.SpeedController

class MotorFactoryTalon : MotorFactory {
  override fun create(channelMain: Int, channel1: Int, channel2: Int): SpeedController {
    val motorMain = WPI_TalonSRX(channelMain)
    val motor1 = WPI_TalonSRX(channel1)
    val motor2 = WPI_TalonSRX(channel2)
    motor1.follow(motorMain)
    motor2.follow(motorMain)
    return motorMain
  }
}