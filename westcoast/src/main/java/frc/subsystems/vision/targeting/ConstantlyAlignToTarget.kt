package frc.subsystems.vision.targeting;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.math.Maths;
import frc.robot.maps.RobotMap;
import frc.subsystems.vision.VisionRunner;

public class ConstantlyAlignToTarget extends CommandBase {
    private final DifferentialDrive drive;
    private PIDController turn;
    private PIDController forward;
    private final VisionRunner vision;
    private final RobotMap map;

    public ConstantlyAlignToTarget(
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
        turn = map.makeTurnCAlignPIDs();
        forward = map.makeForwardCAlignPIDs();
    }

    @Override
    public void execute() {
        double value = vision.getAlignX();
        double distance = vision.getDistance();

        double turnpower = Maths.clamp(turn.calculate(value, 0), 0.4);
        double forwardpower = Maths.clamp(forward.calculate(distance, 5), 0.4);

        drive.arcadeDrive(-forwardpower, turnpower, false);
    }

    @Override
    public void end(boolean interrupted) {
        turn.reset();
        drive.stopMotor();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
