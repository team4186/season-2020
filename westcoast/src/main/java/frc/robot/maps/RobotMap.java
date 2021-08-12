package frc.robot.maps;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;

public interface RobotMap {
    ProfiledPIDController makeLLPIDs();

    ProfiledPIDController makePTPIDs();

    PIDController makeDrivePIDs();

    PIDController makeAlignPIDs();

    PIDController makeForwardCAlignPIDs();

    PIDController makeTurnCAlignPIDs();

    PIDController makeStayOnTargetPIDs();

    boolean getReversed();

    double getLLMult();

    double getPTMult();

    DigitalInput getIndexSensor();

    DigitalInput getMagSensor();

    DigitalInput getShooterSensor();

    WPI_TalonSRX getMainShooter();

    WPI_TalonSRX getSecondaryShooter();

    SpeedController getIntakeMotor();

    SpeedController getIndexMotor();

    SpeedController getMagMotor();
}
