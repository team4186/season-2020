package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj.*;
import frc.subsystems.vision.*;
import frc.subsystems.drive.*;
import frc.maps.*;

public class Janky extends TimedRobot {

  //Robot Map
  private final RobotMap map = new JankyMap();

  // Drivetrain
  private final Spark rightMotor = new Spark(0);
  private final Spark leftMotor = new Spark(1);
  private final DifferentialDrive drive = new DifferentialDrive(leftMotor, rightMotor);

  // Inputs
  private final Joystick joystick = new Joystick(0);
  
  // Commands
  private final TeleopDrive teleop = new TeleopDrive(map, drive, joystick);

  //Vision
  // private final AlignToTarget align = new AlignToTarget(drive);
  private final LimelightRunner lime = new LimelightRunner();

  @Override
  public void robotInit() {
    drive.setSafetyEnabled(false);
  }

  @Override
  public void robotPeriodic() {
    SmartDashboard.putBoolean("Target Lock", lime.hasTarget());
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