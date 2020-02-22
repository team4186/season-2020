package frc.autonomousCommands;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.commands.Shoot;
import frc.vision.AlignToTarget;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class TargetAutonomous extends SequentialCommandGroup {

  public TargetAutonomous(
    DifferentialDrive drive,
    Encoder leftEncoder,
    Encoder rightEncoder,
    double distance,
    boolean lime
  ) {
    super(
      new LeaveLine(drive, leftEncoder, rightEncoder, distance),
      new WaitCommand(1),
      new AlignToTarget(drive, lime),
      new Shoot()
    );
  }
}
