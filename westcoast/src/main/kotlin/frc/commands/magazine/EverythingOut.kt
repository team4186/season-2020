package frc.commands.magazine

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.subsystems.MagazineSubsystem

class EverythingOut(
    private val ball: MagazineSubsystem
) : CommandBase() {
  override fun initialize() {
    ball.resetIndexCount()
  }

  override fun execute() {
    ball.runintakeMotor(-0.4)
    ball.runindexMotor(-0.4)
    ball.runmagMotor(-0.4)
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