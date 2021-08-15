package frc.commands.ballhandling;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.subsystems.BallHandlingSubsystem;

public class IntakeAndIndex extends SequentialCommandGroup {
    private final BallHandlingSubsystem ball;

    public IntakeAndIndex(BallHandlingSubsystem ball) {
        addCommands(
                new IntakeLogic(ball),
                new WaitCommand(0.2),
                new IndexLogic(ball)
        );
        this.ball = ball;
        addRequirements(ball);
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Ball Implemented!");
        ball.stopMotors();
    }
}
