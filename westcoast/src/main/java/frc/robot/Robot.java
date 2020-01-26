package frc.robot;

import com.ctre.phoenix.motorcontrol.can.*;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.button.*;
import frc.autonomousCommands.*;
import frc.commands.*;
import frc.motorFactory.*;
import frc.vision.GripPipeline;

public class Robot extends TimedRobot {

  // Drivetrain
  MotorFactory hybridFactory = new MotorFactoryHybrid();
  private final SpeedController leftMain = hybridFactory.create(2, 1, 3);
  private final SpeedController rightMain = hybridFactory.create(5, 4 ,6);
  private final DifferentialDrive drive = new DifferentialDrive(leftMain, rightMain);

  // Subsystem Motors
  private final WPI_VictorSPX intake = new WPI_VictorSPX(7);
  private final WPI_TalonSRX leftShooter = new WPI_TalonSRX(8);
  private final WPI_TalonSRX rightShooter = new WPI_TalonSRX(9);
  // private final SpeedControllerGroup shooter = new SpeedControllerGroup(leftShooter, rightShooter);

  // Sensors
  private final AHRS ahrs = new AHRS(SPI.Port.kMXP);
  private final Encoder leftEncoder = new Encoder(0, 1);
  private final Encoder rightEncoder = new Encoder(3, 2);

  // Vision
  private final GripPipeline pipeline = new GripPipeline();
  private final NetworkTable filteredContours = NetworkTableInstance.getDefault().getTable("GRIP/filteredReport");

  // Inputs
  private final Joystick joystick = new Joystick(0);
  private final JoystickButton buttonA = new JoystickButton(joystick, 3);
  private final JoystickButton buttonB = new JoystickButton(joystick, 4);
  private final JoystickButton topTrigger = new JoystickButton(joystick, 1);
  private final JoystickButton bottomTrigger = new JoystickButton(joystick, 6);
  
  // Commands
  private final GyroDrive teleop = new GyroDrive(drive, joystick, ahrs);
  // private final TeleopDrive teleop = new TeleopDrive(drive, joystick);
  // private final EncoderDrive teleop = new EncoderDrive(drive, joystick, leftEncoder, rightEncoder);

  // Autonomous Commands
  private final AVeryMarkCommand auton = new AVeryMarkCommand(drive, rightEncoder, leftEncoder);
  // private final Autonomous auton = new Autonomous(drive, ahrs, rightEncoder, leftEncoder, 10, 90);
  
  @Override
  public void robotInit() {
    drive.setSafetyEnabled(false);
    UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(0);
    camera.setResolution(320, 240);
  }

  @Override
  public void robotPeriodic() {

  }

  @Override
  public void autonomousInit() {
    auton.cancel();

    ahrs.reset();
    leftEncoder.reset();
    rightEncoder.reset();

    auton.schedule();
  }

  @Override
  public void autonomousPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    auton.cancel();
    teleop.cancel();

    ahrs.reset();
    leftEncoder.reset();
    rightEncoder.reset();
    
    // teleop.schedule();
  }

  @Override
  public void teleopPeriodic() {
    CommandScheduler.getInstance().run();

    // topTrigger.whileHeld(new SetTwoMotors(joystick, leftShooter, rightShooter));
  }
}
