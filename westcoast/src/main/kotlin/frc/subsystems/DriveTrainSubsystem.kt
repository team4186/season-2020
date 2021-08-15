package frc.subsystems

import edu.wpi.first.wpilibj.Encoder
import edu.wpi.first.wpilibj.SpeedController
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj.interfaces.Gyro
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.vision.VisionRunner

class DriveTrainSubsystem private constructor(
    private val drive: DifferentialDrive,
    val leftEncoder: Encoder,
    val rightEncoder: Encoder,
    val gyro: Gyro,
    val vision: VisionRunner,
) : SubsystemBase() {
  constructor(
      left: SpeedController,
      right: SpeedController,
      leftEncoder: Encoder,
      rightEncoder: Encoder,
      gyro: Gyro,
      vision: VisionRunner,
  ) : this(
      drive = DifferentialDrive(left, right),
      leftEncoder = leftEncoder,
      rightEncoder = rightEncoder,
      gyro = gyro,
      vision = vision,
  )

  // NOTE call from initRobot()
  fun initialize() {
    vision.init()
    drive.stopMotor()
    leftEncoder.reset()
    rightEncoder.reset()
    gyro.reset()

    drive.isSafetyEnabled = false
  }

  fun stop() {
    drive.stopMotor()
  }

  fun arcade(forward: Double, turn: Double, squareInputs: Boolean = true) {
    drive.arcadeDrive(forward, turn, squareInputs)
  }

  fun tank(left: Double, right: Double, squareInputs: Boolean = true) {
    drive.tankDrive(left, right, squareInputs)
  }
}