package frc.commands.auto

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitCommand
import frc.robot.data.Data

fun Data.targetAutonomous(distance: Double) =
    SequentialCommandGroup(
        commands.drive.leaveLine(distance),
        WaitCommand(1.0),
        commands.drive.alignToTarget()
    )