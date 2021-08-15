package frc.commands.magazine

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.subsystems.MagazineSubsystem

class ShooterAccelerator(
    private val ball: MagazineSubsystem
) : CommandBase() {
  override fun initialize() {
    ball.shooterTune()
  }

  override fun execute() {
    ball.runShooter(0.78)
    ball.currentLevels
  }

  override fun end(interrupted: Boolean) {
    ball.stopMotors()
    ball.setShooterPercent()
  }

  override fun isFinished(): Boolean {
    return false
  }

  init {
    addRequirements(ball)
  }
}