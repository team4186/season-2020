package frc.commands;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile.*;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.math.*;

public class EncoderDrive extends CommandBase {
  private DifferentialDrive drive;
  private Joystick joystick;
  private Encoder leftEncoder;
  private Encoder rightEncoder;
  private ProfiledPIDController left;
  private ProfiledPIDController right;
  private Constraints constraints;
  
  /**
   * A fully closed-loop drive system that is significantly more adaptable.
   * @param drive The drivetrain.
   * @param joystick The joystick.
   * @param leftEncoder The drivetrain leftEncoder.
   * @param rightEncoder The drivetrain rightEncoder.
   */
  public EncoderDrive(
    DifferentialDrive drive,
    Joystick joystick,
    Encoder leftEncoder,
    Encoder rightEncoder
  ) {
    this.drive = drive;
    this.joystick = joystick;
    this.leftEncoder = leftEncoder;
    this.rightEncoder = rightEncoder;
  }

  @Override
  public void initialize() {
    leftEncoder.reset();
    rightEncoder.reset();

    double P = 0.1;
    double I = 0.0;
    double D = 0.0;

    constraints = new Constraints(500, 100);
    right = new ProfiledPIDController(P, I, D, constraints);
    left = new ProfiledPIDController(P, I, D, constraints);

    right.reset(0, 0);
    right.setTolerance(100, 10);
    right.disableContinuousInput();

    left.reset(0, 0);
    left.setTolerance(100, 10);
    left.disableContinuousInput();
  }

  @Override
  public void execute() {
    double leftSetpoint;
    double rightSetpoint;
    double xSpeed = Maths.joyclean(joystick.getY(), 0.05);
    double zRotation = Maths.joyclean(joystick.getX(), 0.05);
    double maxInput = Math.copySign(Math.max(Math.abs(xSpeed), Math.abs(zRotation)), xSpeed);
    if (xSpeed >= 0.0) {
      // First quadrant, else second quadrant
      if (zRotation >= 0.0) {
        leftSetpoint = maxInput;
        rightSetpoint = xSpeed - zRotation;
      } else {
        leftSetpoint = xSpeed + zRotation;
        rightSetpoint = maxInput;
      }
    } else {
      // Third quadrant, else fourth quadrant
      if (zRotation >= 0.0) {
        leftSetpoint = xSpeed + zRotation;
        rightSetpoint = maxInput;
      } else {
        leftSetpoint = maxInput;
        rightSetpoint = xSpeed - zRotation;
      }
    }

    double leftOut = left.calculate(leftEncoder.getRate(), leftSetpoint*2000);
    double rightOut = right.calculate(rightEncoder.getRate(), rightSetpoint*2000);

    drive.tankDrive(-leftOut, -rightOut, false);
  }

  @Override
  public void end(boolean interrupted) {
    leftEncoder.reset();
    rightEncoder.reset();
    
    drive.stopMotor();
    left.reset(0, 0);
    right.reset(0, 0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
