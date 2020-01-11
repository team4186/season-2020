package frc.autonomousCommands;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpiutil.math.MathUtil;

public class LeaveLine extends CommandBase {
  private DifferentialDrive drive;
  private PIDController forward;
  private PIDController hold;
  private Encoder encoder;
  private AHRS ahrs;
  private double dist;

  public LeaveLine(
    DifferentialDrive drive,
    Encoder encoder,
    AHRS ahrs,
    double distance
  ) {
    this.encoder = encoder;
    this.ahrs = ahrs;
    this.drive = drive;
    this.dist = distance;
  }

  @Override
  public void initialize() {
    encoder.reset();
    ahrs.reset();

    forward = new PIDController(0.5, 0.1, 0);
    hold = new PIDController(0.5, 0, 0);
    
    forward.reset();
    forward.setTolerance(1);
    forward.disableContinuousInput();

    hold.reset();
    hold.setTolerance(0.1);
    hold.disableContinuousInput();
  }

  @Override
  public void execute() {
    double distance = dist*50; //i need to find multiplier for how many ticks are per inch/foot.
    double forwardraw = forward.calculate(distance, encoder.getDistance());
    double distancecalc = MathUtil.clamp(forwardraw, -0.5, 0.5);
    double headinglock = MathUtil.clamp(-hold.calculate(0, ahrs.getRate()), -0.8, 0.8);

    drive.arcadeDrive(distancecalc, headinglock);
  }

  @Override
  public void end(boolean interrupted) {
    drive.stopMotor();
    forward.reset();
    hold.reset();
    encoder.reset();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
