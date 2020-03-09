package frc.commands.auto;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.commands.ballhandling.*;
import frc.subsystems.BallHandlingSubsystem;
import frc.subsystems.vision.*;
import frc.subsystems.vision.targeting.*;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.maps.*;

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
      new AlignToTarget(map, drive, vision),
      parallel(new ShooterAccelerator(ballHandler), new StayOnTarget(map, drive, vision))
    );
  }
}
