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

    forward.reset();
    forward.setTolerance(10);
    forward.disableContinuousInput();

    hold.reset();
    hold.setTolerance(0.5);
    hold.disableContinuousInput();

    forward = new PIDController(0.07, 0.025, 0.15, 0.1);
    hold = new PIDController(0.07, 0, 0.15, 0.1);
  }

  @Override
  public void execute() {
    double distance = dist*5; //i need to find multiplier for how many ticks are per inch/foot.
    double forwardraw = forward.calculate(distance, encoder.getDistance());
    double distancecalc = MathUtil.clamp(forwardraw, -0.6, 0.6);
    double headinglock = hold.calculate(0, ahrs.getRate());

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
    if(forward.atSetpoint() == true){
      return true;
    }
    else{
      return false;
    }
  }
}
