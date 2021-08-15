package frc.commands.magazine

import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitCommand
import frc.robot.maps.RobotMap
import frc.subsystems.MagazineSubsystem
import frc.vision.VisionRunner
import frc.commands.targeting.AlignToTarget
import frc.commands.targeting.StayOnTarget

class Shooting(map: RobotMap?, drive: DifferentialDrive?, vision: VisionRunner?, ballHandler: MagazineSubsystem) : SequentialCommandGroup() {
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