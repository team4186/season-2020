package frc.autonomousCommands;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AVeryMarkCommand extends SequentialCommandGroup {

  public AVeryMarkCommand(
    DifferentialDrive drive,
    Encoder rightEncoder,
    Encoder leftEncoder
  ) {
    super(
    new Square(drive, rightEncoder, leftEncoder, 10, 90), 
    new Square(drive, rightEncoder, leftEncoder, 10, 90), 
    new Square(drive, rightEncoder, leftEncoder, 10, 90), 
    new Square(drive, rightEncoder, leftEncoder, 10, 90), 
    new Square(drive, rightEncoder, leftEncoder, 10, 90), 
    new Square(drive, rightEncoder, leftEncoder, 10, 90), 
    new Square(drive, rightEncoder, leftEncoder, 10, 90), 
    new Square(drive, rightEncoder, leftEncoder, 10, 90), 
    new Square(drive, rightEncoder, leftEncoder, 10, 90), 
    new Square(drive, rightEncoder, leftEncoder, 10, 90), 
    new Square(drive, rightEncoder, leftEncoder, 10, 90), 
    new Square(drive, rightEncoder, leftEncoder, 10, 90)
    );
  }
}
