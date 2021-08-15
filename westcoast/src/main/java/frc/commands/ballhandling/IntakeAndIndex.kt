package frc.commands.ballhandling

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitCommand
import frc.subsystems.BallHandlingSubsystem

class IntakeAndIndex(ball: BallHandlingSubsystem) : SequentialCommandGroup() {
  private val ball: BallHandlingSubsystem
  override fun end(interrupted: Boolean) {
    println("Ball Implemented!")
    ball.stopMotors()
  }

  init {
    addCommands(
        IntakeLogic(ball),
        WaitCommand(0.2),
        IndexLogic(ball)
    )
    this.ball = ball
    addRequirements(ball)
  }
}