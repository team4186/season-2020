package frc.commands.motors

import edu.wpi.first.wpilibj.SpeedController
import edu.wpi.first.wpilibj2.command.InstantCommand

class StopAllMotors constructor(
    private val motor1: SpeedController,
    private val motor2: SpeedController,
    private val motor3: SpeedController,
) : InstantCommand() {
  override fun execute() {
    motor1.stopMotor()
    motor2.stopMotor()
    motor3.stopMotor()
  }
}