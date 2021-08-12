package frc.commands.ballhandling;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.maps.RobotMap;
import frc.subsystems.BallHandlingSubsystem;
import frc.subsystems.vision.VisionRunner;
import frc.subsystems.vision.targeting.AlignToTarget;
import frc.subsystems.vision.targeting.StayOnTarget;

public class Shooting extends SequentialCommandGroup {

    public Shooting(RobotMap map, DifferentialDrive drive, VisionRunner vision, BallHandlingSubsystem ballHandler) {
        addCommands(
                new AlignToTarget(map, drive, vision),
                race(
                        new ShooterAccelerator(ballHandler),
                        new WaitCommand(1.5)
                ),
                race(
                        new Shoot(ballHandler),
                        new StayOnTarget(map, drive, vision),
                        new WaitCommand(4)
                )
        );
        addRequirements(ballHandler);
    }
}
