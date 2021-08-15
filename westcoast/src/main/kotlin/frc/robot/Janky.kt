package frc.robot

import com.analog.adis16448.frc.ADIS16448_IMU
import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.Spark
import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.CommandScheduler
import frc.robot.maps.JankyMap
import frc.robot.maps.RobotMap
import frc.commands.drive.TeleopDrive

class Janky : TimedRobot() {
  //Robot Map
  private val map: RobotMap = JankyMap()

  // Drivetrain
  private val rightMotor = Spark(0)
  private val leftMotor = Spark(1)
  private val drive = DifferentialDrive(leftMotor, rightMotor)

  // Inputs
  private val joystick = Joystick(0)

  // Sensors
  private val adis = ADIS16448_IMU()

  // Commands
  private val teleop = TeleopDrive(map, drive, joystick)

  //private final AdisDrive teleop = new AdisDrive(map, drive, joystick, adis);
  // Network Table
  private val inst = NetworkTableInstance.getDefault()
  private val table = inst.getTable("Jetson")
  var view1 = table.getEntry("view1")
  var view2 = table.getEntry("view2")
  override fun robotInit() {
    drive.isSafetyEnabled = false
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