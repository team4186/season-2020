package frc.commands.auto

import edu.wpi.first.wpilibj.Encoder
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitCommand
import frc.robot.maps.RobotMap
import frc.subsystems.BallHandlingSubsystem
import frc.subsystems.VisionRunner
import frc.subsystems.targeting.AlignToTarget

class LoadingBayAutonomous(
    map: RobotMap,
    drive: DifferentialDrive,
    leftEncoder: Encoder,
    rightEncoder: Encoder,
    distance: Double,
    angle: Double,
    vision: VisionRunner?,
    ballHandler: BallHandlingSubsystem?
) : SequentialCommandGroup(
    LeaveLine(map, drive, leftEncoder, rightEncoder, distance),
    WaitCommand(1.0),
    PerfectTurn(map, drive, leftEncoder, rightEncoder, angle),
    WaitCommand(1.0),
    AlignToTarget(map, drive, vision!!)
)