package frc.commands;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile.*;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.math.*;

public class EncoderDrive extends CommandBase {
  private DifferentialDrive drive;
  private Joystick joystick;
  private Encoder leftEncoder;
  private Encoder rightEncoder;
  // private ProfiledPIDController left;
  // private ProfiledPIDController right;
  private PIDController left;
  private PIDController right;
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
    // right = new ProfiledPIDController(P, I, D, constraints);
    // left = new ProfiledPIDController(P, I, D, constraints);

    left = new PIDController(P, I, D);
    right = new PIDController(P, I, D);

    // right.reset(0, 0);
    right.reset();
    right.setTolerance(500, 10);
    right.disableContinuousInput();

    // left.reset(0, 0);
    left.reset();
    left.setTolerance(500, 10);
    left.disableContinuousInput();

    SmartDashboard.putNumber("P", P);
    SmartDashboard.putNumber("I", I);
    SmartDashboard.putNumber("D", D);
  }

  @Override
  public void execute() {
    double leftSetpoint;
    double rightSetpoint;
    double xSpeed = Maths.joyclean(-joystick.getY(), 0.05);
    double zRotation = Maths.joyclean(-joystick.getX(), 0.05);
    double maxInput = Math.copySign(Math.max(Math.abs(xSpeed), Math.abs(zRotation)), xSpeed);
    /*if (xSpeed >= 0.0) {
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
    }*/

    double leftOut = left.calculate(Maths.deadband(leftEncoder.getRate(), 100), -joystick.getY()*1500);
    double rightOut = right.calculate(Maths.deadband(rightEncoder.getRate(), 100), -joystick.getY()*1500);
    // double forwardOut = left.calculate(Maths.deadband(rightEncoder.getRate(), 100), -joystick.getY()*2000);

    drive.tankDrive(-leftOut, -rightOut, false);
    // drive.arcadeDrive(-forwardOut, 0);

    SmartDashboard.putNumber("lE Rate", leftEncoder.getRate());
    SmartDashboard.putNumber("setpoint", -joystick.getY()*1500);
    SmartDashboard.putBoolean("lOn Target", left.atSetpoint());
    SmartDashboard.putNumber("lOutput", leftOut);
    SmartDashboard.putNumber("rE Rate", rightEncoder.getRate());
    SmartDashboard.putBoolean("rOn Target", right.atSetpoint());
    SmartDashboard.putNumber("rOutput", rightOut);
    double P = SmartDashboard.getNumber("P", 0.1);
    double I = SmartDashboard.getNumber("I", 0);
    double D = SmartDashboard.getNumber("D", 0);

    left.setPID(P, I, D);
    right.setPID(P, I, D);
  }

  @Override
  public void end(boolean interrupted) {
    leftEncoder.reset();
    rightEncoder.reset();
    
    drive.stopMotor();
    left.reset();
    right.reset();
    // left.reset(0, 0);
    // right.reset(0, 0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
