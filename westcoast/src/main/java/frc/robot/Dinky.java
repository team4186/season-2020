package frc.robot;

import edu.wpi.first.wpilibj2.command.button.*;
import frc.subsystems.drive.motorfactory.*;
import frc.subsystems.vision.targeting.*;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj.drive.*;
import frc.commands.ballhandling.*;
import edu.wpi.first.wpilibj.*;
import frc.subsystems.drive.*;
import frc.subsystems.vision.*;
import frc.subsystems.*;
import frc.maps.*;

public class Dinky extends TimedRobot {

  // Robot Map
  private final RobotMap map = new DinkyMap();

  // Drivetrain
  MotorFactory hybridFactory = new MotorFactoryHybrid();
  private final SpeedController leftMain = hybridFactory.create(2, 1, 3);
  private final SpeedController rightMain = hybridFactory.create(5, 4 ,6);
  private final DifferentialDrive drive = new DifferentialDrive(leftMain, rightMain);

  // Subsystems
  private final BallHandlingSubsystem ballHandler = new BallHandlingSubsystem(map);

  // Vision
  private final VisionRunner vision = new RioVisionRunner();

  // Inputs
  private final Joystick joystick = new Joystick(0);
  private final JoystickButton topTrigger = new JoystickButton(joystick, 1);
  private final JoystickButton bottomTrigger = new JoystickButton(joystick, 6);
  // private final JoystickButton deepTrigger = new JoystickButton(joystick, 15);
  private final JoystickButton buttonA = new JoystickButton(joystick, 3);
  // private final JoystickButton buttonB = new JoystickButton(joystick, 4);
  private final JoystickButton buttonC = new JoystickButton(joystick, 5);
  private final JoystickButton buttonD = new JoystickButton(joystick, 7);
  
  // Commands
  private final TeleopDrive teleop = new TeleopDrive(map, drive, joystick);

  @Override
  public void robotInit() {
    drive.setSafetyEnabled(false);

    vision.init();
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void teleopInit() {
    teleop.cancel();

    CommandScheduler.getInstance().registerSubsystem(ballHandler);

    final Command ballIn = new IntakeAndIndex(ballHandler);
    final Command ballOut = new IntakeOut(ballHandler);
    final Command spitOut = new EverythingOut(ballHandler);
    final Command shoot = new ShooterLogic(null);
    final Command align = new AlignToTarget(map, drive, vision);

    // topTrigger.whenPressed(ballIn);
    topTrigger.whenPressed(align);
    bottomTrigger.whileHeld(ballOut);
    buttonA.cancelWhenPressed(ballIn);
    buttonC.whenPressed(shoot);
    buttonD.whileHeld(spitOut);


    // teleop.schedule();
  }

  @Override
  public void teleopPeriodic() {
    CommandScheduler.getInstance().run();
  }
}
