package frc.vision;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.math.*;

public class AlignToTarget extends CommandBase {
  private String name;
  private DifferentialDrive drive;
  private PIDController forward;
  private PIDController turn;
  private double wait;
  private double value;
  private boolean end;
  private double distance;

  /**
   * Turns to a target found by GRIP.
   * @param drive The drivetrain.
   * @param leftEncoder The drivetrain leftEncoder.
   * @param rightEncoder The drivetrain rightEncoder.
   * @param name The name of the SmartDashboard published value.
   */
  public AlignToTarget(
    DifferentialDrive drive,
    String name
  ) {
    this.name = name;
    this.drive = drive;
  }

  @Override
  public void initialize() {
    wait = 0;

    forward.reset();
    turn.reset();

    forward = new PIDController(0.2, 0, 0.03);
    turn = new PIDController(0.05, 0, 0.005);

    forward.reset();
    forward.setTolerance(10, 50);
    forward.disableContinuousInput();

    turn.reset();
    turn.setTolerance(10, 50);
    turn.disableContinuousInput();
  }

  @Override
  public void execute() {
    value = SmartDashboard.getNumber(name, 160);
    distance = DistanceToTarget.distance("Height");
    if(Double.isInfinite(distance)){
      distance = 5;
    }

    double turnpower = Maths.clamp(turn.calculate(value, 0), 0.3);
    double forwardpower = Maths.clamp(forward.calculate(distance, 5), 0.4);

    drive.arcadeDrive(-forwardpower, turnpower, false);

    if(forward.atSetpoint() && turn.atSetpoint()){
      wait = wait + 1;
      if(wait >= 10){
        end = true;
      }
      else{
        end = false;
      }
    }
    else{
      end = false;
    }

    SmartDashboard.putNumber("Offset", scaler(value));
  }

  @Override
  public void end(boolean interrupted) {
    forward.reset();
    turn.reset();
    drive.stopMotor();
  }

  @Override
  public boolean isFinished() {
    if(end){
      return true;
    }
    else{
      return false;
    }
  }

  private double scaler(double value){
    return ((value-160)/160);
  }
}
