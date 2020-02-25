package frc.robotMaps;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile.Constraints;

public class ShinDestroyerMap implements RobotMap {

    public ProfiledPIDController makeLLPIDs() {
        ProfiledPIDController pid = new ProfiledPIDController(0.02, 0, 0.001, new Constraints(1000, 750));
        pid.setTolerance(5, 100);
        pid.disableContinuousInput();
        pid.reset(0, 0);
        return pid;
    }

    public ProfiledPIDController makePTPIDs() {
        ProfiledPIDController pid = new ProfiledPIDController(0.05, 0, 0, new Constraints(500, 300)); //untuned
        pid.setTolerance(5, 50);
        pid.disableContinuousInput();
        pid.reset(0, 0);
        return pid;
    }

    public PIDController makeDrivePIDs() {
        PIDController pid = new PIDController(0.5, 0.15, 0); //untuned
        pid.setTolerance(0.5); //untuned
        pid.disableContinuousInput();
        pid.reset();
        return pid;
    }

    public PIDController makeAlignPIDs() {
        // PIDController pid = new PIDController(0.12, 0.1, 0.03);
        // pid.disableContinuousInput();
        // pid.setTolerance(0.1);
        PIDController pid = new PIDController(0.7, 0.1, 0.07);
        pid.disableContinuousInput();
        pid.setTolerance(0.1);
        pid.reset();
        return pid;
    }

    public PIDController makeForwardCAlignPIDs() {
        PIDController pid = new PIDController(0.2, 0, 0.03); //untuned
        pid.disableContinuousInput(); 
        pid.setTolerance(0); //untuned
        pid.reset();
        return pid;
    }

    public PIDController makeTurnCAlignPIDs() {
        PIDController pid = new PIDController(0.1, 0, 0.01); //untuned
        pid.disableContinuousInput();
        pid.setTolerance(0.2); //untuned
        pid.reset();
        return pid;
    }

    public boolean getReversed() {
        return false;
    }

    public double getLLMult() {
    return 62;
    }

    public double getPTMult() {
        return 1.04;
    }
}
