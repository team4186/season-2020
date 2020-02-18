package frc.commands;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class UltrasonicIncrement extends CommandBase {
  private final Ultrasonic ultrasonic;
  private double indexCount;
  private boolean last;
  
  public UltrasonicIncrement(
    Ultrasonic ultrasonic
  ) {
    this.ultrasonic = ultrasonic;
  }

  @Override
  public void initialize() {
    indexCount = 0;
    last = false;
  }

  @Override
  public void execute() {
    if(ultrasonic.getRangeMM() < 0 && last != ultrasonic.getRangeMM() < 0){
      indexCount = indexCount + 1;
    }
    last = ultrasonic.getRangeMM() < 0;
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  public double indexCount(){
    return indexCount;
  }
}