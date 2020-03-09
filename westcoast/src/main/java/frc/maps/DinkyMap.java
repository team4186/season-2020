package frc.maps;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile.Constraints;

public class DinkyMap implements RobotMap {

    public ProfiledPIDController makeLLPIDs() {
        ProfiledPIDController pid = new ProfiledPIDController(0.1, 0, 0, new Constraints(600, 500));
        pid.setTolerance(5, 100);
        pid.disableContinuousInput();
        pid.reset(0, 0);
        return pid;
    }

    public ProfiledPIDController makePTPIDs() {
        ProfiledPIDController pid = new ProfiledPIDController(0.1, 0, 0, new Constraints(150, 150));
        pid.setTolerance(5, 50);
        pid.disableContinuousInput();
        pid.reset(0, 0);
        return pid;
    }

    public PIDController makeDrivePIDs() {
        PIDController pid = new PIDController(0.5, 0.15, 0);
        pid.setTolerance(0.5);
        pid.disableContinuousInput();
        pid.reset();
        return pid;
    }

    public PIDController makeAlignPIDs() {
        PIDController pid = new PIDController(0.3, 0.1, 0.01);
        pid.disableContinuousInput();
        pid.setTolerance(0.1);
        pid.reset();
        return pid;
    }

    public PIDController makeForwardCAlignPIDs() {
        PIDController pid = new PIDController(0.2, 0, 0.03);
        pid.disableContinuousInput();
        pid.setTolerance(0);
        pid.reset();
        return pid;
    }

    public PIDController makeTurnCAlignPIDs() {
        PIDController pid = new PIDController(0.1, 0, 0.01);
        pid.disableContinuousInput();
        pid.setTolerance(0.2);
        pid.reset();
        return pid;
    }

    public PIDController makeStayOnTargetPIDs() {
        PIDController pid = new PIDController(0.1, 0, 0);
        pid.disableContinuousInput();
        pid.setTolerance(0.2);
        pid.reset();
        return pid;
    }

    public boolean getReversed() {
        return false;
    }

    public double getLLMult() {
        return 0;
    }

    public double getPTMult() {
        return 0;
    }

    @Override
    public DigitalInput getIndexSensor() {
        return new DigitalInput(0);
    }

    @Override
    public DigitalInput getMagSensor() {
        return new DigitalInput(1);
    }

    @Override
    public DigitalInput getShooterSensor() {
        return new DigitalInput(2);
    }

    @Override
    public WPI_TalonSRX getMainShooter() {
        return new WPI_TalonSRX(8);
    }

    @Override
    public WPI_TalonSRX getSecondaryShooter() {
        return new WPI_TalonSRX(9);
    }

    @Override
    public SpeedController getIntakeMotor() {
        return new WPI_VictorSPX(7);
    }

    @Override
    public SpeedController getIndexMotor() {
        return new VictorSP(10);
    }

    @Override
    public SpeedController getMagMotor() {
        return new VictorSP(12);
    }

    
}
