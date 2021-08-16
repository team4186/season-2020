package frc.commands.magazine

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.subsystems.MagazineSubsystem

class IntakeLogic(
    private val magazine: MagazineSubsystem
) : CommandBase() {

  init {
    addRequirements(magazine)
  }

  override fun execute() {
    when (magazine.sensorSwitch) {
      0x0 -> magazine.runSyncIntdex(0.4) //No sensors see anything.
      0x1 -> Unit //Intake sensor sees something.
      0x2 -> magazine.runSyncIntdex(0.4) //Index sensor sees something.
      0x3 -> Unit //Both Index sensor and Intake sensor see something (all balls after first)
      0x4 -> Unit //End sensor sees something (shouldn't happen).
      0x5 -> magazine.runSyncIntdex(0.4) //Intake sensor and End sensor see something (shouldn't happen).
      0x6 -> magazine.runSyncIntdex(0.4) //End sensor and Index sensor sees something (happens when 4 balls are in the system).
      0x7 -> Unit //All sensors are saturated (happens with 5 balls).
    }
  }

  override fun end(interrupted: Boolean) {
    magazine.stopMotors()
    magazine.incrementIndexCount()
  }

  override fun isFinished(): Boolean =
      when (magazine.sensorSwitch) {
        0x0 -> false
        0x1 -> true
        0x2 -> false
        0x3 -> true
        0x4 -> true
        0x5 -> false
        0x6 -> false
        0x7 -> true
        else -> true // NOTE error finish here
      }
}