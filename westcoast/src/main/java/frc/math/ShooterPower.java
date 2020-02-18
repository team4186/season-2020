package frc.math;

public class ShooterPower {
    public static double power(double value){
        return value;
    }
}

//launch speed = motor rpm times wheel radius*2pi, then unit convert from inches per minute to ft/s (really back convert.)
//take distance, given our angle etc and calculate what speed it needs to be for y to be at 98.25 inches of height at x=0