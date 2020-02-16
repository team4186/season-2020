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
        double calibratedHeight = 27; //35 for the shooter target, 17 for the wide strip of vision tape.
        double calibratedDistance = 5; //10 for shooter target, 5 for wide vision tape.
        double focalLength = (calibratedHeight * calibratedDistance)/realHeight; //Calibrated Height vs Real Height at a calibration distance

        // if(SmartDashboard.getNumber("Height", 0) <= calibratedHeight) {
        distance = (realHeight* focalLength)/(observedHeight);
        // } else{
        //     distance = 9;
        // }

        return distance;
    }
    

}
