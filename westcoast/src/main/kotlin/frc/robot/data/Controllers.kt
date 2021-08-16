package frc.robot.data

import edu.wpi.first.wpilibj.controller.PIDController
import edu.wpi.first.wpilibj.controller.ProfiledPIDController
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile
import frc.commands.drive.GyroDrive
import frc.commands.drive.LeaveLine
import frc.commands.drive.PerfectTurn
import frc.commands.targeting.AlignToTarget
import frc.commands.targeting.StayOnTarget

data class Controllers(
    val gyroDrive: Provider<PIDController, GyroDrive> = Provider {
      PIDController(0.5, 0.15, 0.0).apply {
        setTolerance(0.5)
        disableContinuousInput()
      }
    },

    val leaveLine: Provider<ProfiledPIDController, LeaveLine> = Provider {
      ProfiledPIDController(
          0.1,
          0.0,
          0.0,
          TrapezoidProfile.Constraints(600.0, 500.0)
      ).apply {
        setTolerance(5.0, 100.0)
        disableContinuousInput()
      }
    },

    val perfectTurn: Provider<ProfiledPIDController, PerfectTurn> = Provider {
      ProfiledPIDController(
          0.1,
          0.0,
          0.0,
          TrapezoidProfile.Constraints(150.0, 150.0)
      ).apply {
        setTolerance(5.0, 50.0)
        disableContinuousInput()
      }
    },

    val alignToTarget: Provider<PIDController, AlignToTarget> = Provider {
      PIDController(0.3, 0.1, 0.01).apply {
        disableContinuousInput()
        setTolerance(0.1)
      }
    },

    val stayOnTarget: Provider<PIDController, StayOnTarget> = Provider {
      PIDController(0.1, 0.0, 0.0).apply {
        disableContinuousInput()
        setTolerance(0.2)
      }
    },
)

inline fun <reified Owner> Controllers.pid() =
    when (Owner::class) {
      GyroDrive::class -> gyroDrive<GyroDrive>()
      StayOnTarget::class -> stayOnTarget<StayOnTarget>()
      AlignToTarget::class -> alignToTarget<AlignToTarget>()
      else -> error("Unknown pid")
    }

inline fun <reified Owner> Controllers.profiledPid() =
    when (Owner::class) {
      LeaveLine::class -> leaveLine<LeaveLine>()
      PerfectTurn::class -> perfectTurn<PerfectTurn>()
      else -> error("Unknown pid")
    }