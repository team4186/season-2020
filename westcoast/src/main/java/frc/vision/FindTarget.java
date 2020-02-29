package frc.vision;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class FindTarget extends CommandBase {
  private VisionRunner vision;
  private DifferentialDrive drive;

  public FindTarget(
    DifferentialDrive drive,
    VisionRunner vision
  ) {
    this.vision = vision;
    this.drive = drive;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    drive.tankDrive(-0.35, 0.35);
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return vision.hasTarget();
  }
}
