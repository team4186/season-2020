package frc.subsystems.drive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.maps.RobotMap;

public class TeleopDrive extends CommandBase {
    private final DifferentialDrive drive;
    private final Joystick joy;
    private double direction;
    private final RobotMap map;

    /**
     * Driving :).
     *
     * @param drive    The drivetrain.
     * @param joystick The joystick.
     */
    public TeleopDrive(
            RobotMap map,
            DifferentialDrive drive,
            Joystick joystick
    ) {
        this.drive = drive;
        this.joy = joystick;
        this.map = map;
    }

    @Override
    public void initialize() {
        direction = map.getReversed() ? -1.0 : 1.0;
    }

    @Override
    public void execute() {
        drive.arcadeDrive(attenuate(direction * joy.getY()), attenuate(-direction * joy.getX()));
    }

    @Override
    public void end(boolean interrupted) {
        drive.stopMotor();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    private double attenuate(double value) {
        boolean raw = joy.getRawButton(5);
        if (raw) {
            return (0.5 * value);
        } else {
            return (Math.signum(value) * Math.pow(Math.abs(value), 2.0));
        }
    }
}
