package frc.commands.targeting

import edu.wpi.first.wpilibj.controller.PIDController
import edu.wpi.first.wpilibj2.command.PIDCommand
import frc.subsystems.DriveTrainSubsystem

class StayOnTarget(
    controller: PIDController,
    private val drive: DriveTrainSubsystem,
) : PIDCommand(
    controller,
    { drive.vision.alignX },
    0.0,
    { drive.arcade(0.0, it.coerceIn(-0.4, 0.4), false) },
    drive
) {

  override fun end(interrupted: Boolean) {
    drive.stop()
  }
}
