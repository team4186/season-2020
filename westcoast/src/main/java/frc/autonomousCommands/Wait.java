package frc.autonomousCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class Wait extends CommandBase {
  private double thyme;
  private double time;
  private boolean end;

  public Wait(
    double time
  ) {
    this.time = time;
  }

  @Override
  public void initialize() {
    thyme = 0;
  }

  @Override
  public void execute() {
    thyme = thyme + 1;
    if(thyme >= time*50){
      end = true;
    }
    else{
      end = false;
    }
  }

  @Override
  public void end(boolean interrupted) {
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
