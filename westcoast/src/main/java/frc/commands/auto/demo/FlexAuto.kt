package frc.commands.auto.demo

import edu.wpi.first.wpilibj.Encoder
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitCommand
import frc.commands.auto.LeaveLine
import frc.commands.auto.PerfectTurn
import frc.robot.maps.RobotMap
import frc.subsystems.BallHandlingSubsystem
import frc.subsystems.vision.VisionRunner
import frc.subsystems.vision.targeting.AlignToTarget

class FlexAuto(
    map: RobotMap,
    drive: DifferentialDrive,
    leftEncoder: Encoder,
    rightEncoder: Encoder,
    vision: VisionRunner?,
    ballHandler: BallHandlingSubsystem?
) : SequentialCommandGroup(
    LeaveLine(map, drive, leftEncoder, rightEncoder, -1.0),
    WaitCommand(0.0),
    PerfectTurn(map, drive, leftEncoder, rightEncoder, 180.0),
    WaitCommand(0.0),
    LeaveLine(map, drive, leftEncoder, rightEncoder, 9.0),
    WaitCommand(0.0),
    PerfectTurn(map, drive, leftEncoder, rightEncoder, 180.0),
    WaitCommand(0.0),
    AlignToTarget(map, drive, vision!!)
)