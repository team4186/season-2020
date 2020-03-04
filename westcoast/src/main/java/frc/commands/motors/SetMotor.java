package frc.commands.motors;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetMotor extends CommandBase {
  private SpeedController motor;
  private double value;

  public SetMotor(
    SpeedController motor,
    double value
  ) {
    this.motor = motor;
    this.value = value;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    motor.set(value);
  }

  @Override
  public void end(boolean interrupted) {
    motor.stopMotor();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
