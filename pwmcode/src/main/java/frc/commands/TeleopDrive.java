package frc.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TeleopDrive extends CommandBase {
private final DifferentialDrive drive;
  private final Joystick joystick;

  public TeleopDrive(
    DifferentialDrive dry,
    Joystick joy
  ) {
    this.drive = dry;
    this.joystick = joy;
}

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    drive.arcadeDrive(-joystick.getY(), joystick.getX());
  }

  @Override
  public void end(boolean interrupted) {
    drive.stopMotor();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
