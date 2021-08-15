package frc.robot.data

import frc.commands.drive.LeaveLine
import frc.commands.drive.PerfectTurn

data class Parameters(
    val leaveLineDistanceMultiplier: DoubleParameter<LeaveLine> = 1.0.param(),
    val perfectTurnAngleMultiplier: DoubleParameter<PerfectTurn> = 1.0.param(),
)
