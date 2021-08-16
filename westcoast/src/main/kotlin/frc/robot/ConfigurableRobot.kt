package frc.robot

import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.CommandScheduler
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.commands.auto.centerAutonomous
import frc.commands.auto.flexAuto
import frc.commands.auto.loadingBayAutonomous
import frc.commands.auto.targetAutonomous
import frc.robot.data.Data

class ConfigurableRobot(
    private val config: Data,
) : TimedRobot() {
  // Autonomous Commands
  private val autonomousChooser = SendableChooser<Command>()
  private lateinit var autonomous: Command
  private val spinnyCommand: Command = SequentialCommandGroup(
      config.commands.drive.findTarget(),
      config.commands.drive.alignToTarget()
  )

  override fun robotInit() {
    config.subsystems.driveTrain.initialize()

    with(autonomousChooser) {
      setDefaultOption("Target", config.targetAutonomous(-3.0))
      addOption("Center", config.centerAutonomous(-3.0, -30.0))
      addOption("LoadingBay", config.loadingBayAutonomous(-3.0, -40.0))
      addOption("SpinFast", spinnyCommand)
      addOption("Flex on Mr. Felipe", config.flexAuto())

      SmartDashboard.putData("Autonomous Mode", this)
    }
  }

  override fun simulationInit() {
  }

  override fun disabledInit() {
  }

  override fun disabledPeriodic() {
  }

  override fun robotPeriodic() {
    CommandScheduler.getInstance().run()
  }

  override fun autonomousInit() {
    CommandScheduler.getInstance().cancelAll()

    autonomous = autonomousChooser.selected
    autonomous.schedule()
  }

  override fun autonomousPeriodic() {
  }

  override fun teleopInit() {
    CommandScheduler.getInstance().cancelAll()

    config.compoundCommands.intakeAndIndex.let {
      config.input.intake.whenPressed(it)
      config.input.cancelIntake.whenPressed(it)
    }
    config.input.reverseIntake.whenPressed(config.commands.magazine.intakeOut())
    config.input.ejectAll.whileHeld(config.commands.magazine.everythingOut())

    config.input.shoot.toggleWhenPressed(config.compoundCommands.shoot)

    config
        .commands
        .teleop
        .raw()
        .schedule()
  }

  override fun teleopPeriodic() {
  }
}