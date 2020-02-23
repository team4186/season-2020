package frc.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TankDrive extends CommandBase {
  private final DifferentialDrive tankDrive;
  private final Joystick rightJoy;
  private final Joystick leftJoy;

  public TankDrive(
    DifferentialDrive dry,
    Joystick joyRight,
    Joystick joyLeft
  ) {
    this.tankDrive = dry;
    this.rightJoy = joyRight;
    this.leftJoy = joyLeft;
  }
  
  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    tankDrive.tankDrive(leftJoy.getY(), rightJoy.getY());
  }

  @Override
  public void end(boolean interrupted) {
    tankDrive.stopMotor ();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
