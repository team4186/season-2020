package frc.commands.auto;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.math.Maths;
import frc.robot.maps.RobotMap;

public class LeaveLine extends CommandBase {
    private final DifferentialDrive drive;
    private ProfiledPIDController left;
    private ProfiledPIDController right;
    private final Encoder leftEncoder;
    private final Encoder rightEncoder;
    private final double dist;
    private double wait;
    private final RobotMap map;

    /**
     * Goes to a certain distance defined by "distance"
     *
     * @param drive        The drivetrain.
     * @param leftEncoder  The drivetrain leftEncoder.
     * @param rightEncoder The drivetrain rightEncoder.
     * @param distance     The distance it goes to.
     */

    public LeaveLine(
            RobotMap map,
            DifferentialDrive drive,
            Encoder leftEncoder,
            Encoder rightEncoder,
            double distance
    ) {
        this.map = map;
        this.drive = drive;
        this.leftEncoder = leftEncoder;
        this.rightEncoder = rightEncoder;
        this.dist = distance;
    }

    @Override
    public void initialize() {
        wait = 0;

        rightEncoder.reset();
        leftEncoder.reset();

        right = map.makeLLPIDs();
        left = map.makeLLPIDs();
    }

    @Override
    public void execute() {
        double distance = dist * map.getLLMult();
        double rightOut = Maths.clamp(right.calculate(rightEncoder.getDistance(), distance), 0.4);
        double leftOut = Maths.clamp(left.calculate(leftEncoder.getDistance(), distance), 0.4);

        drive.tankDrive(-leftOut, -rightOut, false);

        if (right.atGoal() && left.atGoal()) {
            wait = wait + 1;
        } else {
            wait = 0;
        }
    }

    @Override
    public void end(boolean interrupted) {
        rightEncoder.reset();
        leftEncoder.reset();

        drive.stopMotor();
        right.reset(0, 0);
        left.reset(0, 0);

        System.out.println("Line Left!");
    }

    @Override
    public boolean isFinished() {
        return wait >= 15;
    }
}
