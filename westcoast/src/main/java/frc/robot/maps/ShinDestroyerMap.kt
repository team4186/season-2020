package frc.robot.maps

import frc.math.Maths
import frc.robot.maps.RobotMap
import edu.wpi.first.wpilibj.controller.ProfiledPIDController
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile
import edu.wpi.first.wpilibj.DigitalInput
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import edu.wpi.first.wpilibj.SpeedController
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX
import edu.wpi.first.wpilibj.VictorSP
import kotlin.jvm.JvmStatic
import edu.wpi.first.wpilibj.RobotBase
import frc.robot.Dinky
import java.util.function.Supplier
import edu.wpi.first.wpilibj.TimedRobot
import frc.robot.maps.DinkyMap
import frc.subsystems.drive.motorfactory.MotorFactory
import frc.subsystems.drive.motorfactory.MotorFactoryHybrid
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import frc.subsystems.BallHandlingSubsystem
import frc.subsystems.vision.RioVisionRunner
import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj2.command.button.JoystickButton
import frc.subsystems.drive.TeleopDrive
import edu.wpi.first.wpilibj2.command.CommandScheduler
import frc.commands.ballhandling.IntakeAndIndex
import frc.commands.ballhandling.IntakeOut
import frc.commands.ballhandling.EverythingOut
import frc.commands.ballhandling.Shooting
import frc.robot.maps.JankyMap
import edu.wpi.first.wpilibj.Spark
import com.analog.adis16448.frc.ADIS16448_IMU
import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.networktables.NetworkTableEntry
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import frc.robot.maps.ClinkyMap
import com.kauailabs.navx.frc.AHRS
import edu.wpi.first.wpilibj.SPI
import edu.wpi.first.wpilibj.controller.PIDController
import frc.subsystems.drive.GyroDrive
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import frc.commands.auto.TargetAutonomous
import frc.commands.auto.CenterAutonomous
import frc.commands.auto.LoadingBayAutonomous
import frc.commands.motors.SetMotor
import frc.robot.maps.ShinDestroyerMap
import frc.subsystems.vision.LimelightRunner
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.commands.auto.demo.FlexAuto

class ShinDestroyerMap : RobotMap {
  override fun makeLLPIDs(): ProfiledPIDController {
    val pid = ProfiledPIDController(0.02, 0.0, 0.001, TrapezoidProfile.Constraints(1000.0, 750.0))
    pid.setTolerance(5.0, 100.0)
    pid.disableContinuousInput()
    pid.reset(0.0, 0.0)
    return pid
  }

  override fun makePTPIDs(): ProfiledPIDController {
    val pid = ProfiledPIDController(0.05, 0.0, 0.0, TrapezoidProfile.Constraints(500.0, 300.0)) //untuned
    pid.setTolerance(5.0, 50.0)
    pid.disableContinuousInput()
    pid.reset(0.0, 0.0)
    return pid
  }

  override fun makeDrivePIDs(): PIDController {
    val pid = PIDController(0.4, 0.12, 0.01)
    pid.setTolerance(0.5)
    pid.disableContinuousInput()
    pid.reset()
    return pid
  }

  override fun makeAlignPIDs(): PIDController {
    // PIDController pid = new PIDController(0.12, 0.1, 0.03);
    // pid.disableContinuousInput();
    // pid.setTolerance(0.1);
    val pid = PIDController(0.7, 0.1, 0.07)
    pid.disableContinuousInput()
    pid.setTolerance(0.1)
    pid.reset()
    return pid
  }

  override fun makeForwardCAlignPIDs(): PIDController {
    val pid = PIDController(0.2, 0.0, 0.03) //untuned
    pid.disableContinuousInput()
    pid.setTolerance(0.0) //untuned
    pid.reset()
    return pid
  }

  override fun makeTurnCAlignPIDs(): PIDController {
    val pid = PIDController(0.1, 0.0, 0.01) //untuned
    pid.disableContinuousInput()
    pid.setTolerance(0.2) //untuned
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
    get() = false
  override val lLMult: Double
    get() = 62.0
  override val pTMult: Double
    get() = 1.04
  override val indexSensor: DigitalInput
    get() = DigitalInput(0)
  override val magSensor: DigitalInput
    get() = DigitalInput(1)
  override val shooterSensor: DigitalInput
    get() = DigitalInput(2)
  override val mainShooter: WPI_TalonSRX
    get() = WPI_TalonSRX(7)
  override val secondaryShooter: WPI_TalonSRX
    get() = WPI_TalonSRX(9)
  override val intakeMotor: SpeedController
    get() = WPI_VictorSPX(4)
  override val indexMotor: SpeedController
    get() = WPI_TalonSRX(11)
  override val magMotor: SpeedController
    get() = WPI_VictorSPX(12)
}