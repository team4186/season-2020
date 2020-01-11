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
  private double wait;
  private boolean end;

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
    wait = 0;
    encoder.reset();
    ahrs.reset();

    forward = new PIDController(0.5, 0.1, 0);
    hold = new PIDController(0.5, 0, 0);
    
    forward.reset();
    forward.setTolerance(5);
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

    if(forward.atSetpoint() == true){
      wait = wait + 1;
      if(wait >= 10){
        end = true;
      }
      else{
        end = false;
      }
    }
    else{
      end = false;
    }
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
    if(end){
      return true;
    }
    else{
      return false;
    }
  }
}
