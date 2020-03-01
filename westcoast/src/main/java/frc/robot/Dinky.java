package frc.robot;

import edu.wpi.first.wpilibj2.command.button.*;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj.drive.*;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.*;
import frc.motorFactory.*;
import frc.robotMaps.*;
import frc.commands.*;

public class Dinky extends TimedRobot {

  //Robot Map
  private final RobotMap map = new DinkyMap();

  // Drivetrain
  MotorFactory hybridFactory = new MotorFactoryHybrid();
  private final SpeedController leftMain = hybridFactory.create(2, 1, 3);
  private final SpeedController rightMain = hybridFactory.create(5, 4 ,6);
  private final DifferentialDrive drive = new DifferentialDrive(leftMain, rightMain);

  // Subsystem Motors
  private final WPI_VictorSPX intake = new WPI_VictorSPX(7);
  private final WPI_TalonSRX mag = new WPI_TalonSRX(8);
  private final WPI_TalonSRX index = new WPI_TalonSRX(9);
  private final VictorSP leftShooter = new VictorSP(10);
  private final VictorSP rightShooter = new VictorSP(12);
  // private final WPI_TalonSRX leftShooter = new WPI_TalonSRX(8);
  // private final WPI_TalonSRX rightShooter = new WPI_TalonSRX(9);

  // Sensors
  private final AHRS ahrs = new AHRS(SPI.Port.kMXP);

  // Inputs
  private final Joystick joystick = new Joystick(0);
  private final JoystickButton topTrigger = new JoystickButton(joystick, 1);
  private final JoystickButton bottomTrigger = new JoystickButton(joystick, 6);
  private final JoystickButton deepTrigger = new JoystickButton(joystick, 15);
  private final JoystickButton buttonA = new JoystickButton(joystick, 3);
  private final JoystickButton buttonB = new JoystickButton(joystick, 4);
  private final JoystickButton buttonC = new JoystickButton(joystick, 5);
  
  // topTrigger.whileHeld(new SetMotor(intake, -0.4));
  // bottomTrigger.whileHeld(new SetTwoMotors(mag, index, 0.5));
  // buttonA.whileHeld(new SetTwoMotors(index, intake, -0.5));
  // buttonB.whileHeld(new SetTwoMotors(mag, index, 0.4));
  // buttonC.whenPressed(new DistanceMotor(mag, 0.2, 0.5));

  // Commands
  // private final GyroDrive teleop = new GyroDrive(drive, joystick, ahrs);
  private final TeleopDrive teleop = new TeleopDrive(map, drive, joystick);
  // private final Command setIntake = new SetMotor(intake, -0.4);
  private final Command intakedex = new SetTwoMotors(intake, index, 0.5);
  private final Command ingazine = new SetTwoMotors(index, mag, 0.4);
  private final Command setShooter = new SetTwoMotors(leftShooter, rightShooter, -0.9, true);
  private final Command constFeedShot = new SetMotor(mag, 0.8);
  private final Command shoot = new SetTwoMotors(leftShooter, rightShooter, -0.8, true);

  // Autonomous Commands

  @Override
  public void robotInit() {
    drive.setSafetyEnabled(false);
    ahrs.reset();
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void teleopInit() {
    topTrigger.whileHeld(intakedex);
    bottomTrigger.whileHeld(ingazine);
    deepTrigger.whileHeld((setShooter.raceWith(new WaitCommand(0.5))).andThen(shoot.alongWith(constFeedShot)));
    // teleop.schedule();
  }

  @Override
  public void teleopPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void testInit(){
    // teleop.schedule();
  }

  @Override
  public void testPeriodic(){
    CommandScheduler.getInstance().run();

    topTrigger.whileHeld(new SetMotor(intake, -0.4));
    bottomTrigger.whileHeld(new SetTwoMotors(mag, index, 0.5));
    buttonA.whileHeld(new SetTwoMotors(index, intake, -0.5));
    buttonB.whileHeld(new SetTwoMotors(mag, index, 0.4));
    buttonC.whenPressed(new DistanceMotor(mag, 0.2, 0.5));
  }
}
