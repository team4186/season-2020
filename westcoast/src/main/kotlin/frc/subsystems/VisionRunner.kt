package frc.subsystems

interface VisionRunner {
  fun init()
  fun periodic()
  fun hasTarget(): Boolean
  fun xOffset(): Double
  val alignX: Double
  fun yOffset(): Double
  fun height(): Double
  val distance: Double
}