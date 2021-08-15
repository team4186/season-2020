package frc.vision

import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj.util.Units
import kotlin.math.tan

class LimelightRunner : VisionRunner {
  private val table = NetworkTableInstance.getDefault().getTable("limelight")

  override fun periodic() {
    SmartDashboard.putBoolean("Has Target?", hasTarget)
    SmartDashboard.putNumber("Alignment Offset", alignX)
    SmartDashboard.putNumber("Distance", distance)
  }

  override val hasTarget: Boolean
    get() {
      val tv: Double = if (yOffset >= 0) table.getEntry("tv").getDouble(0.0) else 0.0
      return tv == 1.0
    }

  override val xOffset: Double
    get() {
      return if (hasTarget) table.getEntry("tx").getDouble(0.0) else 0.0
    }

  override val alignX: Double
    get() = scaler(xOffset)

  override val yOffset: Double
    get() {
      return table.getEntry("ty").getDouble(0.0)
    }

  override val height: Double
    get() {
      return table.getEntry("tvert").getDouble(0.0)
    }

  // double findAngle = Math.toDegrees(Math.atan((targetHeight-cameraHeight)/144)) - targetAngle;
  // return findAngle;
  //Subject to change
  override val distance: Double
    get() {
      val targetHeight = 98.125
      val cameraHeight = 14.0 //Subject to change
      val cameraAngle = 12.632155 //Subject to change (ish)
      val targetAngle = yOffset
      val totalAngleRad = Units.degreesToRadians(cameraAngle + targetAngle)
      val distance = (targetHeight - cameraHeight) / tan(totalAngleRad)
      return if (hasTarget) distance / 12 else Double.NaN
      // double findAngle = Math.toDegrees(Math.atan((targetHeight-cameraHeight)/144)) - targetAngle;
      // return findAngle;
    }

  private fun scaler(value: Double): Double {
    return value * 0.0335570469798658
  }
}