package frc.robot

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX
import edu.wpi.first.wpilibj.controller.PIDController
import edu.wpi.first.wpilibj.controller.ProfiledPIDController
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile
import frc.robot.data.*

val shinDestroyer
  get() = Data(
      name = "Shin Destroyer",
      parameters = Parameters(
          leaveLineDistanceMultiplier = 62.0.param(),
          perfectTurnAngleMultiplier = 1.04.param(),
      ),
      motors = Motors(
          driveLeft = driveCTRMotors(
              lead = WPI_TalonSRX(14),
              follower0 = WPI_VictorSPX(13),
              follower1 = WPI_VictorSPX(15)
          ),
          driveRight = driveCTRMotors(
              lead = WPI_TalonSRX(2),
              follower0 = WPI_VictorSPX(1),
              follower1 = WPI_VictorSPX(3)
          ),
          magazine = MagazineMotors(
              intake = WPI_VictorSPX(4),
              index = WPI_TalonSRX(11),
              magazine = WPI_VictorSPX(12),
          ),
          shooter = ShooterMotors(
              main = WPI_TalonSRX(7),
              secondary = WPI_TalonSRX(9),
          ),
      ),
      controllers = Controllers(
          gyroDrive = Provider {
            PIDController(0.4, 0.12, 0.01).apply {
              setTolerance(0.5)
              disableContinuousInput()
            }
          },

          leaveLine = Provider {
            ProfiledPIDController(
                0.02,
                0.0,
                0.001,
                TrapezoidProfile.Constraints(1000.0, 750.0)
            ).apply {
              setTolerance(5.0, 100.0)
              disableContinuousInput()
            }
          },

          perfectTurn = Provider {
            ProfiledPIDController(
                0.05,
                0.0,
                0.0,
                TrapezoidProfile.Constraints(500.0, 300.0)
            ).apply {
              setTolerance(5.0, 50.0)
              disableContinuousInput()
            }
          },

          alignToTarget = Provider {
            PIDController(0.7, 0.1, 0.07).apply {
              disableContinuousInput()
              setTolerance(0.1)
            }
          },

          stayOnTarget = Provider {
            PIDController(0.1, 0.0, 0.0).apply {
              disableContinuousInput()
              setTolerance(0.2)
            }
          },
      )
  )