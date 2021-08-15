package frc.subsystems.drive

import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.maps.RobotMap
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sign

class TeleopDrive(
    private val map: RobotMap,
    private val drive: DifferentialDrive,
    private val joy: Joystick
) : CommandBase() {
  private var direction = 0.0
  override fun initialize() {
    direction = if (map.reversed) -1.0 else 1.0
  }

  override fun execute() {
    drive.arcadeDrive(attenuate(direction * joy.y), attenuate(-direction * joy.x))
  }

  override fun end(interrupted: Boolean) {
    drive.stopMotor()
  }

  override fun isFinished(): Boolean {
    return false
  }

  private fun attenuate(value: Double): Double {
    val raw = joy.getRawButton(5)
    return if (raw) {
      0.5 * value
    }
    else {
      sign(value) * abs(value).pow(2.0)
    }
  }
}