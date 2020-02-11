package frc.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.SpeedController;

public class SetTwoMotors extends CommandBase {
  private SpeedController leftMotor;
  private SpeedController rightMotor;
  private double speed;

  public SetTwoMotors(
    SpeedController leftMotor,
    SpeedController rightMotor,
    double speed
  ) {
    this.leftMotor = leftMotor;
    this.rightMotor = rightMotor;
    this.speed = speed;
  }

  @Override
  public void initialize() {
    rightMotor.setInverted(true);
  }

  @Override
  public void execute() {  
    leftMotor.set(speed);// + 0.15 / (4 * speed));
    rightMotor.set(speed);
  }

  @Override
  public void end(boolean interrupted) {
    leftMotor.stopMotor();
    rightMotor.stopMotor();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
