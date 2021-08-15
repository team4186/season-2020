package frc.commands.auto

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.data.Data

fun Data.flexAuto() =
    SequentialCommandGroup(
        commands.drive.leaveLine(-1.0),
        commands.drive.perfectTurn(180.0),
        commands.drive.leaveLine(9.0),
        commands.drive.perfectTurn(180.0),
        commands.drive.alignToTarget(),
    )