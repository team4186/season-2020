package frc.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ActuateDoubleSolenoid extends CommandBase {
  private final DoubleSolenoid solenoid;
  private final Value direction;
  private final Value endDirection;
  
  public ActuateDoubleSolenoid(
    DoubleSolenoid solenoid,
    Value direction,
    Value endDirection
  ) {
      this.direction = direction;
			this.solenoid = solenoid;
			this.endDirection = endDirection;
  }

  @Override
  public void initialize() {

  }

  @Override
  public void execute() {
    solenoid.set(direction);
  }

  @Override
  public void end(boolean interrupted) {
    solenoid.set(endDirection);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
