package frc.vision

interface VisionRunner {
  fun init() {}
  fun periodic()
  val hasTarget: Boolean
  val xOffset: Double
  val alignX: Double
  val yOffset: Double
  val height: Double
  val distance: Double
}