package frc.subsystems.vision;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LimelightRunner implements VisionRunner {
    private final NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");

    public LimelightRunner() {
    }

    public void init() {

    }

    public void periodic() {
        SmartDashboard.putBoolean("Has Target?", hasTarget());
        SmartDashboard.putNumber("Alignment Offset", getAlignX());
        SmartDashboard.putNumber("Distance", getDistance());
    }

    public boolean hasTarget() {
        double tv = yOffset() >= 0 ? table.getEntry("tv").getDouble(0) : 0;
        return tv == 1;
    }

    public double xOffset() {
        return hasTarget() ? table.getEntry("tx").getDouble(0) : 0;
    }

    public double getAlignX() {
        return scaler(xOffset());
    }

    public double yOffset() {
        return table.getEntry("ty").getDouble(0);
    }

    public double height() {
        return table.getEntry("tvert").getDouble(0);
    }

    public double getDistance() {
        double targetHeight = 98.125;
        double cameraHeight = 14; //Subject to change
        double cameraAngle = 12.632155; //Subject to change (ish)
        double targetAngle = yOffset();
        double totalAngleRad = Math.toRadians(cameraAngle + targetAngle);
        double distance = (targetHeight - cameraHeight) / Math.tan(totalAngleRad);
        if (hasTarget()) return distance / 12;
        else return Double.NaN;
        // double findAngle = Math.toDegrees(Math.atan((targetHeight-cameraHeight)/144)) - targetAngle;
        // return findAngle;
    }

    private double scaler(double value) {
        return value * 0.0335570469798658;
    }
}
