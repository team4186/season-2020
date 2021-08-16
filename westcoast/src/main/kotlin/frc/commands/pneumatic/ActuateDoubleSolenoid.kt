package frc.commands.pneumatic

import edu.wpi.first.wpilibj.DoubleSolenoid
import edu.wpi.first.wpilibj2.command.CommandBase

class ActuateDoubleSolenoid(
    private val solenoid: DoubleSolenoid,
    private val direction: DoubleSolenoid.Value,
    private val endDirection: DoubleSolenoid.Value
) : CommandBase() {
  override fun execute() {
    solenoid.set(direction)
  }

  override fun end(interrupted: Boolean) {
    solenoid.set(endDirection)
  }
}