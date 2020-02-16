package frc.vision;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.math.*;

public class AlignToTarget extends CommandBase {
  private String name;
  private DifferentialDrive drive;
  private PIDController turn;
  private double wait;
  private double value;
  private boolean end;

  /**
   * Turns to a target found by GRIP.
   * @param drive The drivetrain.
   * @param name The name of the SmartDashboard published value.
   */
  public AlignToTarget(
    DifferentialDrive drive,
    String name
  ) {
    this.name = name;
    this.drive = drive;
  }

  @Override
  public void initialize() {
    wait = 0;

    double p = SmartDashboard.getNumber("Align To Target - P", 0.2);
    double i = SmartDashboard.getNumber("Align To Target - I", 0);
    double d = SmartDashboard.getNumber("Align To Target - D", 0.01);
    
    turn = new PIDController(p, i, d);

    turn.reset();
    turn.setTolerance(0);
    turn.disableContinuousInput();
  }

  @Override
  public void execute() {
    value = scaler(SmartDashboard.getNumber(name, 160));

    double turnpower = Maths.clamp(turn.calculate(value, 0), 0.4);

    drive.arcadeDrive(0, turnpower, false);

    if(turn.atSetpoint()){
      wait = wait + 1;
      if(wait >= 10){
        end = false;
      }
      else{
        end = false;
      }
    }
    else{
      end = false;
    }

    SmartDashboard.putNumber("output", turnpower);
    SmartDashboard.putNumber("Offset", scaler(value));
  }

  @Override
  public void end(boolean interrupted) {
    turn.reset();
    drive.stopMotor();
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

  private double scaler(double value){
    return ((value-160)/160);
  }
}
