package frc.commands.magazine

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.subsystems.MagazineSubsystem

class EverythingOut(
    private val magazine: MagazineSubsystem
) : CommandBase() {
  override fun initialize() {
    magazine.resetIndexCount()
  }

  override fun execute() {
    magazine.runIntakeMotor(-0.4)
    magazine.runIndexMotor(-0.4)
    magazine.runMagMotor(-0.4)
  }

  override fun end(interrupted: Boolean) {
    magazine.stopMotors()
  }

  init {
    addRequirements(magazine)
  }
}