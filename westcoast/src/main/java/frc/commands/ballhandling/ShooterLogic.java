package frc.commands.ballhandling;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.subsystems.BallHandlingSubsystem;

public class ShooterLogic extends CommandBase {
  private BallHandlingSubsystem ball;

  public ShooterLogic(
    BallHandlingSubsystem ballHandler
  ) {
    this.ball = ballHandler;
    addRequirements(ball);
  }

  @Override
  public void initialize() {
    ball.shooterTune();
  }

  @Override
  public void execute() {
    ball.runShooterPercent(60);
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
