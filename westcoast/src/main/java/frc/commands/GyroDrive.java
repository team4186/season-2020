package frc.commands;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpiutil.math.MathUtil;


public class GyroDrive extends CommandBase {
  private DifferentialDrive drive;
  private PIDController pid;
  private Joystick joy;
  private AHRS ahrs;

  public GyroDrive(
    DifferentialDrive drive,
    Joystick joystick,
    AHRS ahrs
  ) {
    this.ahrs = ahrs;
    this.drive = drive;
    this.joy = joystick;
  }

  @Override
  public void initialize() {
    pid.reset();
    pid.setTolerance(1);
    pid.disableContinuousInput();

    double P = SmartDashboard.getNumber("P", 0.07);
    double I = SmartDashboard.getNumber("I", 0.025);
    double D = SmartDashboard.getNumber("D", 0.15);
    pid = new PIDController(P, I, D, 0.1);
  }

  @Override
  public void execute() {
    double input = MathUtil.clamp(ahrs.getRate(),-4, 4);
    double pidraw = pid.calculate(input, attenuate(joy.getTwist())*4);
    double output = MathUtil.clamp(pidraw,-0.6, 0.6);
    drive.arcadeDrive(attenuate(joy.getY()), output, false);

    SmartDashboard.putBoolean("On Target?", pid.atSetpoint());
    SmartDashboard.putNumber("Error", pid.getPositionError());
    SmartDashboard.putNumber("Value", pidraw);
    SmartDashboard.putNumber("P", pid.getP());
    SmartDashboard.putNumber("I", pid.getI());
    SmartDashboard.putNumber("D", pid.getD());
  }

  @Override
  public void end(boolean interrupted) {
    drive.stopMotor();
    pid.reset();
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  private double attenuate(double value){
    double calculated = Math.signum(value)*Math.pow(Math.abs(value), 1.3);
    return calculated;
  }
}
