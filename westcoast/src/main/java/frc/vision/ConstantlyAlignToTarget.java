package frc.vision;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.math.DistanceToTarget;
import frc.math.Maths;

public class ConstantlyAlignToTarget extends CommandBase {
  private String name;
  private DifferentialDrive drive;
  private PIDController turn;
  private PIDController forward;
  private double value;
  private double distance;

  /**
   * Moves to a target found by GRIP
   * @param name The name of the SmartDashboard published value.
   * @param drive The drivetrain.
   */
  public ConstantlyAlignToTarget(
    String name,
    DifferentialDrive drive
  ) {
    this.name = name;
    this.drive = drive;
  }

  @Override
  public void initialize() {
    turn = new PIDController(0.2, 0, 0.03);
    forward = new PIDController(0.1, 0, 0.01);

    turn.reset();
    turn.setTolerance(0);
    turn.disableContinuousInput();
    
    forward.reset();
    forward.setTolerance(0.2);
    forward.disableContinuousInput();
  }

  @Override
  public void execute() {
    value = scaler(SmartDashboard.getNumber(name, 160));
    distance = DistanceToTarget.distance("Height");
    if(Double.isInfinite(distance)){
      distance = 5;
    }
    
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

  private double scaler(double value){
    return ((value-160)/160);
  }
}
