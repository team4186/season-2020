package frc.commands.auto;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.math.Maths;
import frc.robot.maps.RobotMap;

public class PerfectTurn extends CommandBase {
  private DifferentialDrive drive;
  private ProfiledPIDController turnRight;
  private ProfiledPIDController turnLeft;
  private double angle;
  private Encoder leftEncoder;
  private Encoder rightEncoder;
  private double wait;
  private RobotMap map;

  /**
   * Turns to a certain angle defined by "angle"
   * @param drive The drivetrain.
   * @param leftEncoder The drivetrain leftEncoder.
   * @param rightEncoder The drivetrain rightEncoder.
   * @param angle The angle it turns to.
   */

  public PerfectTurn(
    RobotMap map,
    DifferentialDrive drive,
    Encoder leftEncoder,
    Encoder rightEncoder,
    double angle
  ) {
    this.map = map;
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

    turnLeft = map.makePTPIDs();
    turnRight = map.makePTPIDs();
  }

  @Override
  public void execute() {
    double setpoint = angle*map.getPTMult();
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
