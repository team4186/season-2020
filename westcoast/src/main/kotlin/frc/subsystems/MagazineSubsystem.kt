package frc.subsystems

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj.SpeedController
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.maps.RobotMap

class MagazineSubsystem(map: RobotMap) : SubsystemBase() {
  private val intakeMotor: SpeedController?
  private val indexMotor: SpeedController?
  private val magMotor: SpeedController?
  private val leftShooter: WPI_TalonSRX?
  private val rightShooter: WPI_TalonSRX?
  private val headSensor: DigitalInput?
  private val abSensor: DigitalInput?
  private val tailSensor: DigitalInput?
  var shooterPercent = 0.0
    private set
  private var amps = 0.0
  private var percent = 0.0
  var indexCount = 0.0
    private set

  override fun periodic() {
    SmartDashboard.putNumber("Sensor", sensorSwitch.toDouble())
  }

  fun runintakeMotor(value: Double) {
    intakeMotor!!.set(value)
  }

  fun runindexMotor(value: Double) {
    indexMotor!!.set(value)
  }

  fun runsyncIntdex(value: Double) {
    intakeMotor!!.set(value)
    indexMotor!!.set(value)
  }

  fun runmagMotor(value: Double) {
    magMotor!!.set(-value)
  }

  fun runsyncMagdex(value: Double) {
    indexMotor!!.set(value)
    magMotor!!.set(-value)
  }

  fun runShooter(value: Double) {
    leftShooter!!.set(value)
    rightShooter!!.set(value)
  }

  fun shooterTune() {
    leftShooter!!.configNominalOutputForward(0.0)
    leftShooter.configNominalOutputReverse(0.0)
    leftShooter.configPeakOutputForward(100.0)
    leftShooter.configPeakOutputReverse(-100.0)
    leftShooter.setNeutralMode(NeutralMode.Coast)
    leftShooter.config_kP(0, 0.1)
    leftShooter.config_kI(0, 0.4)
    leftShooter.config_kD(0, 0.0)
    rightShooter!!.configNominalOutputForward(0.0)
    rightShooter.configNominalOutputReverse(0.0)
    rightShooter.configPeakOutputForward(100.0)
    rightShooter.configPeakOutputReverse(-100.0)
    rightShooter.setNeutralMode(NeutralMode.Coast)
    rightShooter.config_kP(0, 0.1)
    rightShooter.config_kI(0, 0.4)
    rightShooter.config_kD(0, 0.0)
    rightShooter.inverted = true
  }

  fun runShooterCC(value: Double) {
    amps = value * 9
    leftShooter!![ControlMode.Current] = value * 9
    rightShooter!![ControlMode.Current] = value * 9
  }

  val currentLevels: Unit
    get() {
      SmartDashboard.putNumber("Left Current", leftShooter!!.supplyCurrent)
      SmartDashboard.putNumber("Right Current", rightShooter!!.supplyCurrent)
    }

  fun runShooterPercent(value: Double) {
    percent = value
    leftShooter!![ControlMode.PercentOutput] = value
    rightShooter!![ControlMode.PercentOutput] = value
  }

  fun setShooterPercent() {
    shooterPercent = leftShooter!!.motorOutputPercent
  }

  val motorOutputPercent: Unit
    get() {
      SmartDashboard.putNumber("Left Current", leftShooter!!.motorOutputPercent)
      SmartDashboard.putNumber("Right Current", rightShooter!!.motorOutputPercent)
    }

  fun shooterEndCC(): Boolean {
    return leftShooter!!.supplyCurrent == amps && rightShooter!!.supplyCurrent == amps
  }

  fun shooterEndPercent(): Boolean {
    return leftShooter!!.motorOutputPercent == percent && rightShooter!!.motorOutputPercent == percent
  }

  fun stopMotors() {
    intakeMotor!!.stopMotor()
    indexMotor!!.stopMotor()
    magMotor!!.stopMotor()
    leftShooter!!.stopMotor()
    rightShooter!!.stopMotor()
  }

  fun headSensorValue(): Boolean {
    return !headSensor!!.get()
  }

  fun abdomenSensorValue(): Boolean {
    return !abSensor!!.get()
  }

  fun tailSensorValue(): Boolean {
    return !tailSensor!!.get()
  }

  val sensorSwitch: Int
    get() {
      val head = if (headSensorValue()) 0x1 else 0
      val abdomen = if (abdomenSensorValue()) 0x2 else 0
      val tail = if (tailSensorValue()) 0x4 else 0
      return head or abdomen or tail
    }

  fun incrementIndexCount(): Double {
    indexCount = indexCount + 1
    return indexCount
  }

  fun resetIndexCount() {
    indexCount = 0.0
  }

  init {
    intakeMotor = map.intakeMotor
    indexMotor = map.indexMotor
    magMotor = map.magMotor //should be mag motor, but hardware is off
    leftShooter = map.mainShooter
    rightShooter = map.secondaryShooter
    headSensor = map.indexSensor
    abSensor = map.magSensor
    tailSensor = map.shooterSensor
  }
}