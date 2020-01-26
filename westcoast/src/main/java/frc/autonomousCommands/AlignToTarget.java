package frc.autonomousCommands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AlignToTarget extends CommandBase {
  private NetworkTable table;
  private DifferentialDrive drive;
  private PIDController pid;
  private double width;

  public AlignToTarget(
    NetworkTable table,
    DifferentialDrive drive,
    double width
  ) {
    this.table = table;
    this.drive = drive;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
