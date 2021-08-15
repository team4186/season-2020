package frc.commands.drive

import edu.wpi.first.wpilibj.Encoder
import edu.wpi.first.wpilibj.controller.ProfiledPIDController
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.math.Maths
import frc.robot.maps.RobotMap

class PerfectTurn(
    private val map: RobotMap,
    private val drive: DifferentialDrive,
    private val leftEncoder: Encoder,
    private val rightEncoder: Encoder,
    private val angle: Double
) : CommandBase() {
  private var turnRight: ProfiledPIDController? = null
  private var turnLeft: ProfiledPIDController? = null
  private var wait = 0.0
  override fun initialize() {
    wait = 0.0
    leftEncoder.reset()
    rightEncoder.reset()
    turnLeft = map.makePTPIDs()
    turnRight = map.makePTPIDs()
  }

  override fun execute() {
    val setpoint: Double = angle * map.pTMult
    val rightside = Maths.clamp(turnRight!!.calculate(-rightEncoder.distance, -setpoint), 0.4)
    val leftside = Maths.clamp(turnLeft!!.calculate(-leftEncoder.distance, setpoint), 0.4)
    drive.tankDrive(leftside, rightside, false)
    wait = if (turnRight!!.atGoal() && turnLeft!!.atGoal()) {
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
    turnRight!!.reset(0.0, 0.0)
    turnLeft!!.reset(0.0, 0.0)
    println("Turn Finished!")
  }

  override fun isFinished(): Boolean {
    return wait >= 10
  }
}