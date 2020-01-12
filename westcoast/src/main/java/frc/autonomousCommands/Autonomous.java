package frc.autonomousCommands;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class Autonomous extends SequentialCommandGroup {

  public Autonomous(
    DifferentialDrive drive,
    AHRS ahrs,
    Encoder encoder,
    double distance1,
    double rotation1,
    double distance2,
    double rotation2
  ) {
    super(
    new LeaveLine(drive, encoder, ahrs, distance1), 
    new Wait(50),
    new PerfectTurn(drive, ahrs, rotation1), 
    new Wait(50),
    new LeaveLine(drive, encoder, ahrs, distance2), 
    new Wait(50),
    new PerfectTurn(drive, ahrs, rotation2),
    new Wait(50),
    new LeaveLine(drive, encoder, ahrs, distance1), 
    new Wait(50),
    new PerfectTurn(drive, ahrs, rotation1), 
    new Wait(50),
    new LeaveLine(drive, encoder, ahrs, distance2), 
    new Wait(50),
    new PerfectTurn(drive, ahrs, rotation2),
    new Wait(50)
    );
  }
}