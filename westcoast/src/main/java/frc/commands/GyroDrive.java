package frc.commands;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.math.Maths;


public class GyroDrive extends CommandBase {
  private DifferentialDrive drive;
  private PIDController pid;
  private Joystick joy;
  private AHRS ahrs;

/**
 * Driving more accurately.
 * @param drive The drivetrain.
 * @param joystick The joystick.
 * @param ahrs The navX.
 */

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
    double input = Maths.pidclean(ahrs.getRate(), 6, 0.3);
    double pidraw = pid.calculate(input, Maths.joyclean(joy.getX(), 0.05)*4.6);
    double output = Maths.pidclean(-pidraw, 0.8, 0.05);
    drive.arcadeDrive(Maths.attenuate(joy.getY()), output, false);
  }

  @Override
  public void end(boolean interrupted) {
    drive.stopMotor();
    ahrs.reset();
    pid.reset();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
