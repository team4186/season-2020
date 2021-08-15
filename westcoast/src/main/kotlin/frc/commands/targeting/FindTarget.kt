package frc.commands.targeting

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.subsystems.DriveTrainSubsystem

class FindTarget(
    private val drive: DriveTrainSubsystem
) : CommandBase() {
  override fun initialize() {}
  override fun execute() {
    drive.tank(
        left = -0.35,
        right = 0.35
    )
  }

  override fun isFinished(): Boolean = drive.vision.hasTarget
}
