package frc.robot;

import com.ctre.phoenix.motorcontrol.can.*;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.button.*;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.commands.*;
import frc.motorFactory.*;

public class Dinky extends TimedRobot {

  // Drivetrain
  MotorFactory hybridFactory = new MotorFactoryHybrid();
  private final SpeedController leftMain = hybridFactory.create(2, 1, 3);
  private final SpeedController rightMain = hybridFactory.create(5, 4 ,6);
  private final DifferentialDrive drive = new DifferentialDrive(leftMain, rightMain);

  // Subsystem Motors
  private final WPI_VictorSPX intake = new WPI_VictorSPX(7);
  private final WPI_TalonSRX mag = new WPI_TalonSRX(8);
  private final WPI_TalonSRX index = new WPI_TalonSRX(9);
  // private final StopAllMotors allmotors = new StopAllMotors(intake, mag, index);
  // private final WPI_TalonSRX leftShooter = new WPI_TalonSRX(8);
  // private final WPI_TalonSRX rightShooter = new WPI_TalonSRX(9);

  // Sensors  
  private final AHRS ahrs = new AHRS(SPI.Port.kMXP);

  // Inputs
  private final Joystick joystick = new Joystick(0);
  private final JoystickButton topTrigger = new JoystickButton(joystick, 1);
  private final JoystickButton bottomTrigger = new JoystickButton(joystick, 6);
  // private final JoystickButton deepTrigger = new JoystickButton(joystick, 15);
  // private final JoystickButton buttonA = new JoystickButton(joystick, 3);
  // private final JoystickButton buttonB = new JoystickButton(joystick, 4);
  private final JoystickButton buttonC = new JoystickButton(joystick, 5);
  
  // Commands
  private final TeleopDrive teleop = new TeleopDrive(drive, joystick);

  // Autonomous Commands

  @Override
  public void robotInit() {
    drive.setSafetyEnabled(false);
    ahrs.reset();
  }

  @Override
  public void robotPeriodic() {
  }

  /*@Override
  public void teleopInit() {
    teleop.schedule();
  }

  @Override
  public void teleopPeriodic() {
    CommandScheduler.getInstance().run();
  }*/

  @Override
  public void testInit(){
    teleop.cancel();
    teleop.schedule();

    topTrigger.whileHeld(new SetTwoMotors(intake, index, -0.4));
    bottomTrigger.whileHeld(new SetTwoMotors(mag, index, 0.5, true));
    buttonC.whileHeld(new SetTwoMotors(mag, index, -0.5, true));
    buttonC.whileHeld(new SetMotor(intake, 0.4));
  }

  @Override
  public void testPeriodic(){
    CommandScheduler.getInstance().run();
  }
}
