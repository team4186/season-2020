package frc.subsystems.vision;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.vision.VisionThread;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

public class RioVisionRunner implements VisionRunner {
    private final Object imgLock = new Object();
    private double centerX = 0.0;
    private double centerY = 0.0;
    private double height = 0.0;
    private boolean target;

    public RioVisionRunner() {

    }

    public void init() {
        UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
        camera.setResolution(320, 240);
        camera.setFPS(30);
        camera.setExposureManual(20);

        VisionThread visionThread = new VisionThread(camera, new GripPipeline(), pipeline -> {
            if (!pipeline.convexHullsOutput().isEmpty()) {
                Rect r = Imgproc.boundingRect(pipeline.convexHullsOutput().get(0));
                synchronized (imgLock) {
                    centerX = r.x + (r.width / 2);
                    centerY = r.y + (r.height / 2);
                    height = r.height;
                    target = true;
                }
            } else {
                synchronized (imgLock) {
                    centerX = 160;
                    centerY = 120;
                    height = 0;
                    target = false;
                }
            }
        });
        visionThread.start();
    }

    public void periodic() {
        SmartDashboard.putBoolean("Has Target?", hasTarget());
        SmartDashboard.putNumber("Alignment Offset", getAlignX());
        SmartDashboard.putNumber("Distance", getDistance());
    }

    public boolean hasTarget() {
        return target;
    }

    public double getAlignX() {
        double centerX;
        synchronized (imgLock) {
            centerX = this.centerX;
        }
        return scaler(centerX);
    }

    public double xOffset() {
        double centerX;
        synchronized (imgLock) {
            centerX = this.centerX;
        }
        return scaler(centerX);
    }

    public double yOffset() {
        double centerY;
        synchronized (imgLock) {
            centerY = this.centerY;
        }
        return centerY;
    }

    public double height() {
        double height;
        synchronized (imgLock) {
            height = this.height;
        }
        return height;
    }

    public double getDistance() {
        // double observedHeight = height();
        // double distance = 0;
        // double realHeight = 17/12;
        // double calibratedHeight = 17; //35 for the shooter target, 17 for the wide strip of vision tape.
        // double focalLength = (calibratedHeight * 10)/realHeight; //Calibrated Height vs Real Height at a calibration distance (10 feet here)

        // distance = (realHeight* focalLength)/(observedHeight);

        // if(Double.isInfinite(distance)){
        //     distance = 15;
        //   }
        return 5;
    }

    private double scaler(double value) {
        return ((value - 160) / 160);
    }
}
