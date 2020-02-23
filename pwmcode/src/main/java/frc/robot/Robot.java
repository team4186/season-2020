package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.commands.*;

public class Robot extends TimedRobot {

  // Joystick
  Joystick joystick = new Joystick(0);
  Joystick joystick2 = new Joystick(1);
  
  // Drivetrain
  Spark rightMotor = new Spark(0);
  Spark leftMotor = new Spark(1);
  DifferentialDrive drive = new DifferentialDrive(leftMotor, rightMotor);
  
  // Commands
  TankDrive teleop = new TankDrive(drive, joystick, joystick2);

  @Override
  public void robotInit() {
  }

  @Override
  public void teleopInit() {
    teleop.schedule(false);
  }

  @Override
  public void teleopPeriodic() {
    CommandScheduler.getInstance().run();
  }
}
