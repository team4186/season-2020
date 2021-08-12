package frc.subsystems.drive;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.math.Maths;
import frc.robot.maps.RobotMap;


public class GyroDrive extends CommandBase {
    private final DifferentialDrive drive;
    private PIDController pid;
    private final Joystick joy;
    private final AHRS ahrs;
    private double direction;
    private final RobotMap map;

    /**
     * Driving more accurately.
     *
     * @param drive    The drivetrain.
     * @param joystick The joystick.
     * @param ahrs     The navX.
     */
    public GyroDrive(
            RobotMap map,
            DifferentialDrive drive,
            Joystick joystick,
            AHRS ahrs
    ) {
        this.map = map;
        this.drive = drive;
        this.joy = joystick;
        this.ahrs = ahrs;
    }

    @Override
    public void initialize() {
        direction = map.getReversed() ? -1.0 : 1.0;

        pid = map.makeDrivePIDs();

        ahrs.reset();
    }

    @Override
    public void execute() {
        double input = Maths.pidclean(ahrs.getRate(), 6.0, 0.3);
        double pidraw = pid.calculate(input, Maths.joyclean(direction * joy.getX(), 0.05) * 4.6);
        double output = Maths.pidclean(-pidraw, 0.8, 0.05);
        drive.arcadeDrive(Maths.attenuate(direction * joy.getY()), output, false);
    }

    @Override
    public void end(boolean interrupted) {
        drive.stopMotor();
        ahrs.reset();
        pid.reset();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
