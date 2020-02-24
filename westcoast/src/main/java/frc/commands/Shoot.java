package frc.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class Shoot extends CommandBase {
  private boolean end;

  public Shoot() {
  }

  @Override
  public void initialize() {    
    System.out.println("shooting");
  }

  @Override
  public void execute() {
    end = true;
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return end;
  }
}
