package frc.commands.ballhandling

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.subsystems.BallHandlingSubsystem

class IntakeLogic(private val ballHandler: BallHandlingSubsystem) : CommandBase() {
  private var end = false
  override fun initialize() {
    end = false
  }

  override fun execute() {
    when (ballHandler.sensorSwitch) {
      0x0 -> ballHandler.runsyncIntdex(0.4) //No sensors see anything.
      0x1 -> end = true //Intake sensor sees something.
      0x2 -> ballHandler.runsyncIntdex(0.4) //Index sensor sees something.
      0x3 -> end = true //Both Index sensor and Intake sensor see something (all balls after first)
      0x4 -> end = true //End sensor sees something (shouldn't happen).
      0x5 -> ballHandler.runsyncIntdex(0.4) //Intake sensor and End sensor see something (shouldn't happen).
      0x6 -> ballHandler.runsyncIntdex(0.4) //End sensor and Index sensor sees something (happens when 4 balls are in the system).
      0x7 -> end = true //All sensors are saturated (happens with 5 balls).
    }
  }

  override fun end(interrupted: Boolean) {
    println("Intake Complete")
    ballHandler.stopMotors()
    ballHandler.incrementIndexCount()
  }

  override fun isFinished(): Boolean {
    return end
  }

  init {
    addRequirements(ballHandler)
  }
}