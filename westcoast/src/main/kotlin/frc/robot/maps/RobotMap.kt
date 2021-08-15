package frc.robot.maps

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj.SpeedController
import edu.wpi.first.wpilibj.controller.PIDController
import edu.wpi.first.wpilibj.controller.ProfiledPIDController

interface RobotMap {
  fun makeLLPIDs(): ProfiledPIDController
  fun makePTPIDs(): ProfiledPIDController
  fun makeDrivePIDs(): PIDController
  fun makeAlignPIDs(): PIDController
  fun makeForwardCAlignPIDs(): PIDController
  fun makeTurnCAlignPIDs(): PIDController
  fun makeStayOnTargetPIDs(): PIDController
  val reversed: Boolean
  val lLMult: Double
  val pTMult: Double
  val indexSensor: DigitalInput?
  val magSensor: DigitalInput?
  val shooterSensor: DigitalInput?
  val mainShooter: WPI_TalonSRX?
  val secondaryShooter: WPI_TalonSRX?
  val intakeMotor: SpeedController?
  val indexMotor: SpeedController?
  val magMotor: SpeedController?
}