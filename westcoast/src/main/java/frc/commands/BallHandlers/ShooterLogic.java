package frc.commands.BallHandlers;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShooterLogic extends CommandBase {
  private boolean end;

  public ShooterLogic() {
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
