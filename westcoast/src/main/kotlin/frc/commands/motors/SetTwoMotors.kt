package frc.commands.motors

import edu.wpi.first.wpilibj.SpeedController
import edu.wpi.first.wpilibj2.command.CommandBase

class SetTwoMotors(
    private val mainMotor: SpeedController,
    private val reversibleMotor: SpeedController,
    private val speed: Double,
    private val inverted: Boolean = false
) : CommandBase() {

  override fun initialize() {
    reversibleMotor.inverted = inverted
  }

  override fun execute() {
    mainMotor.set(speed)
    reversibleMotor.set(speed)
  }

  override fun end(interrupted: Boolean) {
    mainMotor.stopMotor()
    reversibleMotor.stopMotor()
  }

  override fun isFinished(): Boolean {
    return false
  }
}