package frc.autonomousCommands;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robotMaps.*;

public class Square extends SequentialCommandGroup {

  /**
   * Runs a group of commands sequentially.
   * @param drive The drivetrain.
   * @param rightEncoder The drivetrain rightEncoder.
   * @param leftEncoder The drivetrain leftEncoder.
   * @param distance The distance for a command to go to.
   * @param rotation The angle for a command to turn to.
   */
  
  public Square(
    RobotMap map,
    DifferentialDrive drive,
    Encoder rightEncoder,
    Encoder leftEncoder,
    double distance,
    double rotation
  ) {
    super(
    new LeaveLine(map, drive, leftEncoder, rightEncoder, distance),
    new WaitCommand(1),
    new PerfectTurn(map, drive, leftEncoder, rightEncoder, rotation),
    new WaitCommand(1),
    new LeaveLine(map, drive, leftEncoder, rightEncoder, distance),
    new WaitCommand(1),
    new PerfectTurn(map, drive, leftEncoder, rightEncoder, rotation),
    new WaitCommand(1),
    new LeaveLine(map, drive, leftEncoder, rightEncoder, distance),
    new WaitCommand(1),
    new PerfectTurn(map, drive, leftEncoder, rightEncoder, rotation),
    new WaitCommand(1),
    new LeaveLine(map, drive, leftEncoder, rightEncoder, distance),
    new WaitCommand(1),
    new PerfectTurn(map, drive, leftEncoder, rightEncoder, rotation),
    new WaitCommand(1)
    );
  }
}
