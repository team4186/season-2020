package frc.commands.motors

import edu.wpi.first.wpilibj.SpeedController
import edu.wpi.first.wpilibj2.command.CommandBase

class DistanceMotor(
    private val motor: SpeedController,
    private val speed: Double,
    time: Double
) : CommandBase() {
  private var i = 0
  private var end = false
  private val time: Double
  override fun initialize() {
    i = 0
    end = false
  }

  override fun execute() {
    if (i <= time) {
      motor.set(speed)
      i = i + 1
    }
    else {
      end = true
    }
    println(i)
  }

  override fun end(interrupted: Boolean) {
    motor.stopMotor()
    i = 0
  }

  override fun isFinished(): Boolean {
    return end
  }

  init {
    this.time = time * 50
  }
}