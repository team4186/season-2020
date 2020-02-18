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
    
    turn = new PIDController(0.2, 0.15, 0);

    turn.reset();
    turn.setTolerance(0.1);
    turn.disableContinuousInput();
  }

  @Override
  public void execute() {
    value = scaler(SmartDashboard.getNumber(name, 160));

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
