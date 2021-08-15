package frc.robot

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX
import com.kauailabs.navx.frc.AHRS
import edu.wpi.first.wpilibj.Encoder
import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.SPI
import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.CommandScheduler
import edu.wpi.first.wpilibj2.command.button.JoystickButton
import frc.commands.auto.CenterAutonomous
import frc.commands.auto.LoadingBayAutonomous
import frc.commands.auto.TargetAutonomous
import frc.commands.motors.SetMotor
import frc.robot.maps.ClinkyMap
import frc.robot.maps.RobotMap
import frc.subsystems.BallHandlingSubsystem
import frc.subsystems.drive.GyroDrive
import frc.subsystems.drive.motorfactory.MotorFactory
import frc.subsystems.drive.motorfactory.MotorFactoryHybrid
import frc.subsystems.RioVisionRunner
import frc.subsystems.VisionRunner

class Clinky : TimedRobot() {
  // Robot Map
  private val map: RobotMap = ClinkyMap()

  // Drivetrain
  var hybridFactory: MotorFactory = MotorFactoryHybrid()
  private val leftMain = hybridFactory.create(2, 1, 3)
  private val rightMain = hybridFactory.create(5, 4, 6)
  private val drive = DifferentialDrive(leftMain, rightMain)

  // Subsystem Motors
  private val intake = WPI_VictorSPX(7)

  // private final WPI_TalonSRX leftShooter = new WPI_TalonSRX(8);
  // private final WPI_TalonSRX rightShooter = new WPI_TalonSRX(9);
  private val ballHandler = BallHandlingSubsystem(map)

  // Sensors
  private val ahrs = AHRS(SPI.Port.kMXP)
  private val leftEncoder = Encoder(0, 1)
  private val rightEncoder = Encoder(3, 2)

  // Vision
  private val vision: VisionRunner = RioVisionRunner()

  // Inputs
  private val joystick = Joystick(0)
  private val topTrigger = JoystickButton(joystick, 1)

  // private final JoystickButton bottomTrigger = new JoystickButton(joystick, 6);
  // private final JoystickButton buttonA = new JoystickButton(joystick, 3);
  // private final JoystickButton buttonB = new JoystickButton(joystick, 4);
  // Commands
  private val teleop = GyroDrive(map, drive, joystick, ahrs)

  // Autonomous Commands
  private val autonomousChooser = SendableChooser<Command>()
  private var autonomous: Command? = null
  private val autonTarget = TargetAutonomous(map, drive, leftEncoder, rightEncoder, 3.0, vision, ballHandler)
  private val autonCenter = CenterAutonomous(map, drive, leftEncoder, rightEncoder, 3.0, 30.0, vision, ballHandler)
  private val autonBay = LoadingBayAutonomous(map, drive, leftEncoder, rightEncoder, 3.0, -40.0, vision, ballHandler)
  override fun robotInit() {
    drive.isSafetyEnabled = false
    autonomousChooser.setDefaultOption("Target", autonTarget)
    autonomousChooser.setDefaultOption("Center", autonCenter)
    autonomousChooser.setDefaultOption("LoadingBay", autonBay)
    SmartDashboard.putData("Autonomous Mode", autonomousChooser)
  }

  override fun robotPeriodic() {}
  override fun autonomousInit() {
    CommandScheduler.getInstance().cancelAll()
    autonomous = autonomousChooser.selected
    ahrs.reset()
    leftEncoder.reset()
    rightEncoder.reset()
    autonomous?.schedule()
  }

  override fun autonomousPeriodic() {
    CommandScheduler.getInstance().run()
  }

  override fun teleopInit() {
    autonomous!!.cancel()
    teleop.cancel()
    ahrs.reset()
    leftEncoder.reset()
    rightEncoder.reset()
    teleop.schedule()
    topTrigger.whileHeld(SetMotor(intake, 0.4))
  }

  override fun teleopPeriodic() {
    CommandScheduler.getInstance().run()
  }
}