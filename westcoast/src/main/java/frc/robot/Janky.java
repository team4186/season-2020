package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.commands.*;

public class Janky extends TimedRobot {

  // Drivetrain
  private final Spark rightMotor = new Spark(0);
  private final Spark leftMotor = new Spark(1);
  private final DifferentialDrive drive = new DifferentialDrive(leftMotor, rightMotor);

  // Inputs
  private final Joystick joystick = new Joystick(0);
  
  // Commands
  private final TeleopDrive teleop = new TeleopDrive(drive, joystick, true);

  @Override
  public void robotInit() {
    drive.setSafetyEnabled(false);
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void teleopInit() {
    teleop.schedule();
  }

  @Override
  public void teleopPeriodic() {
    CommandScheduler.getInstance().run();
  }
}