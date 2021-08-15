package frc.subsystems.vision.targeting;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.math.Maths;
import frc.robot.maps.RobotMap;
import frc.subsystems.vision.VisionRunner;

public class AlignToTarget extends CommandBase {
    private final DifferentialDrive drive;
    private PIDController turn;
    private double wait;
    private final VisionRunner vision;
    private final RobotMap map;

    /**
     * Turns to a target found by GRIP.
     *
     * @param drive  The drivetrain.
     * @param vision The vision runner.
     */
    public AlignToTarget(
            RobotMap map,
            DifferentialDrive drive,
            VisionRunner vision
    ) {
        this.map = map;
        this.drive = drive;
        this.vision = vision;
    }

    @Override
    public void initialize() {
        wait = 0;

        turn = map.makeAlignPIDs();
    }

    @Override
    public void execute() {
        if (vision.hasTarget()) {
            double value = vision.getAlignX();
            double turnpower = Maths.clamp(turn.calculate(value, 0.0), 0.4);
            drive.arcadeDrive(0.0, turnpower, false);
        } else {
            drive.arcadeDrive(0.0, 0.0, false);
        }

        wait = turn.atSetpoint() ? wait + 1 : 0;
    }

    @Override
    public void end(boolean interrupted) {
        turn.reset();
        drive.stopMotor();

        System.out.println("Ready, Aim, Fire!");
    }

    @Override
    public boolean isFinished() {
        return wait >= 10 && vision.hasTarget();
    }
}
