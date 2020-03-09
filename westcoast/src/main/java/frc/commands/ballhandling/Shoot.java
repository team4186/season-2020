package frc.commands.ballhandling;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.subsystems.BallHandlingSubsystem;

public class Shoot extends CommandBase {
  private BallHandlingSubsystem ball;

  public Shoot(
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
    ball.runShooter(0.78);
    ball.runsyncMagdex(0.5);
  }

  @Override
  public void end(boolean interrupted) {
    ball.stopMotors();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
