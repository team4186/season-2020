package frc.commands.BallHandlers;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.subsystems.BallHandlingSubsystem;

public class IndexLogic extends CommandBase {
  private final BallHandlingSubsystem ballHandler;
  private boolean end;

  public IndexLogic(
    BallHandlingSubsystem ballHandler
  ) {
    this.ballHandler=ballHandler;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    switch (ballHandler.getSensorSwitch())  {
      case 0x0: ballHandler.runsyncMagdex(0.5); //No sensors see anything.
        break;
      case 0x1: ballHandler.runsyncMagdex(0.5); //Intake sensor sees something.
        break;
      case 0x2: end = true; //Index sensor sees something.
        break;
      case 0x3: ballHandler.runsyncMagdex(0.5); //Both Index sensor and Intake sensor see something (all balls after first)
        break;
      case 0x4: end = true; //End sensor sees something (shouldn't happen).
        break;
      case 0x5: end = true; //Intake sensor and End sensor see something (shouldn't happen).
        break;
      case 0x6: end = true; //End sensor and Index sensor sees something (happens when 4 balls are in the system).
        break;
      case 0x7: end = true; //All sensors are saturated (happens with 5 balls).
        break;
    }
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return end;
  }
}
