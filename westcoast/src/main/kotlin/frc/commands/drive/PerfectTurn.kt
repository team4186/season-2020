package frc.commands.drive

import edu.wpi.first.wpilibj.controller.ProfiledPIDController
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.data.DoubleParameter
import frc.subsystems.DriveTrainSubsystem

class PerfectTurn(
    private val angle: Double,
    private val angleMultiplier: DoubleParameter<PerfectTurn>,
    private val right: ProfiledPIDController,
    private val left: ProfiledPIDController,
    private val drive: DriveTrainSubsystem,
) : CommandBase() {
  private var wait = 0
  override fun initialize() {
    wait = 0
    drive.leftEncoder.reset()
    drive.rightEncoder.reset()
    right.reset(0.0, 0.0)
    left.reset(0.0, 0.0)
  }

  override fun execute() {
    val setpoint = angle * angleMultiplier.value
    drive.tank(
        left.calculate(-drive.leftEncoder.distance, setpoint).coerceIn(-0.4, 0.4),
        right.calculate(-drive.rightEncoder.distance, -setpoint).coerceIn(-0.4, 0.4),
        squareInputs = false
    )
    wait = if (right.atGoal() && left.atGoal()) {
      wait + 1
    }
    else {
      0
    }
  }

  override fun end(interrupted: Boolean) {
    drive.stop()
  }

  override fun isFinished(): Boolean {
    return wait >= 10
  }
}