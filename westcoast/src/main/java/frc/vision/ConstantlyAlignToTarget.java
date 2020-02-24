package frc.vision;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robotMaps.*;
import frc.math.Maths;

public class ConstantlyAlignToTarget extends CommandBase {
  private DifferentialDrive drive;
  private PIDController turn;
  private PIDController forward;
  private double value;
  private double distance;
  private VisionRunner vision;
  private RobotMap map;

  public ConstantlyAlignToTarget(
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
    turn = map.makeTurnCAlignPIDs();
    forward = map.makeForwardCAlignPIDs();
  }

  @Override
  public void execute() {
    value = vision.getAlignX();
    distance = vision.getDistance();
    
    double turnpower = Maths.clamp(turn.calculate(value, 0), 0.4);
    double forwardpower = Maths.clamp(forward.calculate(distance, 5), 0.4);

    drive.arcadeDrive(-forwardpower, turnpower, false);
  }

  @Override
  public void end(boolean interrupted) {
    turn.reset();
    drive.stopMotor();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
