package frc.robot.maps

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj.SpeedController
import edu.wpi.first.wpilibj.controller.PIDController
import edu.wpi.first.wpilibj.controller.ProfiledPIDController
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile

class JankyMap : RobotMap {
  override fun makeLLPIDs(): ProfiledPIDController {
    val pid = ProfiledPIDController(0.1, .0, 0.0, TrapezoidProfile.Constraints(600.0, 500.0))
    pid.setTolerance(5.0, 100.0)
    pid.disableContinuousInput()
    pid.reset(0.0, 0.0)
    return pid
  }

  override fun makePTPIDs(): ProfiledPIDController {
    val pid = ProfiledPIDController(0.1, 0.0, 0.0, TrapezoidProfile.Constraints(150.0, 150.0))
    pid.setTolerance(5.0, 50.0)
    pid.disableContinuousInput()
    pid.reset(0.0, 0.0)
    return pid
  }

  override fun makeDrivePIDs(): PIDController {
    val pid = PIDController(0.2, 0.0, 0.0)
    pid.setTolerance(0.5)
    pid.disableContinuousInput()
    pid.reset()
    return pid
  }

  override fun makeAlignPIDs(): PIDController {
    val pid = PIDController(0.2, 0.15, 0.0)
    pid.disableContinuousInput()
    pid.setTolerance(0.1)
    pid.reset()
    return pid
  }

  override fun makeForwardCAlignPIDs(): PIDController {
    val pid = PIDController(0.2, 0.0, 0.03)
    pid.disableContinuousInput()
    pid.setTolerance(0.0)
    pid.reset()
    return pid
  }

  override fun makeTurnCAlignPIDs(): PIDController {
    val pid = PIDController(0.1, 0.0, 0.01)
    pid.disableContinuousInput()
    pid.setTolerance(0.2)
    pid.reset()
    return pid
  }

  override fun makeStayOnTargetPIDs(): PIDController {
    val pid = PIDController(0.1, 0.0, 0.0)
    pid.disableContinuousInput()
    pid.setTolerance(0.2)
    pid.reset()
    return pid
  }

  override val reversed: Boolean
    get() = true
  override val lLMult: Double
    get() = 0.0
  override val pTMult: Double
    get() = 0.0
  override val indexSensor: DigitalInput
    get() = DigitalInput(0)
  override val magSensor: DigitalInput?
    get() = null
  override val shooterSensor: DigitalInput?
    get() = null
  override val mainShooter: WPI_TalonSRX?
    get() = null
  override val secondaryShooter: WPI_TalonSRX?
    get() = null
  override val intakeMotor: SpeedController?
    get() = null
  override val indexMotor: SpeedController?
    get() = null
  override val magMotor: SpeedController?
    get() = null
}