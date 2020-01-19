package frc.autonomousCommands;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ProfiledLeaveLine extends CommandBase {
  private DifferentialDrive drive;
  private ProfiledPIDController left;
  private ProfiledPIDController right;
  private Encoder leftEncoder;
  private Encoder rightEncoder;
  private double dist;
  private double wait;
  private boolean end;
  private Constraints constraints;

  /**
   * Goes to a certain distance defined by "distance"
   * @param drive The drivetrain.
   * @param leftEncoder The drivetrain leftEncoder.
   * @param rightEncoder The drivetrain rightEncoder.
   * @param distance The distance it goes to.
   */

  public ProfiledLeaveLine(
    DifferentialDrive drive,
    Encoder leftEncoder,
    Encoder rightEncoder,
    double distance
  ) {
    this.leftEncoder = leftEncoder;
    this.rightEncoder = rightEncoder;
    this.drive = drive;
    this.dist = distance;
  }

  @Override
  public void initialize() {
    wait = 0;

    rightEncoder.reset();
    leftEncoder.reset();

    constraints = new Constraints(600, 500);
    right = new ProfiledPIDController(0.1, 0, 0, constraints);
    left = new ProfiledPIDController(0.1, 0, 0, constraints);
    
    right.reset(0, 0);
    right.setTolerance(5, 100);
    right.disableContinuousInput();

    left.reset(0, 0);
    left.setTolerance(5, 100);
    left.disableContinuousInput();
  }

  @Override
  public void execute() {
    double distance = dist*85;
    double rightOut = right.calculate(rightEncoder.get(), distance);
    double leftOut = left.calculate(leftEncoder.get(), distance);

    drive.tankDrive(-leftOut, -rightOut, false);

    if(right.atGoal() && left.atGoal() == true){
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
      wait = 0;
    }
  }

  @Override
  public void end(boolean interrupted) {
    rightEncoder.reset();
    leftEncoder.reset();

    drive.stopMotor();
    right.reset(0, 0);
    left.reset(0, 0);
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
}
