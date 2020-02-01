package frc.robot;

import com.ctre.phoenix.motorcontrol.can.*;
import com.kauailabs.navx.frc.AHRS;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.vision.VisionThread;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.button.*;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.autonomousCommands.*;
import frc.commands.*;
import frc.math.DistanceToTarget;
import frc.motorFactory.*;
import frc.vision.*;

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

  // Sensors
  private final AHRS ahrs = new AHRS(SPI.Port.kMXP);
  private final Encoder leftEncoder = new Encoder(0, 1);
  private final Encoder rightEncoder = new Encoder(3, 2);

  // Vision
  private VisionThread visionThread;
  private final Object imgLock = new Object();
  private double centerX = 0.0;
  private double height = 0.0;

  // Inputs
  private final Joystick joystick = new Joystick(0);
  private final JoystickButton buttonA = new JoystickButton(joystick, 3);
  private final JoystickButton buttonB = new JoystickButton(joystick, 4);
  private final JoystickButton topTrigger = new JoystickButton(joystick, 1);
  private final JoystickButton bottomTrigger = new JoystickButton(joystick, 6);
  
  // Commands
  // private final GyroDrive teleop = new GyroDrive(drive, joystick, ahrs);
  private final TeleopDrive teleop = new TeleopDrive(drive, joystick);
  // private final EncoderDrive teleop = new EncoderDrive(drive, joystick, leftEncoder, rightEncoder);

  // Autonomous Commands
  // private final AVeryMarkCommand auton = new AVeryMarkCommand(drive, rightEncoder, leftEncoder);
  private final AlignToTarget auton = new AlignToTarget(drive, leftEncoder, rightEncoder,"CenterX");

  @Override
  public void robotInit() {
    drive.setSafetyEnabled(false);
    UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
    camera.setResolution(320, 240);
    
    visionThread = new VisionThread(camera, new GripPipeline(), pipeline -> {
      if (!pipeline.filterContoursOutput().isEmpty()) {
          Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
          synchronized (imgLock) {
              centerX = r.x + (r.width / 2);
              height = r.height;
          }
      }
  });
  visionThread.start();
  }

  @Override
  public void robotPeriodic() {
    double centerX;
    double height;
    synchronized(imgLock){
      centerX = this.centerX;
      height = this.height;
    }
    SmartDashboard.putNumber("CenterX", centerX);
    SmartDashboard.putNumber("Height", height);
    SmartDashboard.putNumber("Distance", DistanceToTarget.distance("Height"));
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
    
    teleop.schedule();
  }

  @Override
  public void teleopPeriodic() {
    CommandScheduler.getInstance().run();

    // topTrigger.whileHeld(new SetTwoMotors(joystick, leftShooter, rightShooter));
  }

  @Override
  public void testInit(){
    auton.cancel();
    teleop.cancel();

    ahrs.reset();
    leftEncoder.reset();
    rightEncoder.reset();
    
    teleop.schedule();
  }

  @Override
  public void testPeriodic(){
    CommandScheduler.getInstance().run();

    topTrigger.whileHeld(new SetMotor(intake, -0.8));
  }
}
