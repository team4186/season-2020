package frc.commands.auto;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.maps.RobotMap;
import frc.subsystems.BallHandlingSubsystem;
import frc.subsystems.vision.VisionRunner;
import frc.subsystems.vision.targeting.AlignToTarget;

public class TargetAutonomous extends SequentialCommandGroup {

    public TargetAutonomous(
            RobotMap map,
            DifferentialDrive drive,
            Encoder leftEncoder,
            Encoder rightEncoder,
            double distance,
            VisionRunner vision,
            BallHandlingSubsystem ballHandler
    ) {
        super(
                new LeaveLine(map, drive, leftEncoder, rightEncoder, distance),
                new WaitCommand(1),
                new AlignToTarget(map, drive, vision)
        );
    }
}
