package frc.commands.auto.demo;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.maps.*;

public class AVeryMarkCommand extends SequentialCommandGroup {

  public AVeryMarkCommand(
    RobotMap map,
    DifferentialDrive drive,
    Encoder rightEncoder,
    Encoder leftEncoder
  ) {
    super(
    new Square(map, drive, rightEncoder, leftEncoder, 10, 90), 
    new Square(map, drive, rightEncoder, leftEncoder, 10, 90), 
    new Square(map, drive, rightEncoder, leftEncoder, 10, 90), 
    new Square(map, drive, rightEncoder, leftEncoder, 10, 90), 
    new Square(map, drive, rightEncoder, leftEncoder, 10, 90), 
    new Square(map, drive, rightEncoder, leftEncoder, 10, 90), 
    new Square(map, drive, rightEncoder, leftEncoder, 10, 90), 
    new Square(map, drive, rightEncoder, leftEncoder, 10, 90), 
    new Square(map, drive, rightEncoder, leftEncoder, 10, 90), 
    new Square(map, drive, rightEncoder, leftEncoder, 10, 90), 
    new Square(map, drive, rightEncoder, leftEncoder, 10, 90), 
    new Square(map, drive, rightEncoder, leftEncoder, 10, 90)
    );
  }
}
