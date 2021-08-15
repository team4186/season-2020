package frc.commands.drive

import edu.wpi.first.wpilibj.Encoder
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj2.command.CommandBase
import edu.wpi.first.wpilibj.controller.ProfiledPIDController
import frc.math.Maths
import frc.robot.maps.RobotMap

class LeaveLine(
    private val map: RobotMap,
    private val drive: DifferentialDrive,
    private val leftEncoder: Encoder,
    private val rightEncoder: Encoder,
    private val dist: Double
) : CommandBase() {
  private var left: ProfiledPIDController? = null
  private var right: ProfiledPIDController? = null
  private var wait = 0.0
  override fun initialize() {
    wait = 0.0
    rightEncoder.reset()
    leftEncoder.reset()
    right = map.makeLLPIDs()
    left = map.makeLLPIDs()
  }

  override fun execute() {
    val distance: Double = dist * map.lLMult
    val rightOut = Maths.clamp(right!!.calculate(rightEncoder.distance, distance), 0.4)
    val leftOut = Maths.clamp(left!!.calculate(leftEncoder.distance, distance), 0.4)
    drive.tankDrive(-leftOut, -rightOut, false)
    wait = if (right!!.atGoal() && left!!.atGoal()) {
      wait + 1
    }
    else {
      0.0
    }
  }

  override fun end(interrupted: Boolean) {
    rightEncoder.reset()
    leftEncoder.reset()
    drive.stopMotor()
    right!!.reset(0.0, 0.0)
    left!!.reset(0.0, 0.0)
    println("Line Left!")
  }

  override fun isFinished(): Boolean {
    return wait >= 15
  }
}