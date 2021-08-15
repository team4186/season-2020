package frc.subsystems.vision.targeting

import edu.wpi.first.wpilibj.controller.PIDController
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.math.Maths.clamp
import frc.robot.maps.RobotMap
import frc.subsystems.vision.VisionRunner

class AlignToTarget
/**
 * Turns to a target found by GRIP.
 *
 * @param drive  The drivetrain.
 * @param vision The vision runner.
 */(
    private val map: RobotMap,
    private val drive: DifferentialDrive,
    private val vision: VisionRunner
) : CommandBase() {
  private var turn: PIDController? = null
  private var wait = 0.0
  override fun initialize() {
    wait = 0.0
    turn = map.makeAlignPIDs()
  }

  override fun execute() {
    if (vision.hasTarget()) {
      val value = vision.alignX
      val turnpower = clamp(turn!!.calculate(value, 0.0), 0.4)
      drive.arcadeDrive(0.0, turnpower, false)
    }
    else {
      drive.arcadeDrive(0.0, 0.0, false)
    }
    wait = if (turn!!.atSetpoint()) wait + 1 else 0.0
  }

  override fun end(interrupted: Boolean) {
    turn!!.reset()
    drive.stopMotor()
    println("Ready, Aim, Fire!")
  }

  override fun isFinished(): Boolean {
    return wait >= 10 && vision.hasTarget()
  }
}