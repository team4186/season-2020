package frc.commands.auto

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitCommand
import frc.robot.data.Data

fun Data.square(size: Double) =
    SequentialCommandGroup(
        commands.drive.leaveLine(size),
        WaitCommand(1.0),
        commands.drive.perfectTurn(90.0),
        WaitCommand(1.0),

        commands.drive.leaveLine(size),
        WaitCommand(1.0),
        commands.drive.perfectTurn(90.0),
        WaitCommand(1.0),

        commands.drive.leaveLine(size),
        WaitCommand(1.0),
        commands.drive.perfectTurn(90.0),
        WaitCommand(1.0),

        commands.drive.leaveLine(size),
        WaitCommand(1.0),
        commands.drive.perfectTurn(90.0),
    )