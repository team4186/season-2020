package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.SPI;
import frc.autonomousCommands.*;
import frc.commands.*;
import frc.motorFactory.*;

public class Robot extends TimedRobot {

  // Drivetrain
  MotorFactory hybridFactory = new MotorFactoryHybrid();
  private final SpeedController leftMain = hybridFactory.create(2, 1, 3);
  private final SpeedController rightMain = hybridFactory.create(5, 4 ,6);
  private final DifferentialDrive drive = new DifferentialDrive(leftMain, rightMain);

  // Subsystem Motors
  private final WPI_VictorSPX intake = new WPI_VictorSPX(7);
  private final WPI_TalonSRX shooter1 = new WPI_TalonSRX(8);
  private final WPI_TalonSRX shooter2 = new WPI_TalonSRX(9);
  private final SpeedControllerGroup shooter = new SpeedControllerGroup(shooter1, shooter2);

  // Sensors
  private final AHRS ahrs = new AHRS(SPI.Port.kMXP);
  private final Encoder leftEncoder = new Encoder(0, 1);
  private final Encoder rightEncoder = new Encoder(3, 2);

  // Inputs
  private final Joystick joystick = new Joystick(0);
  private final JoystickButton buttonA = new JoystickButton(joystick, 3);
  private final JoystickButton buttonB = new JoystickButton(joystick, 4);
  private final JoystickButton topTrigger = new JoystickButton(joystick, 1);
  private final JoystickButton bottomTrigger = new JoystickButton(joystick, 6);
  
  // Commands
  private final TeleopDrive teleop = new TeleopDrive(drive, joystick);
  // private final EncoderDrive teleop = new EncoderDrive(drive, joystick, leftEncoder, rightEncoder);
  // private final GyroDrive teleop = new GyroDrive(drive, joystick, ahrs);

  // Autonomous Commands
  private final AVeryMarkCommand auton = new AVeryMarkCommand(drive, rightEncoder, leftEncoder);
  // private final Autonomous auton = new Autonomous(drive, ahrs, rightEncoder, leftEncoder, 10, 90);
  
  @Override
  public void robotInit() {
    joystick.setTwistChannel(4);
    drive.setSafetyEnabled(false);
    shooter2.setInverted(true);
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
    
    teleop.schedule();
  }

  @Override
  public void teleopPeriodic() {
    CommandScheduler.getInstance().run();

    // topTrigger.whenHeld(new SetMotor(intake, -1));
    // bottomTrigger.whenHeld(new SetMotor(intake, 1));
    topTrigger.whenHeld(new SetMotor(shooter, 1));
  }
}
