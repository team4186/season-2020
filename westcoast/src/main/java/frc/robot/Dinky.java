package frc.robot;

import edu.wpi.first.wpilibj2.command.button.*;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj.drive.*;
import com.kauailabs.navx.frc.AHRS;
import frc.commands.BallHandlers.*;
import edu.wpi.first.wpilibj.*;
import frc.motorFactory.*;
import frc.subsystems.*;
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

  // Subsystems
  private final BallHandlingSubsystem ballHandler = new BallHandlingSubsystem(map);

  // Inputs
  private final Joystick joystick = new Joystick(0);
  private final JoystickButton topTrigger = new JoystickButton(joystick, 1);
  private final JoystickButton bottomTrigger = new JoystickButton(joystick, 6);
  // private final JoystickButton deepTrigger = new JoystickButton(joystick, 15);
  private final JoystickButton buttonA = new JoystickButton(joystick, 3);
  // private final JoystickButton buttonB = new JoystickButton(joystick, 4);
  // private final JoystickButton buttonC = new JoystickButton(joystick, 5);
  
  // Commands
  private final TeleopDrive teleop = new TeleopDrive(map, drive, joystick);

  @Override
  public void robotInit() {
    drive.setSafetyEnabled(false);
  }

  @Override
  public void robotPeriodic() {

  }

  @Override
  public void teleopInit() {
    teleop.cancel();

    final Command ballIn = new IntakeAndIndex(ballHandler);

    topTrigger.whenPressed(ballIn);
    bottomTrigger.whileHeld(() -> ballHandler.runintakeMotor(0.4));
    buttonA.cancelWhenPressed(ballIn);

    // teleop.schedule();
  }

  @Override
  public void teleopPeriodic() {
    CommandScheduler.getInstance().run();
  }
}
