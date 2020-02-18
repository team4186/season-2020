package frc.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class Shoot extends CommandBase {

  public Shoot() {
  }

  @Override
  public void initialize() {    
    System.out.println("shooting");
  }

  @Override
  public void execute() {
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
