package frc.autonomousCommands;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

    forward = new PIDController(0.5, 0.1, 0.1);
    hold = new PIDController(0.1, 0.15, 0.2);
    
    forward.reset();
    forward.setTolerance(5);
    forward.disableContinuousInput();

    hold.reset();
    hold.setTolerance(1);
    hold.disableContinuousInput();
  }

  @Override
  public void execute() {
    double distance = dist*83; //i need to find multiplier for how many ticks are per inch/foot.
    double forwardraw = forward.calculate(distance, encoder.getDistance());
    double distancecalc = MathUtil.clamp(forwardraw, -0.5, 0.5);
    double yaw = ahrs.getYaw();
    double headinglock = MathUtil.clamp(hold.calculate(0, deadband(yaw, 3)), -0.4, 0.4);

    drive.arcadeDrive(distancecalc, headinglock);

  SmartDashboard.putNumber("YAWWW", ahrs.getYaw());

    if(forward.atSetpoint() == true){
      wait = wait + 1;
      if(wait >= 5){
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

  private double deadband(double value, double deadzone){
    if(value<deadzone&&value>deadzone*(-1)){
      return 0;
    }
    else{
      return value;
    }
  }
}
