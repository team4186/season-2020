package frc.vision

import edu.wpi.first.cameraserver.CameraServer
import edu.wpi.first.vision.VisionThread
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import org.opencv.imgproc.Imgproc

class RioVisionRunner : VisionRunner {
  private val imgLock = Any()
  private var centerX = 0.0
  private var centerY = 0.0

  override fun init() {
    val camera = CameraServer.getInstance().startAutomaticCapture()
    camera.setResolution(320, 240)
    camera.setFPS(30)
    camera.setExposureManual(20)
    val visionThread = VisionThread(camera, GripPipeline()) { pipeline ->
      if (pipeline.convexHullsOutput().isNotEmpty()) {
        val r = Imgproc.boundingRect(pipeline.convexHullsOutput()[0])
        synchronized(imgLock) {
          centerX = (r.x + r.width / 2).toDouble()
          centerY = (r.y + r.height / 2).toDouble()
          height = r.height.toDouble()
          hasTarget = true
        }
      }
      else {
        synchronized(imgLock) {
          centerX = 160.0
          centerY = 120.0
          height = 0.0
          hasTarget = false
        }
      }
    }
    visionThread.start()
  }

  override fun periodic() {
    SmartDashboard.putBoolean("Has Target?", hasTarget)
    SmartDashboard.putNumber("Alignment Offset", alignX)
    SmartDashboard.putNumber("Distance", distance)
  }

  override var hasTarget: Boolean = false
    private set

  override val alignX: Double get() = scaler(synchronized(imgLock) { centerX })

  override val xOffset: Double get() = alignX

  override val yOffset: Double get() = synchronized(imgLock) { centerY }

  override var height: Double = 0.0
    get() = synchronized(imgLock) { field }
    private set

  // double observedHeight = height();
  // double distance = 0;
  // double realHeight = 17/12;
  // double calibratedHeight = 17; //35 for the shooter target, 17 for the wide strip of vision tape.
  // double focalLength = (calibratedHeight * 10)/realHeight; //Calibrated Height vs Real Height at a calibration distance (10 feet here)

  // distance = (realHeight* focalLength)/(observedHeight);

  // if(Double.isInfinite(distance)){
  //     distance = 15;
  //   }

  override val distance: Double get() = 5.0

  // double observedHeight = height();
  // double distance = 0;
  // double realHeight = 17/12;
  // double calibratedHeight = 17; //35 for the shooter target, 17 for the wide strip of vision tape.
  // double focalLength = (calibratedHeight * 10)/realHeight; //Calibrated Height vs Real Height at a calibration distance (10 feet here)

  // distance = (realHeight* focalLength)/(observedHeight);

  // if(Double.isInfinite(distance)){
  //     distance = 15;
  //   }

  private fun scaler(value: Double): Double {
    return (value - 160) / 160
  }
}