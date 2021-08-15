package frc.subsystems.vision.targeting;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.subsystems.vision.VisionRunner;

public class FindTarget extends CommandBase {
    private final VisionRunner vision;
    private final DifferentialDrive drive;

    public FindTarget(
            DifferentialDrive drive,
            VisionRunner vision
    ) {
        this.vision = vision;
        this.drive = drive;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        drive.tankDrive(-0.35, 0.35);
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return vision.hasTarget();
    }
}
