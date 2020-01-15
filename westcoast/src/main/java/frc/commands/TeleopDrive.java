package frc.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TeleopDrive extends CommandBase {
  private DifferentialDrive drive;
  private Joystick joy;

/**
 * Driving :) xD uwu owo plz use this documentation plz notice me senpai oh god oh fuck.
 * @param drive The drivetrain.
 * @param joystick The joystick.
 */

  public TeleopDrive(
    DifferentialDrive drive,
    Joystick joystick
  ) {
    this.drive = drive;
    this.joy = joystick;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    drive.arcadeDrive(attenuate(joy.getY()), attenuate(-joy.getX()), false);
  }

  @Override
  public void end(boolean interrupted) {
    drive.stopMotor();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
  
  private double attenuate(double value) {
    double v = value;
    boolean raw = joy.getRawButton(5);
    if(raw == true){ 
        return (0.5*v);
    }
    else{
        return (Math.signum(v) * Math.pow(Math.abs(v), 1.2));
    }
}
}
