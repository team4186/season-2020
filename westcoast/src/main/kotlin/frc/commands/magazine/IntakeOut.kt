package frc.commands.magazine

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.subsystems.MagazineSubsystem

class IntakeOut(
    private val ball: MagazineSubsystem
) : CommandBase() {
  override fun initialize() {}
  override fun execute() {
    ball.runintakeMotor(-0.4)
  }

  override fun end(interrupted: Boolean) {
    ball.stopMotors()
  }

  override fun isFinished(): Boolean {
    return false
  }

  init {
    addRequirements(ball)
  }
}