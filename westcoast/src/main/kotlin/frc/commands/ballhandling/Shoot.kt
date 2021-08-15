package frc.commands.ballhandling

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.subsystems.BallHandlingSubsystem

class Shoot(
    private val ball: BallHandlingSubsystem
) : CommandBase() {
  override fun initialize() {
    ball.shooterTune()
  }

  override fun execute() {
    ball.runShooter(0.78)
    ball.runmagMotor(0.3)
    ball.runindexMotor(0.25)
  }

  override fun end(interrupted: Boolean) {
    ball.stopMotors()
    ball.resetIndexCount()
  }

  override fun isFinished(): Boolean {
    return false
  }

  init {
    addRequirements(ball)
  }
}