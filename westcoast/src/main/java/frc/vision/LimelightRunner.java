package frc.vision;

import edu.wpi.first.networktables.NetworkTableInstance;

public class LimelightRunner implements VisionRunner{
    private double tv;
    private double tx;
    private double ty;
    private double height;

    public LimelightRunner(){
    }

    public void init(){

    }

    public boolean hasTarget(){
        tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
        return tv == 1;
    }

    public double xOffset(){
        tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        return tx;
    }

    public double getAlignX(){
        tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        return scaler(tx);
    }

    public double yOffset(){
        ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
        return ty;
    }

    public double height(){
        height = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tvert").getDouble(0);
        return height;
    }

    public double getDistance(){
        // if(Double.isInfinite(distance)){
        //     distance = 15;
        //   }
        return 5; //WIP, use http://docs.limelightvision.io/en/latest/cs_estimating_distance.html to build the math for this. 
    }

    private double scaler(double value){
        return value*0.0335570469798658;
      }
}
