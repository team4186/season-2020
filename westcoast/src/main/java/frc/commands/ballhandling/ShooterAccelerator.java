package frc.commands.ballhandling;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.subsystems.BallHandlingSubsystem;

public class ShooterAccelerator extends CommandBase {
  private BallHandlingSubsystem ball;
  private double time;

  public ShooterAccelerator(
    BallHandlingSubsystem ballHandler
  ) {
    this.ball = ballHandler;
    addRequirements(ball);
  }

  @Override
  public void initialize() {
    time = System.currentTimeMillis() + 1000;
    ball.shooterTune();
  }

  @Override
  public void execute() {
    ball.runShooter(0.78);
    ball.getCurrentLevels();
  }

  @Override
  public void end(boolean interrupted) {
    ball.stopMotors();
    ball.setShooterPercent();
  }

  @Override
  public boolean isFinished() {
    return System.currentTimeMillis() >= time;
  }
}
