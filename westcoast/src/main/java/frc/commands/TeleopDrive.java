/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TeleopDrive extends CommandBase {
  private DifferentialDrive drive;
  private Joystick joy;

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
    drive.arcadeDrive(attenuate(joy.getY()), attenuate(joy.getTwist()), false);
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
