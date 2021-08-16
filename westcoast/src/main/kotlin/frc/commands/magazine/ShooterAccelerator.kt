package frc.commands.magazine

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.subsystems.MagazineSubsystem

class ShooterAccelerator(
    private val magazine: MagazineSubsystem
) : CommandBase() {
  override fun initialize() {
    magazine.shooterTune()
  }

  override fun execute() {
    magazine.runShooter(0.78)
    magazine.currentLevels
  }

  override fun end(interrupted: Boolean) {
    magazine.stopMotors()
    magazine.setShooterPercent()
  }

  init {
    addRequirements(magazine)
  }
}