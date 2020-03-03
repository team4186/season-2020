package frc.commands.BallHandlers;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.subsystems.BallHandlingSubsystem;

public class IntakeAndIndex extends SequentialCommandGroup {
  private final BallHandlingSubsystem ball;

  public IntakeAndIndex(BallHandlingSubsystem ball) {
    addCommands(
      new IntakeLogic(ball),
      new IndexLogic(ball)
    );
    this.ball = ball;
  }

  @Override
  public void end(boolean interrupted) {
    ball.stopMotors();
  }
}
