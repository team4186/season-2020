package frc.autonomousCommands;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.commands.BallHandlers.ShooterLogic;
import frc.vision.*;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robotMaps.*;
import frc.vision.Targeting.*;

public class TargetAutonomous extends SequentialCommandGroup {

  public TargetAutonomous(
    RobotMap map,
    DifferentialDrive drive,
    Encoder leftEncoder,
    Encoder rightEncoder,
    double distance,
    VisionRunner vision
  ) {
    super(
      new LeaveLine(map, drive, leftEncoder, rightEncoder, distance),
      new WaitCommand(1),
      new AlignToTarget(map, drive, vision),
      parallel(new ShooterLogic(), new StayOnTarget(map, drive, vision))
    );
  }
}
