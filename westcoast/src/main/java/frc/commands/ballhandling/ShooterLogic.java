package frc.commands.ballhandling;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.maps.RobotMap;

public class ShooterLogic extends CommandBase {
  private SpeedController right;
  private TalonSRX left;

  public ShooterLogic(
    RobotMap map
  ) {
    this.left = map.getMainShooter();
    this.right = map.getSecondaryShooter();
  }

  @Override
  public void initialize() {
    left.configNominalOutputForward(0);
    left.configNominalOutputReverse(0);
    left.configPeakOutputForward(1);
    left.configPeakOutputReverse(-1);
    
    left.config_kP(0, 0.1);
    left.config_kI(0, 0.1);
    left.config_kD(0, 0.1);
  }

  @Override
  public void execute() {
    left.set(ControlMode.Current, 0.78 * 7);
    right.set(left.getMotorOutputPercent()/100);
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
