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
    pid = new PIDController(0.5, 0.15, 0);

    ahrs.reset();
    pid.reset();
    pid.setTolerance(0.5);
    pid.disableContinuousInput();
  }

  @Override
  public void execute() {
    double input = MathUtil.clamp(deadband(ahrs.getRate(), 0.3),-6, 6);
    double pidraw = pid.calculate(input, attenuate(deadband(joy.getX(), 0.05))*4.6);
    double output = MathUtil.clamp(deadband(-pidraw, 0.3),-0.8, 0.8);
    double deadband = deadband(output, 0.05);
    drive.arcadeDrive(attenuate(joy.getY()), deadband, false);

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
  
  private double deadband(double value, double deadzone){
    if(value<deadzone&&value>deadzone*(-1)){
      return 0;
    }
    else{
      return value;
    }
  }
}
