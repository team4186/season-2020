package frc.commands.auto

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitCommand
import frc.robot.data.Data

fun Data.loadingBayAutonomous(distance: Double, angle: Double) =
    SequentialCommandGroup(
        commands.drive.leaveLine(distance),
        WaitCommand(1.0),
        commands.drive.perfectTurn(angle),
        WaitCommand(1.0),
        commands.drive.alignToTarget()
    )