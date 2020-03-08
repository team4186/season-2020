package frc.commands.ballhandling;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShooterLogic extends CommandBase {
  private WPI_TalonSRX right;
  private WPI_TalonSRX left;

  public ShooterLogic(
    WPI_TalonSRX right,
    WPI_TalonSRX left
  ) {
    this.left = right;
    this.right = left;
  }

  @Override
  public void initialize() {
    left.configNominalOutputForward(0);
    left.configNominalOutputReverse(0);
    left.configPeakOutputForward(1);
    left.configPeakOutputReverse(-1);
    
    left.config_kP(0, 0.1);
    left.config_kI(0, 0.2);
    left.config_kD(0, 0);

    right.configNominalOutputForward(0);
    right.configNominalOutputReverse(0);
    right.configPeakOutputForward(1);
    right.configPeakOutputReverse(-1);
    
    right.config_kP(0, 0.1);
    right.config_kI(0, 0.2);
    right.config_kD(0, 0);
  }

  @Override
  public void execute() {
    left.set(ControlMode.PercentOutput, 60);
    // right.set(ControlMode.Current, -7);
    right.set(ControlMode.PercentOutput, -60);
    SmartDashboard.putNumber("aaa", right.getSupplyCurrent());
  }

  @Override
  public void end(boolean interrupted) {
    right.stopMotor();
    left.stopMotor();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
