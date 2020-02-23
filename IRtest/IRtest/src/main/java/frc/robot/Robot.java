package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
  // Sensor
  DigitalInput indexSensor = new DigitalInput(0);
  
  // Joystick
  Joystick joystick = new Joystick(0);

  // Drivetrain
  Spark rightMotor = new Spark(0);
  Spark leftMotor = new Spark(1);
  DifferentialDrive drive = new DifferentialDrive(leftMotor, rightMotor);

  DriveCommand driveCommand = new DriveCommand(joystick, drive);
  @Override
  public void robotInit() {
   
  }

  
  @Override
  public void robotPeriodic() {
     boolean trigger = indexSensor.get();
    SmartDashboard.putBoolean("IndexSensor", trigger);
  }

  
  @Override
  public void autonomousInit() {
   
  }

 
  @Override
  public void autonomousPeriodic() {
    
  }

  @Override
  public void teleopInit() {
    driveCommand.start();
  }
  
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void testPeriodic() {
  }
}
