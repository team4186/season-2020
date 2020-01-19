package frc.autonomousCommands;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.math.*;

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

    ahrs.reset();
    encoder.reset();

    forward = new PIDController(0.85, 0, 0.001);
    hold = new PIDController(0.1, 0, 0.2);
    
    forward.reset();
    forward.setTolerance(1);
    forward.disableContinuousInput();

    hold.reset();
    hold.setTolerance(1);
    hold.disableContinuousInput();
  }

  @Override
  public void execute() {
    double distance = dist*83; //i need to find multiplier for how many ticks are per inch/foot.
    double forwardraw = forward.calculate(distance, encoder.getDistance());
    double distancecalc = Maths.clamp(forwardraw, 0.5);
    double headinglock = Maths.clamp(hold.calculate(0, Maths.deadband(ahrs.getYaw(), 3)), 0.4);

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
      //wait = 0;
    }
  }

  @Override
  public void end(boolean interrupted) {
    ahrs.reset();
    encoder.reset();

    drive.stopMotor();
    forward.reset();
    hold.reset();
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
