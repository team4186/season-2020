package frc.commands.drive

import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj2.command.CommandBase
import edu.wpi.first.wpilibj2.command.button.Button
import frc.subsystems.DriveTrainSubsystem
import kotlin.math.pow
import kotlin.math.sign

class TeleopDrive(
    private val forward: Double,
    private val joystick: Joystick,
    private val attenuate: Button,
    private val drive: DriveTrainSubsystem,
) : CommandBase() {

  private var processRef = ::full

  init {
    addRequirements(drive)
  }

  override fun initialize() {
    attenuate
        .whenPressed(Runnable { processRef = ::attenuated })
        .whenReleased(Runnable { processRef = ::full })
  }

  override fun execute() {
    val process = processRef // NOTE commiting to current processRef value
    drive.arcade(process(forward * joystick.y), process(-forward * joystick.x))
  }

  override fun end(interrupted: Boolean) {
    drive.stop()
  }


  private fun full(value: Double): Double = sign(value) * value.pow(2.0)
  private fun attenuated(value: Double): Double = 0.5 * value
}