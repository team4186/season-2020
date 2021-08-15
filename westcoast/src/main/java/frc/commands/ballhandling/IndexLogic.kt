package frc.commands.ballhandling

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.subsystems.BallHandlingSubsystem

class IndexLogic(
    private val ballHandler: BallHandlingSubsystem
) : CommandBase() {
  private var end = false
  private var boost = 0.0
  override fun initialize() {
    end = false
    boost = 0.019 * ballHandler.indexCount
  }

  override fun execute() {
    when (ballHandler.sensorSwitch) {
      0x0 -> {
        ballHandler.runindexMotor(0.3)
        ballHandler.runmagMotor(0.3 + boost) //No sensors see anything.
      }
      0x1 -> {
        ballHandler.runindexMotor(0.3)
        ballHandler.runmagMotor(0.43) //Intake sensor sees something.
      }
      0x2 -> end = true //Index sensor sees something.
      0x3 -> {
        ballHandler.runindexMotor(0.27) //Both Index sensor and Intake sensor see something (all balls after first)
        ballHandler.runmagMotor(0.3 + boost) //Boosts magazines speed so as to avoid magazine still seeing ball while index get's cleared (could also be fixed by moving sensor positions)
      }
      0x4 -> end = true //End sensor sees something (shouldn't happen).
      0x5 -> end = true //Intake sensor and End sensor see something (shouldn't happen).
      0x6 -> end = true //End sensor and Index sensor sees something (happens when 4 balls are in the system).
      0x7 -> end = true //All sensors are saturated (happens with 5 balls).
    }
  }

  override fun end(interrupted: Boolean) {
    println("Index Complete")
    ballHandler.stopMotors()
  }

  override fun isFinished(): Boolean {
    return end
  }

  init {
    addRequirements(ballHandler)
  }
}