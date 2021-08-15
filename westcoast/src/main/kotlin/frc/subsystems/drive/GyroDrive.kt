package frc.subsystems.drive

import com.kauailabs.navx.frc.AHRS
import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.controller.PIDController
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.math.Maths.attenuate
import frc.math.Maths.joyclean
import frc.math.Maths.pidclean
import frc.robot.maps.RobotMap

class GyroDrive(
    private val map: RobotMap,
    private val drive: DifferentialDrive,
    private val joy: Joystick,
    private val ahrs: AHRS
) : CommandBase() {
  private var pid: PIDController? = null
  private var direction = 0.0
  override fun initialize() {
    direction = if (map.reversed) -1.0 else 1.0
    pid = map.makeDrivePIDs()
    ahrs.reset()
  }

  override fun execute() {
    val input = pidclean(ahrs.rate, 6.0, 0.3)
    val pidraw = pid!!.calculate(input, joyclean(direction * joy.x, 0.05) * 4.6)
    val output = pidclean(-pidraw, 0.8, 0.05)
    drive.arcadeDrive(attenuate(direction * joy.y), output, false)
  }

  override fun end(interrupted: Boolean) {
    drive.stopMotor()
    ahrs.reset()
    pid!!.reset()
  }

  override fun isFinished(): Boolean {
    return false
  }
}