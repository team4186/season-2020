package frc.autonomousCommands;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.vision.*;
import frc.commands.BallHandlers.ShooterLogic;
import frc.robotMaps.*;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.vision.Targeting.*;

public class CenterAutonomous extends SequentialCommandGroup {
  
  public CenterAutonomous(
    RobotMap map, 
    DifferentialDrive drive,
    Encoder leftEncoder,
    Encoder rightEncoder,
    double distance,
    double angle,
    VisionRunner vision
) {
    addCommands(
      new LeaveLine(map, drive, leftEncoder, rightEncoder, distance),
      new WaitCommand(1),
      new PerfectTurn(map, drive, leftEncoder, rightEncoder, angle),
      new WaitCommand(1),
      new AlignToTarget(map, drive, vision),
      parallel(new ShooterLogic(), new StayOnTarget(map, drive, vision))
    );
  }
}
