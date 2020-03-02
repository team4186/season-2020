package frc.autonomousCommands;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.commands.Shoot;
import frc.vision.*;
import frc.robotMaps.*;
import edu.wpi.first.wpilibj2.command.WaitCommand;

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
      parallel(new Shoot(), new StayOnTarget(map, drive, vision))
    );
  }
}
