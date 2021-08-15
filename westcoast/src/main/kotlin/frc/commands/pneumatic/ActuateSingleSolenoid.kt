package frc.commands.pneumatic

import edu.wpi.first.wpilibj.Solenoid
import edu.wpi.first.wpilibj2.command.CommandBase

class ActuateSingleSolenoid(private val solenoid: Solenoid) : CommandBase() {
  override fun initialize() {}
  override fun execute() {
    solenoid.set(true)
  }

  override fun end(interrupted: Boolean) {
    solenoid.set(false)
  }

  override fun isFinished(): Boolean {
    return false
  }
}