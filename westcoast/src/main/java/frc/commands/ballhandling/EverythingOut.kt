package frc.commands.ballhandling;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.subsystems.BallHandlingSubsystem;

public class EverythingOut extends CommandBase {
    private final BallHandlingSubsystem ball;

    public EverythingOut(
            BallHandlingSubsystem ballHandler
    ) {
        this.ball = ballHandler;
        addRequirements(ballHandler);
    }

    @Override
    public void initialize() {
        ball.resetIndexCount();
    }

    @Override
    public void execute() {
        ball.runintakeMotor(-0.4);
        ball.runindexMotor(-0.4);
        ball.runmagMotor(-0.4);
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
