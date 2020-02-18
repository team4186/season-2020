package frc.autonomousCommands;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.commands.Shoot;
import frc.vision.AlignToTarget;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class LoadingBayAutonomous extends SequentialCommandGroup {

  public LoadingBayAutonomous(
    DifferentialDrive drive,
    Encoder leftEncoder,
    Encoder rightEncoder,
    double distance,
    double angle,
    String name
  ) {

    super(
      new LeaveLine(drive, leftEncoder, rightEncoder, distance),
      new WaitCommand(1),
      new PerfectTurn(drive, leftEncoder, rightEncoder, angle),
      new AlignToTarget(drive, name),
      new Shoot()
    );
  }
}
