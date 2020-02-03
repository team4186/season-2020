package frc.vision;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.math.*;

public class AlignToTarget extends CommandBase {
  private String name;
  private DifferentialDrive drive;
  private ProfiledPIDController turnRight;
  private ProfiledPIDController turnLeft;
  private Encoder leftEncoder;
  private Encoder rightEncoder;
  private double wait;
  private double value;
  private double goal;
  private boolean end;
  private boolean cache;
  private Constraints constraints;

  /**
   * Turns to a target found by GRIP.
   * @param drive The drivetrain.
   * @param leftEncoder The drivetrain leftEncoder.
   * @param rightEncoder The drivetrain rightEncoder.
   * @param name The name of the SmartDashboard published value
   */
  public AlignToTarget(
    DifferentialDrive drive,
    Encoder leftEncoder,
    Encoder rightEncoder,
    String name
  ) {
    this.name = name;
    this.drive = drive;
    this.leftEncoder = leftEncoder;
    this.rightEncoder = rightEncoder;
  }

  @Override
  public void initialize() {
    wait = 0;
    cache = false;

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
    value = SmartDashboard.getNumber(name, 160);

    if(cache){
    }
    else{
      goal = scaler(value);
      cache = true;
    }

    double rightside = Maths.clamp(turnRight.calculate(-rightEncoder.getDistance(), goal),0.4);
    double leftside = Maths.clamp(turnLeft.calculate(-leftEncoder.getDistance(), -goal),0.4);

    drive.tankDrive(leftside, rightside, false);

    if(turnRight.atGoal() && turnLeft.atGoal()){
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
    turnLeft.reset(0, 0);
    turnRight.reset(0, 0);
    leftEncoder.reset();
    rightEncoder.reset();
    System.out.println("Aligned");
    cache = false;
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
    return ((value-160)/160)*23.5*1.95;
  }
}
