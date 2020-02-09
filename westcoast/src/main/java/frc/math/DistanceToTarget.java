package frc.math;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DistanceToTarget {

    /**
     * Calculates the distance given the height observed by the camera of the vision target.
     * @param name The string that I use to send the variable between classes because I am stupid.
     */
    public static double distance(
        String name
    ) {
        double observedHeight = SmartDashboard.getNumber(name, 0);
        double distance = 0;
        double realHeight = 17/12;
        double calibratedHeight = 48;
        double focalLength = (calibratedHeight * 10)/realHeight; //Calibrated Height vs Real Height at a calibration distance (10 feet here)

        if(SmartDashboard.getNumber("Height", 0) <= calibratedHeight) {
        distance = (realHeight* focalLength)/(observedHeight);
        } else{
            distance = 9;
        }

        return distance;
    }
    

}
