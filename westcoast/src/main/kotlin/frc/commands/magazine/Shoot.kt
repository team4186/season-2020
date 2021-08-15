package frc.commands.magazine

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.subsystems.MagazineSubsystem

class Shoot(
    private val magazine: MagazineSubsystem
) : CommandBase() {
  override fun initialize() {
    magazine.shooterTune()
  }

  override fun execute() {
    magazine.runShooter(0.78)
    magazine.runMagMotor(0.3)
    magazine.runIndexMotor(0.25)
  }

  override fun end(interrupted: Boolean) {
    magazine.stopMotors()
    magazine.resetIndexCount()
  }

  init {
    addRequirements(magazine)
  }
}