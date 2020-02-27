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
        tv = table.getEntry("tv").getDouble(0);
        return tv == 1;
    }

    public double xOffset(){
        tx = table.getEntry("tx").getDouble(0);
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
        // if(Double.isInfinite(distance)){
        //     distance = 15;
        //   }
        return 5; //WIP, use http://docs.limelightvision.io/en/latest/cs_estimating_distance.html to build the math for this. 
    }

    private double scaler(double value){
        return value*0.0335570469798658;
      }
}
