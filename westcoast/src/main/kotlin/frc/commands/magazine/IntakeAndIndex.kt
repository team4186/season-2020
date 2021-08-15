package frc.commands.magazine

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitCommand
import frc.subsystems.MagazineSubsystem

class IntakeAndIndex(ball: MagazineSubsystem) : SequentialCommandGroup() {
  private val ball: MagazineSubsystem
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