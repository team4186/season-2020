package frc.vision;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robotMaps.*;
import frc.math.*;

public class AlignToTarget extends CommandBase {
  private DifferentialDrive drive;
  private PIDController turn;
  private double wait;
  private double value;
  private VisionRunner vision;
  private RobotMap map;
  
  /**
   * Turns to a target found by GRIP.
   * @param drive The drivetrain.
   * @param vision The vision runner.
   */
  public AlignToTarget(
    RobotMap map,
    DifferentialDrive drive,
    VisionRunner vision
  ) {
    this.map = map;
    this.drive = drive;
    this.vision = vision;
  }

  @Override
  public void initialize() {
    wait = 0;
    
    turn = map.makeAlignPIDs();
  }

  @Override
  public void execute() {
    value = vision.getAlignX();
    
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
}
