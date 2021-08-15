package frc.subsystems.targeting

import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.subsystems.VisionRunner

class FindTarget(
    private val drive: DifferentialDrive,
    private val vision: VisionRunner
) : CommandBase() {
  override fun initialize() {}
  override fun execute() {
    drive.tankDrive(-0.35, 0.35)
  }

  override fun end(interrupted: Boolean) {}
  override fun isFinished(): Boolean {
    return vision.hasTarget()
  }
}