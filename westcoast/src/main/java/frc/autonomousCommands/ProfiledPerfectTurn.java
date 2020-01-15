/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.autonomousCommands;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.math.*;

public class ProfiledPerfectTurn extends CommandBase {
  private AHRS ahrs;
  private DifferentialDrive drive;
  private ProfiledPIDController turn;
  private double angle;
  private double wait;
  private boolean end;
  private Constraints constraints;

  public ProfiledPerfectTurn(
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

    constraints = new Constraints(3, 1);
    turn = new ProfiledPIDController(0.6, 0.15, 0.1, constraints);

    turn.reset(0, 0);
    turn.setTolerance(1);
    turn.disableContinuousInput();
  }

  @Override
  public void execute() {
    double angleturned = turn.calculate(Maths.deadband(ahrs.getYaw(), 2), angle);

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

    turn.reset(0, 0);
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
