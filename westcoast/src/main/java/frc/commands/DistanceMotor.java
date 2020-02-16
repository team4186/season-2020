package frc.commands;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DistanceMotor extends CommandBase {
  private final SpeedController motor;
  private int i;
  private boolean end;
  private final 
  double time;
  private double speed;

  public DistanceMotor(
    SpeedController motor,
    double speed,
    double time
  ) {
    this.motor = motor;
    this.speed = speed;
    this.time = time*50;
  }

  @Override
  public void initialize() {
    i = 0;
    end = false;
  }

  @Override
  public void execute() {
    if(i <= time){
      motor.set(speed);
      i=i+1;
    }
    else{
      end = true;
    }
    System.out.println(i);
  }

  @Override
  public void end(boolean interrupted) {
    motor.stopMotor();
    i = 0;
   }

  @Override
  public boolean isFinished() {
    return end;
  }
}
