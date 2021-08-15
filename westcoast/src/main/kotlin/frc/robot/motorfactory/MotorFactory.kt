package frc.robot.motorfactory

import edu.wpi.first.wpilibj.SpeedController

interface MotorFactory {
  fun create(channelMain: Int, channel1: Int, channel2: Int): SpeedController
}