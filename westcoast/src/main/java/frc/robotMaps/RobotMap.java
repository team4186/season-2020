package frc.robotMaps;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;

public interface RobotMap {
    public ProfiledPIDController makeLLPIDs();
    public ProfiledPIDController makePTPIDs();
    public PIDController makeDrivePIDs();
    public PIDController makeAlignPIDs();
    public PIDController makeForwardCAlignPIDs();
    public PIDController makeTurnCAlignPIDs();
    public boolean getReversed();
}
