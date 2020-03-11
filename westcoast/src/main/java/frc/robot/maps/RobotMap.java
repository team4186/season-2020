package frc.robot.maps;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;

public interface RobotMap {
    public ProfiledPIDController makeLLPIDs();
    public ProfiledPIDController makePTPIDs();
    public PIDController makeDrivePIDs();
    public PIDController makeAlignPIDs();
    public PIDController makeForwardCAlignPIDs();
    public PIDController makeTurnCAlignPIDs();
    public PIDController makeStayOnTargetPIDs();
    public boolean getReversed();
    public double getLLMult();
    public double getPTMult();
    public DigitalInput getIndexSensor();
    public DigitalInput getMagSensor();
    public DigitalInput getShooterSensor();
    public WPI_TalonSRX getMainShooter();
    public WPI_TalonSRX getSecondaryShooter();
    public SpeedController getIntakeMotor();
    public SpeedController getIndexMotor();
    public SpeedController getMagMotor();
}
