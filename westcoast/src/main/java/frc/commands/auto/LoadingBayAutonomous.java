package frc.commands.auto;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.commands.ballhandling.*;
import frc.subsystems.BallHandlingSubsystem;
import frc.subsystems.vision.*;
import frc.subsystems.vision.targeting.*;
import frc.maps.*;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class LoadingBayAutonomous extends SequentialCommandGroup {

  public LoadingBayAutonomous(
    RobotMap map,
    DifferentialDrive drive,
    Encoder leftEncoder,
    Encoder rightEncoder,
    double distance,
    double angle,
    VisionRunner vision,
    BallHandlingSubsystem ballHandler
  ) {

    super(
      new LeaveLine(map, drive, leftEncoder, rightEncoder, distance),
      new WaitCommand(1),
      new PerfectTurn(map, drive, leftEncoder, rightEncoder, angle),
      new WaitCommand(1),
      new AlignToTarget(map, drive, vision),
      parallel(new ShooterLogic(ballHandler), new StayOnTarget(map, drive, vision))
    );
  }
}
