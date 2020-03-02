package frc.commands.BallHandlers;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.subsystems.BallHandlingSubsystem;

public class IntakeAndIndex extends SequentialCommandGroup {

  public IntakeAndIndex(BallHandlingSubsystem ball) {
    addCommands(
      new IntakeLogic(ball),
      new IndexLogic(ball)
    );
  }
}
