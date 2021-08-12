package frc.commands.ballhandling;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.subsystems.BallHandlingSubsystem;

public class IndexLogic extends CommandBase {
    private final BallHandlingSubsystem ballHandler;
    private boolean end;
    private double boost;

    public IndexLogic(
            BallHandlingSubsystem ballHandler
    ) {
        this.ballHandler = ballHandler;
        addRequirements(ballHandler);
    }

    @Override
    public void initialize() {
        end = false;
        boost = 0.019 * ballHandler.getIndexCount();
    }

    @Override
    public void execute() {
        switch (ballHandler.getSensorSwitch()) {
            case 0x0:
                ballHandler.runindexMotor(0.3);
                ballHandler.runmagMotor(0.3 + boost); //No sensors see anything.
                break;
            case 0x1:
                ballHandler.runindexMotor(0.3);
                ballHandler.runmagMotor(0.43); //Intake sensor sees something.
                break;
            case 0x2:
                end = true; //Index sensor sees something.
                break;
            case 0x3:
                ballHandler.runindexMotor(0.27); //Both Index sensor and Intake sensor see something (all balls after first)
                ballHandler.runmagMotor(0.3 + boost); //Boosts magazines speed so as to avoid magazine still seeing ball while index get's cleared (could also be fixed by moving sensor positions)
                break;
            case 0x4:
                end = true; //End sensor sees something (shouldn't happen).
                break;
            case 0x5:
                end = true; //Intake sensor and End sensor see something (shouldn't happen).
                break;
            case 0x6:
                end = true; //End sensor and Index sensor sees something (happens when 4 balls are in the system).
                break;
            case 0x7:
                end = true; //All sensors are saturated (happens with 5 balls).
                break;
        }
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Index Complete");
        ballHandler.stopMotors();
    }

    @Override
    public boolean isFinished() {
        return end;
    }
}
