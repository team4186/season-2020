package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.commands.ballhandling.EverythingOut;
import frc.commands.ballhandling.IntakeAndIndex;
import frc.commands.ballhandling.IntakeOut;
import frc.commands.ballhandling.Shooting;
import frc.robot.maps.DinkyMap;
import frc.robot.maps.RobotMap;
import frc.subsystems.BallHandlingSubsystem;
import frc.subsystems.drive.TeleopDrive;
import frc.subsystems.drive.motorfactory.MotorFactory;
import frc.subsystems.drive.motorfactory.MotorFactoryHybrid;
import frc.subsystems.vision.RioVisionRunner;
import frc.subsystems.vision.VisionRunner;

public class Dinky extends TimedRobot {

    // Robot Map
    private final RobotMap map = new DinkyMap();

    // Drivetrain
    MotorFactory hybridFactory = new MotorFactoryHybrid();
    private final SpeedController leftMain = hybridFactory.create(2, 1, 3);
    private final SpeedController rightMain = hybridFactory.create(5, 4, 6);
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
        vision.periodic();
    }

    @Override
    public void teleopInit() {
        teleop.cancel();

        CommandScheduler.getInstance().registerSubsystem(ballHandler);

        final Command ballIn = new IntakeAndIndex(ballHandler);
        final Command ballOut = new IntakeOut(ballHandler);
        final Command spitOut = new EverythingOut(ballHandler);
        final Command shoot = new Shooting(map, drive, vision, ballHandler);

        topTrigger.whenPressed(ballIn);
        bottomTrigger.whileHeld(ballOut);
        buttonA.cancelWhenPressed(ballIn);
        buttonC.toggleWhenPressed(shoot);
        buttonD.whileHeld(spitOut);

        teleop.schedule();
    }

    @Override
    public void teleopPeriodic() {
        CommandScheduler.getInstance().run();
    }
}
