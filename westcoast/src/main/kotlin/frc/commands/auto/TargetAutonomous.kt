package frc.commands.auto

import edu.wpi.first.wpilibj.Encoder
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitCommand
import frc.commands.drive.LeaveLine
import frc.robot.maps.RobotMap
import frc.subsystems.MagazineSubsystem
import frc.vision.VisionRunner
import frc.commands.targeting.AlignToTarget

class TargetAutonomous(
    map: RobotMap,
    drive: DifferentialDrive,
    leftEncoder: Encoder,
    rightEncoder: Encoder,
    distance: Double,
    vision: VisionRunner?,
    ballHandler: MagazineSubsystem?
) : SequentialCommandGroup(
    LeaveLine(map, drive, leftEncoder, rightEncoder, distance),
    WaitCommand(1.0),
    AlignToTarget(map, drive, vision!!)
)