package frc.subsystems.targeting

import edu.wpi.first.wpilibj.controller.PIDController
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.math.Maths.clamp
import frc.robot.maps.RobotMap
import frc.subsystems.VisionRunner

class StayOnTarget(
    private val map: RobotMap,
    private val drive: DifferentialDrive,
    private val vision: VisionRunner
) : CommandBase() {
  private var pid: PIDController? = null
  override fun initialize() {
    pid = map.makeStayOnTargetPIDs()
  }

  override fun execute() {
    val value = vision.alignX
    val turn = clamp(pid!!.calculate(value, 0.0), 0.4)
    drive.arcadeDrive(0.0, turn, false)
  }

  override fun end(interrupted: Boolean) {
    pid!!.reset()
    drive.stopMotor()
  }

  override fun isFinished(): Boolean {
    return false
  }
}