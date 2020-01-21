package frc.math;

public final class Maths{
   
   /**
   * Code originally from edu.wpi.first.wpiutil.math.MathUtil.
   * Returns value clamped between low and high boundaries.
   * @param value Value to clamp.
   * @param out   The boundary to which to clamp value.
   */
    public static double clamp(double value, double out) {
        return Math.max(-out, Math.min(value, out));
      }
    
    /**
     * Returns values outside of a certain zone, so as to remove noise.
     * @param value Value given.
     * @param deadzone The boundry in whicxh the value doesn't register.
     */
    public static double deadband(double value, double deadzone){
        if(value<deadzone&&value>deadzone*(-1)){
            return 0;
        }
        else{
            return value;
        }
    }

    public static double antiband(double value, double antizone){
        if(value<antizone*(-1)||value>antizone){
            return Math.signum(value)*1;
        }
        else{
            return value;
        }
    }

    /**
     * Attenuates a value by some amount to make the control curve cleaner.
     * @param value Value to be attenuated.
     */
    public static double attenuate(double value){
        double calculated = Math.signum(value)*Math.pow(Math.abs(value), 1.3);
        return calculated;
    }

    /**
     * Clamps and deadbands so as it creates a slower, less noisy PID loop.
     * @param value Value to be cleaned.
     * @param out The boundry to which to clamp value.
     * @param in The boundry in which the value doesn't register.
     */
    public static double pidclean(double value, double out, double in){
        return deadband(clamp(value, out), in);     
    }

    /**
     * Attenuates and deadbands so as to avoid joystick drift and control problems.
     * @param value Value to deadbanded and attenuated.
     * @param in The boundry in whicxh the value doesn't register.
     */
    public static double joyclean(double value, double in){
        return attenuate(deadband(value, in));
    }
}
