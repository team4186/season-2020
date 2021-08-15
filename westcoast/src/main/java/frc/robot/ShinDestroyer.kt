package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.commands.auto.CenterAutonomous;
import frc.commands.auto.LoadingBayAutonomous;
import frc.commands.auto.TargetAutonomous;
import frc.commands.auto.demo.FlexAuto;
import frc.commands.motors.SetMotor;
import frc.robot.maps.RobotMap;
import frc.robot.maps.ShinDestroyerMap;
import frc.subsystems.BallHandlingSubsystem;
import frc.subsystems.drive.GyroDrive;
import frc.subsystems.drive.motorfactory.MotorFactory;
import frc.subsystems.drive.motorfactory.MotorFactoryHybrid;
import frc.subsystems.vision.LimelightRunner;
import frc.subsystems.vision.VisionRunner;
import frc.subsystems.vision.targeting.AlignToTarget;
import frc.subsystems.vision.targeting.FindTarget;

public class ShinDestroyer extends TimedRobot {

    // Robot Map
    private final RobotMap map = new ShinDestroyerMap();

    // Drivetrain
    MotorFactory hybridFactory = new MotorFactoryHybrid();
    private final SpeedController leftMain = hybridFactory.create(14, 13, 15);
    private final SpeedController rightMain = hybridFactory.create(2, 1, 3);
    private final DifferentialDrive drive = new DifferentialDrive(leftMain, rightMain);

    // Subsystem Motors
    private final WPI_VictorSPX intake = new WPI_VictorSPX(7);
    // private final WPI_TalonSRX leftShooter = new WPI_TalonSRX(8);
    // private final WPI_TalonSRX rightShooter = new WPI_TalonSRX(9);
    private final BallHandlingSubsystem ballHandler = new BallHandlingSubsystem(map);

    // Sensors
    private final AHRS ahrs = new AHRS(SPI.Port.kMXP);
    private final Encoder leftEncoder = new Encoder(9, 8);
    private final Encoder rightEncoder = new Encoder(6, 7);

    // Vision
    private final VisionRunner vision = new LimelightRunner();

    // Inputs
    private final Joystick joystick = new Joystick(0);
    private final JoystickButton topTrigger = new JoystickButton(joystick, 1);
    // private final JoystickButton bottomTrigger = new JoystickButton(joystick, 6);
    // private final JoystickButton buttonA = new JoystickButton(joystick, 3);
    // private final JoystickButton buttonB = new JoystickButton(joystick, 4);

    // Commands
    private final GyroDrive teleop = new GyroDrive(map, drive, joystick, ahrs);

    // Autonomous Commands
    private final SendableChooser<Command> autonomousChooser = new SendableChooser<>();
    private Command autonomous = new TargetAutonomous(map, drive, leftEncoder, rightEncoder, -3, vision, ballHandler);
    private final TargetAutonomous autonTarget = new TargetAutonomous(map, drive, leftEncoder, rightEncoder, -3, vision, ballHandler);
    private final CenterAutonomous autonCenter = new CenterAutonomous(map, drive, leftEncoder, rightEncoder, -3, -30, vision, ballHandler);
    private final LoadingBayAutonomous autonBay = new LoadingBayAutonomous(map, drive, leftEncoder, rightEncoder, -3, -40, vision, ballHandler);
    private final Command spinnyCommand = new SequentialCommandGroup(new FindTarget(drive, vision), new AlignToTarget(map, drive, vision));
    private final FlexAuto phlexAuto = new FlexAuto(map, drive, leftEncoder, rightEncoder, vision, ballHandler);

    @Override
    public void robotInit() {
        drive.setSafetyEnabled(false);

        leftEncoder.setDistancePerPulse(0.390625);
        rightEncoder.setDistancePerPulse(0.390625);

        autonomousChooser.setDefaultOption("Target", autonTarget);
        autonomousChooser.setDefaultOption("Center", autonCenter);
        autonomousChooser.setDefaultOption("LoadingBay", autonBay);
        autonomousChooser.setDefaultOption("SpinFast", spinnyCommand);
        autonomousChooser.setDefaultOption("Flex on Mr. Felipe", phlexAuto);
        SmartDashboard.putData("Autonomous Mode", autonomousChooser);
    }

    @Override
    public void robotPeriodic() {
    }

    @Override
    public void autonomousInit() {
        autonomous = autonomousChooser.getSelected();

        ahrs.reset();
        leftEncoder.reset();
        rightEncoder.reset();

        autonomous.schedule();
    }

    @Override
    public void autonomousPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        autonomous.cancel();
        teleop.cancel();

        ahrs.reset();
        leftEncoder.reset();
        rightEncoder.reset();

        teleop.schedule();
        topTrigger.whenPressed(new SetMotor(intake, 1));
    }

    @Override
    public void teleopPeriodic() {
        CommandScheduler.getInstance().run();
    }
}
