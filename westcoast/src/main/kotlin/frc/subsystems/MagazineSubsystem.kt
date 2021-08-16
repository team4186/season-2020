package frc.subsystems

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj.SpeedController
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.SubsystemBase

class MagazineSubsystem(
    private val intakeMotor: SpeedController,
    private val indexMotor: SpeedController,
    private val magazineMotor: SpeedController,
    private val leftShooter: WPI_TalonSRX,
    private val rightShooter: WPI_TalonSRX,
    private val headSensor: DigitalInput,
    private val abSensor: DigitalInput,
    private val tailSensor: DigitalInput,
) : SubsystemBase() {
  private var shooterPercent = 0.0
  private var amps = 0.0
  private var percent = 0.0
  var indexCount = 0.0
    private set

  private inline val headSensorValue: Boolean get() = !headSensor.get()
  private inline val abdomenSensorValue: Boolean get() = !abSensor.get()
  private inline val tailSensorValue: Boolean get() = !tailSensor.get()

  override fun periodic() {
    SmartDashboard.putNumber("Sensor", sensorSwitch.toDouble())
  }

  fun runIntakeMotor(value: Double) {
    intakeMotor.set(value)
  }

  fun runIndexMotor(value: Double) {
    indexMotor.set(value)
  }

  fun runSyncIntdex(value: Double) {
    intakeMotor.set(value)
    indexMotor.set(value)
  }

  fun runMagMotor(value: Double) {
    magazineMotor.set(-value)
  }

  fun runSyncMagIndex(value: Double) {
    indexMotor.set(value)
    magazineMotor.set(-value)
  }

  fun runShooter(value: Double) {
    leftShooter.set(value)
    rightShooter.set(value)
  }

  fun shooterTune() {
    with(leftShooter) {
      configNominalOutputForward(0.0)
      configNominalOutputReverse(0.0)
      configPeakOutputForward(100.0)
      configPeakOutputReverse(-100.0)
      setNeutralMode(NeutralMode.Coast)
      config_kP(0, 0.1)
      config_kI(0, 0.4)
      config_kD(0, 0.0)
    }

    with(rightShooter) {
      configNominalOutputForward(0.0)
      configNominalOutputReverse(0.0)
      configPeakOutputForward(100.0)
      configPeakOutputReverse(-100.0)
      setNeutralMode(NeutralMode.Coast)
      config_kP(0, 0.1)
      config_kI(0, 0.4)
      config_kD(0, 0.0)
      inverted = true
    }
  }

  fun runShooterCC(value: Double) {
    amps = value * 9
    leftShooter[ControlMode.Current] = value * 9
    rightShooter[ControlMode.Current] = value * 9
  }

  val currentLevels: Unit
    get() {
      SmartDashboard.putNumber("Left Current", leftShooter.supplyCurrent)
      SmartDashboard.putNumber("Right Current", rightShooter.supplyCurrent)
    }

  fun runShooterPercent(value: Double) {
    percent = value
    leftShooter[ControlMode.PercentOutput] = value
    rightShooter[ControlMode.PercentOutput] = value
  }

  fun setShooterPercent() {
    shooterPercent = leftShooter.motorOutputPercent
  }

  val motorOutputPercent: Unit
    get() {
      SmartDashboard.putNumber("Left Current", leftShooter.motorOutputPercent)
      SmartDashboard.putNumber("Right Current", rightShooter.motorOutputPercent)
    }

  fun shooterEndCC(): Boolean {
    return leftShooter.supplyCurrent == amps && rightShooter.supplyCurrent == amps
  }

  fun shooterEndPercent(): Boolean {
    return leftShooter.motorOutputPercent == percent && rightShooter.motorOutputPercent == percent
  }

  fun stopMotors() {
    intakeMotor.stopMotor()
    indexMotor.stopMotor()
    magazineMotor.stopMotor()
    leftShooter.stopMotor()
    rightShooter.stopMotor()
  }

  val sensorSwitch: Int
    get() {
      val head = if (headSensorValue) 0x1 else 0
      val abdomen = if (abdomenSensorValue) 0x2 else 0
      val tail = if (tailSensorValue) 0x4 else 0
      return head or abdomen or tail
    }

  fun incrementIndexCount(): Double {
    indexCount += 1
    return indexCount
  }

  fun resetIndexCount() {
    indexCount = 0.0
  }
}