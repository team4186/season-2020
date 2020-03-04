package frc.vision.Targeting;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.math.Maths;
import frc.vision.*;
import frc.robotMaps.RobotMap;

public class StayOnTarget extends CommandBase {
  private RobotMap map;
  private PIDController pid;
  private DifferentialDrive drive;
  private VisionRunner vision;
  private double value;

  public StayOnTarget(
    RobotMap map,
    DifferentialDrive drive,
    VisionRunner vision
  ) {
    this.map = map;
    this.drive = drive;
    this.vision = vision;
  }

  @Override
  public void initialize() {
    pid = map.makeStayOnTargetPIDs();
  }

  @Override
  public void execute() {
    value = vision.getAlignX();

    double turn = Maths.clamp(pid.calculate(value, 0), 0.4);

    drive.arcadeDrive(0, turn, false);
  }

  @Override
  public void end(boolean interrupted) {
    pid.reset();
    drive.stopMotor();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
