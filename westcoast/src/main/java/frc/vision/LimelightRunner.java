package frc.vision;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LimelightRunner implements VisionRunner{
    private double tv;
    private double tx;
    private double ty;
    private double height;
    private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");

    public LimelightRunner(){
    }

    public void init(){

    }

    public boolean hasTarget(){
        if(yOffset() >= 0) tv = table.getEntry("tv").getDouble(0);
        else tv = 0;
        return tv == 1;
    }

    public double xOffset(){
        if(hasTarget()) tx = table.getEntry("tx").getDouble(0);
        else tx = 0;
        return tx;
    }

    public double getAlignX(){
        return scaler(xOffset());
    }

    public double yOffset(){
        ty = table.getEntry("ty").getDouble(0);
        return ty;
    }

    public double height(){
        height = table.getEntry("tvert").getDouble(0);
        return height;
    }

    public double getDistance(){
        double targetHeight = 98.125;
        double cameraHeight = 14; //Subject to change
        double cameraAngle = 12.632155; //Subject to change (ish)
        double targetAngle = yOffset();
        double totalAngleRad = Math.toRadians(cameraAngle+targetAngle);
        double distance = (targetHeight - cameraHeight) / Math.tan(totalAngleRad);
        if(hasTarget()) return distance/12;
        else return Double.NaN;
        // double findAngle = Math.toDegrees(Math.atan((targetHeight-cameraHeight)/144)) - targetAngle;
        // return findAngle;
    }

    private double scaler(double value){
        return value*0.0335570469798658;
      }
}
