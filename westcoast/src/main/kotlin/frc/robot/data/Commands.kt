package frc.robot.data

import edu.wpi.first.wpilibj2.command.Command
import frc.commands.drive.GyroDrive
import frc.commands.drive.LeaveLine
import frc.commands.drive.PerfectTurn
import frc.commands.drive.TeleopDrive
import frc.commands.magazine.*
import frc.commands.targeting.AlignToTarget
import frc.commands.targeting.FindTarget
import frc.commands.targeting.StayOnTarget

data class Commands(
    val teleop: TeleopCommands,
    val drive: DriveCommands,
    val magazine: MagazineCommands,
    val shooter: ShooterCommands,
)

data class TeleopCommands(
    val raw: () -> TeleopDrive,
    val assisted: () -> GyroDrive,
)

data class DriveCommands(
    val leaveLine: (distance: Double) -> LeaveLine,
    val perfectTurn: (angle: Double) -> PerfectTurn,
    val alignToTarget: () -> AlignToTarget,
    val stayOnTarget: () -> StayOnTarget,
    val findTarget: () -> FindTarget,
)

data class MagazineCommands(
    val index: () -> IndexLogic,
    val intake: () -> IntakeLogic,

    val intakeOut: () -> IntakeOut,
    val everythingOut: () -> EverythingOut
)

data class ShooterCommands(
    val shoot: () -> Shoot,
    val shooterAccelerator: () -> ShooterAccelerator,
)

data class Compound(
    val shoot: Command,
    val intakeAndIndex: Command,
)
