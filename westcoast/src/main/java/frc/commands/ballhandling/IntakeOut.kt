package frc.commands.ballhandling

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.subsystems.BallHandlingSubsystem

class IntakeOut(
    private val ball: BallHandlingSubsystem
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