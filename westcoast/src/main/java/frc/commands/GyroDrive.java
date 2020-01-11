package frc.commands;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
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
    double input = MathUtil.clamp(ahrs.getRate(),-6, 6);
    double output = MathUtil.clamp(pid.calculate(input,attenuate(joy.getTwist()*4.6)), -0.8, 0.8);
    drive.arcadeDrive(attenuate(joy.getY()), output, false);
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
    double deadzone = 0.05;
    if(value<deadzone&&value>deadzone*(-1)){
      return 0;
    }
    else{
      return calculated;
    }
  }
}
