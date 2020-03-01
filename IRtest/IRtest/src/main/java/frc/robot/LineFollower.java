/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class LineFollower extends CommandBase {

  private enum State {
    Forward,
    SeekingLeft,
    SeekingRight
  }

  private DifferentialDrive drive;
  private DigitalInput IRSensor;

  private State state = State.Forward;
  private State lastTurn = State.SeekingLeft;

  public LineFollower(DifferentialDrive drive, DigitalInput ir) {
    this.drive = drive;
    this.IRSensor = ir;
  }

  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (state == State.Forward) {
      stateForward();
    } else {
      stateSeeking();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }


  private void stateForward() {
    if (onTheLine()) {
      driveForward();
    } else {
      if (lastTurn == State.SeekingLeft) {
        state = State.SeekingRight;
      } else {
        state = State.SeekingLeft;
      }
    }
  }

  private void stateSeeking() {
    if (!onTheLine()) {
      if (state == State.SeekingRight) {
        driveRight();
      } else if (state == State.SeekingLeft) {
        driveLeft();
      }
    
    } else {
      lastTurn = state;
      state = State.Forward;
    }

  }


  private boolean onTheLine() {
    return !IRSensor.get();
  }

  private void driveForward() {
    drive.tankDrive(0.3, 0.34, false);
  }

  private void driveLeft() {
    drive.tankDrive(0.25, -0.31, false);
  }

  private void driveRight() {
    drive.tankDrive(-0.25, 0.31, false);
  }
}
