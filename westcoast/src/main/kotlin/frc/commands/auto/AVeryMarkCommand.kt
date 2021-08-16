package frc.commands.auto

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.data.Data

fun Data.aVeryMarkCommand() =
    SequentialCommandGroup(
        square(10.0),
        square(10.0),
        square(10.0),
        square(10.0),
        square(10.0),
        square(10.0),
        square(10.0),
        square(10.0),
        square(10.0),
        square(10.0),
        square(10.0),
        square(10.0)
    )