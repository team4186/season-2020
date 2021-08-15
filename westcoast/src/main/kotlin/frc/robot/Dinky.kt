package frc.robot

import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.CommandScheduler
import edu.wpi.first.wpilibj2.command.button.JoystickButton
import frc.commands.magazine.EverythingOut
import frc.commands.magazine.IntakeAndIndex
import frc.commands.magazine.IntakeOut
import frc.commands.magazine.Shooting
import frc.robot.maps.DinkyMap
import frc.robot.maps.RobotMap
import frc.subsystems.MagazineSubsystem
import frc.commands.drive.TeleopDrive
import frc.robot.motorfactory.MotorFactory
import frc.robot.motorfactory.MotorFactoryHybrid
import frc.vision.RioVisionRunner
import frc.vision.VisionRunner

class Dinky : TimedRobot() {
  // Robot Map
  private val map: RobotMap = DinkyMap()

  // Drivetrain
  var hybridFactory: MotorFactory = MotorFactoryHybrid()
  private val leftMain = hybridFactory.create(2, 1, 3)
  private val rightMain = hybridFactory.create(5, 4, 6)
  private val drive = DifferentialDrive(leftMain, rightMain)

  // Subsystems
  private val ballHandler = MagazineSubsystem(map)

  // Vision
  private val vision: VisionRunner = RioVisionRunner()

  // Inputs
  private val joystick = Joystick(0)
  private val topTrigger = JoystickButton(joystick, 1)
  private val bottomTrigger = JoystickButton(joystick, 6)

  // private final JoystickButton deepTrigger = new JoystickButton(joystick, 15);
  private val buttonA = JoystickButton(joystick, 3)

  // private final JoystickButton buttonB = new JoystickButton(joystick, 4);
  private val buttonC = JoystickButton(joystick, 5)
  private val buttonD = JoystickButton(joystick, 7)

  // Commands
  private val teleop = TeleopDrive(map, drive, joystick)
  override fun robotInit() {
    drive.isSafetyEnabled = false
    vision.init()
  }

  override fun robotPeriodic() {
    vision.periodic()
  }

  override fun teleopInit() {
    teleop.cancel()
    CommandScheduler.getInstance().registerSubsystem(ballHandler)
    val ballIn: Command = IntakeAndIndex(ballHandler)
    val ballOut: Command = IntakeOut(ballHandler)
    val spitOut: Command = EverythingOut(ballHandler)
    val shoot: Command = Shooting(map, drive, vision, ballHandler)
    topTrigger.whenPressed(ballIn)
    bottomTrigger.whileHeld(ballOut)
    buttonA.cancelWhenPressed(ballIn)
    buttonC.toggleWhenPressed(shoot)
    buttonD.whileHeld(spitOut)
    teleop.schedule()
  }

  override fun teleopPeriodic() {
    CommandScheduler.getInstance().run()
  }
}