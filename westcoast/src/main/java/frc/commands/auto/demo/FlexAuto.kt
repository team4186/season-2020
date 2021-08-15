package frc.commands.auto.demo;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.commands.auto.LeaveLine;
import frc.commands.auto.PerfectTurn;
import frc.robot.maps.RobotMap;
import frc.subsystems.BallHandlingSubsystem;
import frc.subsystems.vision.VisionRunner;
import frc.subsystems.vision.targeting.AlignToTarget;

public class FlexAuto extends SequentialCommandGroup {

    public FlexAuto(
            RobotMap map,
            DifferentialDrive drive,
            Encoder leftEncoder,
            Encoder rightEncoder,
            VisionRunner vision,
            BallHandlingSubsystem ballHandler
    ) {

        super(
                new LeaveLine(map, drive, leftEncoder, rightEncoder, -1),
                new WaitCommand(0),
                new PerfectTurn(map, drive, leftEncoder, rightEncoder, 180),
                new WaitCommand(0),
                new LeaveLine(map, drive, leftEncoder, rightEncoder, 9),
                new WaitCommand(0),
                new PerfectTurn(map, drive, leftEncoder, rightEncoder, 180),
                new WaitCommand(0),
                new AlignToTarget(map, drive, vision)
        );
    }
}
