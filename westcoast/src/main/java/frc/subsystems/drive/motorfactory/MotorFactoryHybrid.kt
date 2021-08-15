package frc.subsystems.drive.motorfactory

import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX
import edu.wpi.first.wpilibj.SpeedController

class MotorFactoryHybrid : MotorFactory {
  override fun create(channelMain: Int, channel1: Int, channel2: Int): SpeedController {
    val motorMain = WPI_TalonSRX(channelMain)
    val motor1 = WPI_VictorSPX(channel1)
    val motor2 = WPI_VictorSPX(channel2)
    motor1.follow(motorMain)
    motor2.follow(motorMain)
    motorMain.configContinuousCurrentLimit(18)
    motorMain.configPeakCurrentLimit(20)
    motorMain.configPeakCurrentDuration(45)
    motorMain.enableCurrentLimit(true)
    motorMain.setNeutralMode(NeutralMode.Brake)
    return motorMain
  }
}