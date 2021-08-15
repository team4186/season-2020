package frc.robot.data

import frc.subsystems.DriveTrainSubsystem
import frc.subsystems.MagazineSubsystem

data class Subsystems(
    val driveTrain: DriveTrainSubsystem,
    val magazine: MagazineSubsystem
)