package frc.vision;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.vision.VisionThread;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

public class RioVisionRunner {
  private VisionThread visionThread;
  private final UsbCamera camera;
  private final Object imgLock = new Object();
  private double centerX = 0.0;
  private double centerY = 0.0;
  private double height = 0.0;

  public RioVisionRunner(
    UsbCamera camera
  ) {
    this.camera = camera;
  }

  public void init() {
    visionThread = new VisionThread(camera, new GripPipeline(), pipeline -> {
      if (!pipeline.convexHullsOutput().isEmpty()) {
        Rect r = Imgproc.boundingRect(pipeline.convexHullsOutput().get(0));
        synchronized (imgLock) {
            centerX = r.x + (r.width / 2);
            centerY = r.y + (r.height / 2);
            height = r.height;
        }
      }
      else{
        synchronized (imgLock) {
          centerX = 160;
          centerY = 120;
          height = 0;
        }
      }
    });
    visionThread.start();
  }

  public double getCenterX(){
    double centerX;
    synchronized(imgLock){
        centerX = this.centerX;    
    }
    return centerX;
  }
  
  public double getCenterY(){
    double centerY;
    synchronized(imgLock){
        centerY = this.centerY;
    }
    return centerY;
  }
  
  public double getHeight(){
    double height;
    synchronized(imgLock) {
        height = this.height;
    }   
    return height;
  }

  public double getDistance(){
        double observedHeight = getHeight();
        double distance = 0;
        double realHeight = 17/12;
        double calibratedHeight = 17; //35 for the shooter target, 17 for the wide strip of vision tape.
        double focalLength = (calibratedHeight * 10)/realHeight; //Calibrated Height vs Real Height at a calibration distance (10 feet here)

        // if(height <= calibratedHeight) {
        // distance = (realHeight* focalLength)/(observedHeight);
        // } else{
        //     distance = 9;
        // }
        distance = (realHeight* focalLength)/(observedHeight);

        return distance;
    }
}
