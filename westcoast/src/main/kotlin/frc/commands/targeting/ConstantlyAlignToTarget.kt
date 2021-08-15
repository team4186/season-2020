package frc.commands.targeting

import edu.wpi.first.wpilibj.controller.PIDController
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.math.Maths.clamp
import frc.robot.maps.RobotMap
import frc.vision.VisionRunner

class ConstantlyAlignToTarget(
    private val map: RobotMap,
    private val drive: DifferentialDrive,
    private val vision: VisionRunner
) : CommandBase() {
  private var turn: PIDController? = null
  private var forward: PIDController? = null
  override fun initialize() {
    turn = map.makeTurnCAlignPIDs()
    forward = map.makeForwardCAlignPIDs()
  }

  override fun execute() {
    val value = vision.alignX
    val distance = vision.distance
    val turnpower = clamp(turn!!.calculate(value, 0.0), 0.4)
    val forwardpower = clamp(forward!!.calculate(distance, 5.0), 0.4)
    drive.arcadeDrive(-forwardpower, turnpower, false)
  }

  override fun end(interrupted: Boolean) {
    turn!!.reset()
    drive.stopMotor()
  }

  override fun isFinished(): Boolean {
    return false
  }
}