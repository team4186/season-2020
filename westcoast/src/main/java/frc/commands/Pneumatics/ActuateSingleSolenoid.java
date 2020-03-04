package frc.commands.Pneumatics;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ActuateSingleSolenoid extends CommandBase {
  private Solenoid solenoid;

  public ActuateSingleSolenoid(Solenoid solenoid) {
    this.solenoid = solenoid;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    solenoid.set(true);
  }

  @Override
  public void end(boolean interrupted) {
    solenoid.set(false);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
