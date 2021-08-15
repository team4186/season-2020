package frc.robot

import com.analog.adis16448.frc.ADIS16448_IMU
import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj.Encoder
import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.Spark
import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.CommandScheduler
import edu.wpi.first.wpilibj2.command.button.JoystickButton
import frc.commands.drive.TeleopDrive
import frc.hardware.SaitekX52Buttons
import frc.subsystems.DriveTrainSubsystem
import frc.vision.RioVisionRunner

class Janky : TimedRobot() {
  // Drivetrain
  private val drive = DriveTrainSubsystem(
      left = Spark(1),
      right = Spark(0),
      leftEncoder = Encoder(9, 8).apply { distancePerPulse = 0.390625 },
      rightEncoder = Encoder(6, 7).apply { distancePerPulse = 0.390625 },
      gyro = ADIS16448_IMU(),
      vision = RioVisionRunner(),
  )

  // Inputs
  private val joystick = Joystick(0)

  // Sensors
  private val adis = ADIS16448_IMU()

  private val teleop = TeleopDrive(
      forward = 1.0,
      joystick = joystick,
      attenuate = JoystickButton(joystick, SaitekX52Buttons.FIRE_C + 1),
      drive = drive,
  )

//  private val teleop = GyroDrive(
//      forward = 1.0,
//      controller = PIDController(0.2, 0.0, 0.0).apply {
//        setTolerance(0.5)
//        disableContinuousInput()
//      },
//      joystick = joystick,
//      drive = drive,
//  )

  // Network Table
  private val inst = NetworkTableInstance.getDefault()
  private val table = inst.getTable("Jetson")
  private val view1 = table.getEntry("view1")
  private val view2 = table.getEntry("view2")

  override fun robotInit() {
    drive.initialize()
  }

  override fun robotPeriodic() {
    view1.setBoolean(!joystick.getRawButton(5))
    view2.setBoolean(joystick.getRawButton(5))
    SmartDashboard.putNumber("ADIS Value", adis.rate)
  }

  override fun teleopInit() {
    teleop.schedule()
  }

  override fun teleopPeriodic() {
    CommandScheduler.getInstance().run()
  }
}