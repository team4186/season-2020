package frc.commands.motors

import edu.wpi.first.wpilibj.SpeedController
import edu.wpi.first.wpilibj2.command.CommandBase

class SetMotor(
    private val motor: SpeedController,
    private val value: Double
) : CommandBase() {
  override fun initialize() {}
  override fun execute() {
    motor.set(value)
  }

  override fun end(interrupted: Boolean) {
    motor.stopMotor()
  }

  override fun isFinished(): Boolean {
    return false
  }
}