package frc.autonomousCommands;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.math.*;

public class PerfectTurn extends CommandBase {
  private DifferentialDrive drive;
  private ProfiledPIDController turnRight;
  private ProfiledPIDController turnLeft;
  private double angle;
  private Encoder leftEncoder;
  private Encoder rightEncoder;
  private double wait;
  private Constraints constraints;

  /**
   * Turns to a certain angle defined by "angle"
   * @param drive The drivetrain.
   * @param leftEncoder The drivetrain leftEncoder.
   * @param rightEncoder The drivetrain rightEncoder.
   * @param angle The angle it turns to.
   */

  public PerfectTurn(
    DifferentialDrive drive,
    Encoder leftEncoder,
    Encoder rightEncoder,
    double angle
  ) {
    this.drive = drive;
    this.angle = angle;
    this.leftEncoder = leftEncoder;
    this.rightEncoder = rightEncoder;
  }

  @Override
  public void initialize() {
    wait = 0;

    leftEncoder.reset();
    rightEncoder.reset();

    constraints = new Constraints(150, 150);
    double P = 0.1;
    double I = 0;
    double D = 0;
    turnLeft = new ProfiledPIDController(P, I, D, constraints);
    turnRight = new ProfiledPIDController(P, I, D, constraints);

    turnLeft.reset(0, 0);
    turnLeft.setTolerance(5, 50);
    turnLeft.disableContinuousInput();

    turnRight.reset(0, 0);
    turnRight.setTolerance(5, 50);
    turnRight.disableContinuousInput();
  }

  @Override
  public void execute() {
    double setpoint = angle*1.45;
    double rightside = Maths.clamp(turnRight.calculate(-rightEncoder.getDistance(), -setpoint),0.4);
    double leftside = Maths.clamp(turnLeft.calculate(-leftEncoder.getDistance(), setpoint),0.4);

    drive.tankDrive(leftside, rightside, false);

    if(turnRight.atGoal() && turnLeft.atGoal()){
      wait = wait + 1;
    }
    else{
      wait = 0;
    }
  }

  @Override
  public void end(boolean interrupted) {
    rightEncoder.reset();
    leftEncoder.reset();

    drive.stopMotor();
    turnRight.reset(0, 0);
    turnLeft.reset(0, 0);

    System.out.println("Turn Finished!");
  }

  @Override
  public boolean isFinished() {
    return wait >= 10;
  }
}
