package frc.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.math.Maths;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class SetTwoMotors extends CommandBase {
  private Joystick joystick;
  private SpeedController leftMotor;
  private SpeedController rightMotor;
  private double speed;
  private double leftSpeed;
  private double rightSpeed;

  public SetTwoMotors(
    Joystick joystick,
    SpeedController leftMotor,
    SpeedController rightMotor
  ) {
    this.joystick = joystick;
    this.leftMotor = leftMotor;
    this.rightMotor = rightMotor;
  }

  @Override
  public void initialize() {
    rightMotor.setInverted(true);
  }

  @Override
  public void execute() {
    speed = (-joystick.getZ() + 1.0) * 0.5;
    if(joystick.getRawAxis(4) < -0.2) {
      leftSpeed = speed + Maths.antiband(joystick.getRawAxis(4), 0.8) * speed;
      // leftSpeed = 0;
      rightSpeed = speed;
    }
    else if(joystick.getRawAxis(4) > 0.2) {
      leftSpeed = speed;
      rightSpeed = speed - Maths.antiband(joystick.getRawAxis(4), 0.8) * speed;
      // rightSpeed = 0;
    }
    else {
      leftSpeed = speed;
      rightSpeed = speed;
    }

    leftMotor.set(leftSpeed);// + 0.15 / (4 * speed));
    rightMotor.set(rightSpeed);

    SmartDashboard.putNumber("Speed", speed);
    SmartDashboard.putNumber("leftSpeed", rightSpeed);
    SmartDashboard.putNumber("rightSpeed", leftSpeed);

    

  }

  @Override
  public void end(boolean interrupted) {
    leftMotor.stopMotor();
    rightMotor.stopMotor();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
