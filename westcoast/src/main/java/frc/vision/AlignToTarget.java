package frc.vision;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.math.*;

public class AlignToTarget extends CommandBase {
  private DifferentialDrive drive;
  private PIDController turn;
  private double wait;
  private double value;
  private boolean lime;
  
  /**
   * Turns to a target found by GRIP.
   * @param drive The drivetrain.
   * @param limelight If the command uses the limelight's values or if it uses onboard cameras
   */
  public AlignToTarget(
    DifferentialDrive drive,
    boolean limelight
  ) {
    this.drive = drive;
    this.lime = limelight;
  }

  /**
   * Turns to a target found by the limelight
   * @param drive The drivetrain.
   */
  public AlignToTarget(
    DifferentialDrive drive
  ){
    this(drive, true);
  }

  @Override
  public void initialize() {
    wait = 0;
    
    if(lime) turn = new PIDController(0.007, 0.005, 0); //Tune for the limelight, because it's -29.8 to 29.8 instead of -1 to 1 (these are just the previous values but divided by 29.8 :))
    else turn = new PIDController(0.2, 0.15, 0);

    turn.reset();
    turn.setTolerance(0.1);
    turn.disableContinuousInput();
  }

  @Override
  public void execute() {
    if(lime) {
      if(NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0) == 1){
        value = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
      }
      else value = 0;
    }
    else value = scaler(SmartDashboard.getNumber("CenterX", 160));

    double turnpower = Maths.clamp(turn.calculate(value, 0), 0.4);

    drive.arcadeDrive(0, turnpower, false);

    if(turn.atSetpoint()){
      wait = wait + 1;
    }
    else{
      wait = 0;
    }
  }

  @Override
  public void end(boolean interrupted) {
    turn.reset();
    drive.stopMotor();

    System.out.println("Ready, Aim, Fire!");
  }

  @Override
  public boolean isFinished() {
    return wait >= 10;
  }

  private double scaler(double value){
    return ((value-160)/160);
  }
}
