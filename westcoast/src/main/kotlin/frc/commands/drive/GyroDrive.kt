package frc.commands.drive

import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.controller.PIDController
import edu.wpi.first.wpilibj2.command.PIDCommand
import frc.math.attenuated
import frc.math.joyclean
import frc.math.pidclean
import frc.subsystems.DriveTrainSubsystem

class GyroDrive(
    forward: Double,
    controller: PIDController,
    joystick: Joystick,
    private val drive: DriveTrainSubsystem,
) : PIDCommand(
    controller,
    { drive.gyro.rate.pidclean(6.0, 0.3) },
    { (forward * joystick.x).joyclean(0.05) * 4.6 },
    {
      drive.arcade(
          forward = (forward * joystick.y).attenuated,
          turn = (-it).pidclean(0.8, 0.05),
          squareInputs = false
      )
    },
    drive,
) {

  override fun initialize() {
    drive.gyro.reset()
  }

  override fun end(interrupted: Boolean) {
    drive.stop()
  }
}