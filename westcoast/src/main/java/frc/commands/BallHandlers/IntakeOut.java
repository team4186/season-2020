/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.commands.BallHandlers;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.subsystems.BallHandlingSubsystem;

public class IntakeOut extends CommandBase {
  private final BallHandlingSubsystem ball;

  public IntakeOut(
    BallHandlingSubsystem ballHandler
  ) {
    this.ball = ballHandler;
    addRequirements(ballHandler);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    ball.runintakeMotor(-0.4);
  }

  @Override
  public void end(boolean interrupted) {
    ball.stopMotors();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
