package frc.commands.magazine

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.subsystems.MagazineSubsystem

class IndexLogic(
    private val magazine: MagazineSubsystem
) : CommandBase() {

  private var boost = 0.0

  override fun initialize() {
    boost = 0.019 * magazine.indexCount
  }

  override fun execute() {
    when (magazine.sensorSwitch) {
      0x0 -> {
        magazine.runIndexMotor(0.3)
        magazine.runMagMotor(0.3 + boost) //No sensors see anything.
      }
      0x1 -> {
        magazine.runIndexMotor(0.3)
        magazine.runMagMotor(0.43) //Intake sensor sees something.
      }
      0x2 -> Unit //Index sensor sees something.
      0x3 -> {
        magazine.runIndexMotor(0.27) //Both Index sensor and Intake sensor see something (all balls after first)
        magazine.runMagMotor(0.3 + boost) //Boosts magazines speed so as to avoid magazine still seeing ball while index get's cleared (could also be fixed by moving sensor positions)
      }
      0x4 -> Unit //End sensor sees something (shouldn't happen).
      0x5 -> Unit //Intake sensor and End sensor see something (shouldn't happen).
      0x6 -> Unit //End sensor and Index sensor sees something (happens when 4 balls are in the system).
      0x7 -> Unit //All sensors are saturated (happens with 5 balls).
    }
  }

  override fun end(interrupted: Boolean) {
    println("Index Complete")
    magazine.stopMotors()
  }

  override fun isFinished(): Boolean =
      when (magazine.sensorSwitch) {
        0x0 -> false
        0x1 -> false
        0x2 -> true
        0x3 -> false
        0x4 -> true
        0x5 -> true
        0x6 -> true
        0x7 -> true
        else -> true
      }

  init {
    addRequirements(magazine)
  }
}