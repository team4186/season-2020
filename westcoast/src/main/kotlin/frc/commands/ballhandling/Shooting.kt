package frc.commands.ballhandling

import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitCommand
import frc.robot.maps.RobotMap
import frc.subsystems.BallHandlingSubsystem
import frc.subsystems.VisionRunner
import frc.subsystems.targeting.AlignToTarget
import frc.subsystems.targeting.StayOnTarget

class Shooting(map: RobotMap?, drive: DifferentialDrive?, vision: VisionRunner?, ballHandler: BallHandlingSubsystem) : SequentialCommandGroup() {
  init {
    addCommands(
        AlignToTarget(map!!, drive!!, vision!!),
        race(
            ShooterAccelerator(ballHandler),
            WaitCommand(1.5)
        ),
        race(
            Shoot(ballHandler),
            StayOnTarget(map, drive, vision),
            WaitCommand(4.0)
        )
    )
    addRequirements(ballHandler)
  }
}