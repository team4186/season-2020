package frc.commands.motors

import edu.wpi.first.wpilibj.SpeedController
import edu.wpi.first.wpilibj.SpeedControllerGroup

class StopAllMotors @JvmOverloads constructor(
    motor1: SpeedController?,
    motor2: SpeedController?,
    motor3: SpeedController? = motor1
) {
  private val motors: SpeedControllerGroup
  fun stop() {
    motors.stopMotor()
  }

  init {
    motors = SpeedControllerGroup(motor1, motor2, motor3)
  }
}