package frc.commands.motors;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.SpeedController;

public class SetTwoMotors extends CommandBase {
  private SpeedController mainMotor;
  private SpeedController reversibleMotor;
  private double speed;
  private boolean inverted;

  public SetTwoMotors(
    SpeedController mainMotor,
    SpeedController reversibleMotor,
    double speed,
    boolean isInverted
  ) {
    this.mainMotor = mainMotor;
    this.reversibleMotor = reversibleMotor;
    this.speed = speed;
    this.inverted = isInverted;
  }

  public SetTwoMotors(
    SpeedController mainMotor,
    SpeedController secondMotor,
    double speed
  ){
    this(mainMotor, secondMotor, speed, false);
  }

  @Override
  public void initialize() {
    reversibleMotor.setInverted(inverted);
  }

  @Override
  public void execute() {  
    mainMotor.set(speed);
    reversibleMotor.set(speed);
  }

  @Override
  public void end(boolean interrupted) {
    mainMotor.stopMotor();
    reversibleMotor.stopMotor();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
