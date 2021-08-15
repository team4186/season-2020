package frc.commands.auto

import edu.wpi.first.wpilibj.Encoder
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitCommand
import frc.commands.auto.demo.Square
import edu.wpi.first.wpilibj2.command.CommandBase
import edu.wpi.first.wpilibj.controller.ProfiledPIDController
import frc.robot.maps.RobotMap
import frc.subsystems.BallHandlingSubsystem
import frc.subsystems.vision.VisionRunner
import frc.subsystems.vision.targeting.AlignToTarget

class CenterAutonomous(
    map: RobotMap,
    drive: DifferentialDrive,
    leftEncoder: Encoder,
    rightEncoder: Encoder,
    distance: Double,
    angle: Double,
    vision: VisionRunner?,
    ballHandler: BallHandlingSubsystem?
) : SequentialCommandGroup() {
  init {
    addCommands(
        LeaveLine(map, drive, leftEncoder, rightEncoder, distance),
        WaitCommand(1.0),
        PerfectTurn(map, drive, leftEncoder, rightEncoder, angle),
        WaitCommand(1.0),
        AlignToTarget(map, drive, vision!!)
    )
  }
}