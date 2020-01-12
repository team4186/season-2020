package frc.autonomousCommands;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.math.*;

public class ProfiledLeaveLine extends CommandBase {
  private DifferentialDrive drive;
  private ProfiledPIDController forward;
  private PIDController hold;
  private Encoder encoder;
  private AHRS ahrs;
  private double dist;
  private double wait;
  private boolean end;
  private Constraints constraints;

  public ProfiledLeaveLine(
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

    constraints = new Constraints(150, 50);
    forward = new ProfiledPIDController(0.85, 0, 0.001, constraints);
    hold = new PIDController(0.1, 0, 0.2);
    
    forward.reset(0, 0);
    forward.setTolerance(1, 5);
    forward.disableContinuousInput();

    hold.reset();
    hold.setTolerance(1);
    hold.disableContinuousInput();
  }

  @Override
  public void execute() {
    double distance = dist*83;
    double forwardraw = forward.calculate(distance, distance);
    double headinglock = Maths.clamp(hold.calculate(Maths.deadband(ahrs.getYaw(), 3), 0), 0.4);

    drive.arcadeDrive(forwardraw, headinglock);

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
    forward.reset(0, 0);
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
