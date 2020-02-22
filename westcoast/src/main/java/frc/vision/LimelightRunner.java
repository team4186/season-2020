package frc.vision;

import edu.wpi.first.networktables.NetworkTableInstance;

public class LimelightRunner {
    private double tv;
    private double tx;
    private double ty;

    public LimelightRunner(){
        tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
        tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    }

    public boolean hasTarget(){
        return tv == 1;
    }

    public double xOffset(){
        return tx;
    }

    public double yOffset(){
        return ty;
    }

    public double distance(){
        return 1; //WIP, use http://docs.limelightvision.io/en/latest/cs_estimating_distance.html to build the math for this. 
    }
}
