package frc.vision;

import edu.wpi.first.networktables.NetworkTableInstance;

public class LimelightRunner {
    private double tv;
    private double tx;
    private double ty;

    public LimelightRunner(){
    }

    public boolean hasTarget(){
        tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
        return tv == 1;
    }

    public double xOffset(){
        ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
        return tx;
    }

    public double yOffset(){
        ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
        return ty;
    }

    public double distance(){
        return 1; //WIP, use http://docs.limelightvision.io/en/latest/cs_estimating_distance.html to build the math for this. 
    }
}
