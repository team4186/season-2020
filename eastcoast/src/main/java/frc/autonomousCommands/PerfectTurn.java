/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.autonomousCommands;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.math.*;

public class PerfectTurn extends CommandBase {
  private AHRS ahrs;
  private DifferentialDrive drive;
  private PIDController turn;
  private double angle;
  private double wait;
  private boolean end;

  public PerfectTurn(
    DifferentialDrive drive,
    AHRS ahrs,
    double angle
  ) {
    this.ahrs = ahrs;
    this.drive = drive;
    this.angle = angle;
  }

  @Override
  public void initialize() {
    wait = 0;

    ahrs.reset();

    turn = new PIDController(0.6, 0.15, 0.1);

    turn.reset();
    turn.setTolerance(1);
    turn.disableContinuousInput();
  }

  @Override
  public void execute() {
    double angleturned = Maths.clamp(turn.calculate(-angle+5, Maths.deadband(ahrs.getYaw(), 2)), 0.3);

    drive.tankDrive(angleturned, -angleturned, false);

    if(turn.atSetpoint() == true){
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
    ahrs.reset();

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
}
