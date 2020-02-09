package frc.commands;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetMotor extends CommandBase {
  private SpeedController motor;
  private double value;
  private ThreadMotor threadMotor;

  public SetMotor(
    SpeedController motor,
    double value
  ) {
    this.motor = motor;
    this.value = value;
  }

  @Override
  public void initialize() {
    threadMotor = new ThreadMotor(motor, value);
    threadMotor.run();
  }

  @Override
  public void execute() {
  }

  @Override
  public void end(boolean interrupted) {
    threadMotor.interrupt();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
