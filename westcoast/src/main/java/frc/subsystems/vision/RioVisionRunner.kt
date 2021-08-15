package frc.subsystems.vision

import edu.wpi.first.cameraserver.CameraServer
import edu.wpi.first.vision.VisionThread
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import org.opencv.imgproc.Imgproc

class RioVisionRunner : VisionRunner {
  private val imgLock = Any()
  private var centerX = 0.0
  private var centerY = 0.0
  private var height = 0.0
  private var target = false
  override fun init() {
    val camera = CameraServer.getInstance().startAutomaticCapture()
    camera.setResolution(320, 240)
    camera.setFPS(30)
    camera.setExposureManual(20)
    val visionThread = VisionThread(camera, GripPipeline()) { pipeline: GripPipeline ->
      if (!pipeline.convexHullsOutput().isEmpty()) {
        val r = Imgproc.boundingRect(pipeline.convexHullsOutput()[0])
        synchronized(imgLock) {
          centerX = (r.x + r.width / 2).toDouble()
          centerY = (r.y + r.height / 2).toDouble()
          height = r.height.toDouble()
          target = true
        }
      }
      else {
        synchronized(imgLock) {
          centerX = 160.0
          centerY = 120.0
          height = 0.0
          target = false
        }
      }
    }
    visionThread.start()
  }

  override fun periodic() {
    SmartDashboard.putBoolean("Has Target?", hasTarget())
    SmartDashboard.putNumber("Alignment Offset", alignX)
    SmartDashboard.putNumber("Distance", distance)
  }

  override fun hasTarget(): Boolean {
    return target
  }

  override val alignX: Double
    get() {
      var centerX: Double
      synchronized(imgLock) { centerX = this.centerX }
      return scaler(centerX)
    }

  override fun xOffset(): Double {
    var centerX: Double
    synchronized(imgLock) { centerX = this.centerX }
    return scaler(centerX)
  }

  override fun yOffset(): Double {
    var centerY: Double
    synchronized(imgLock) { centerY = this.centerY }
    return centerY
  }

  override fun height(): Double {
    var height: Double
    synchronized(imgLock) { height = this.height }
    return height
  }
  // double observedHeight = height();
  // double distance = 0;
  // double realHeight = 17/12;
  // double calibratedHeight = 17; //35 for the shooter target, 17 for the wide strip of vision tape.
  // double focalLength = (calibratedHeight * 10)/realHeight; //Calibrated Height vs Real Height at a calibration distance (10 feet here)

  // distance = (realHeight* focalLength)/(observedHeight);

  // if(Double.isInfinite(distance)){
  //     distance = 15;
  //   }
  override val distance: Double
    get() =// double observedHeight = height();
    // double distance = 0;
    // double realHeight = 17/12;
    // double calibratedHeight = 17; //35 for the shooter target, 17 for the wide strip of vision tape.
    // double focalLength = (calibratedHeight * 10)/realHeight; //Calibrated Height vs Real Height at a calibration distance (10 feet here)

    // distance = (realHeight* focalLength)/(observedHeight);

    // if(Double.isInfinite(distance)){
    //     distance = 15;
        //   }
      5.0

  private fun scaler(value: Double): Double {
    return (value - 160) / 160
  }
}