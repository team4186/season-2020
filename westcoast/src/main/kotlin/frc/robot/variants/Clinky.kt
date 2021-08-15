package frc.robot.variants

import edu.wpi.first.wpilibj.controller.PIDController
import frc.robot.data.Data
import frc.robot.data.*
import frc.vision.RioVisionRunner

val clinky
  get() = Data(
      name = "Clinky",
      parameters = Parameters(
          leaveLineDistanceMultiplier = 85.0.param(),
          perfectTurnAngleMultiplier = 1.45.param(),
      ),
      sensors = Sensors(
          drive = DriveSensors(
              vision = RioVisionRunner()
          )
      ),
      controllers = Controllers(
          alignToTarget = Provider {
            PIDController(0.2, 0.15, 0.0).apply {
              disableContinuousInput()
              setTolerance(0.1)
            }
          },
      ),
  )