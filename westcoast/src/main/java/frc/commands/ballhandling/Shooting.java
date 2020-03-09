package frc.commands.ballhandling;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.maps.RobotMap;
import frc.subsystems.*;
import frc.subsystems.vision.*;
import frc.subsystems.vision.targeting.*;

public class Shooting extends SequentialCommandGroup {

  public Shooting(RobotMap map, DifferentialDrive drive, VisionRunner vision, BallHandlingSubsystem ballHandler) {
    addCommands(
      new ShooterAccelerator(ballHandler),
      new Shoot(ballHandler)
    );
    addRequirements(ballHandler);
  }
}
