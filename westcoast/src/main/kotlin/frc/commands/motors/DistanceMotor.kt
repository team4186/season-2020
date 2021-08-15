package frc.commands.motors

import edu.wpi.first.wpilibj.SpeedController
import edu.wpi.first.wpilibj2.command.CommandBase

class DistanceMotor(
    private val motor: SpeedController,
    private val speed: Double,
    private val time: Double,
    private val timeMultiplier: Double = 50.0,
) : CommandBase() {
  private var frame = 0
  override fun initialize() {
    frame = 0
  }

  override fun execute() {
    if (!isFinished) {
      motor.set(speed)
      frame += 1
    }
    println(frame)
  }

  override fun end(interrupted: Boolean) {
    motor.stopMotor()
  }

  override fun isFinished(): Boolean {
    return frame > time * timeMultiplier
  }
}