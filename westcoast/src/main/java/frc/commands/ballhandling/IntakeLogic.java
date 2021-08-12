package frc.commands.ballhandling;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.subsystems.BallHandlingSubsystem;

public class IntakeLogic extends CommandBase {
    private final BallHandlingSubsystem ballHandler;
    private boolean end;

    public IntakeLogic(BallHandlingSubsystem ballHandler) {
        this.ballHandler = ballHandler;
        addRequirements(ballHandler);
    }

    @Override
    public void initialize() {
        end = false;
    }

    @Override
    public void execute() {
        switch (ballHandler.getSensorSwitch()) {
            case 0x0:
                ballHandler.runsyncIntdex(0.4); //No sensors see anything.
                break;
            case 0x1:
                end = true; //Intake sensor sees something.
                break;
            case 0x2:
                ballHandler.runsyncIntdex(0.4); //Index sensor sees something.
                break;
            case 0x3:
                end = true; //Both Index sensor and Intake sensor see something (all balls after first)
                break;
            case 0x4:
                end = true; //End sensor sees something (shouldn't happen).
                break;
            case 0x5:
                ballHandler.runsyncIntdex(0.4); //Intake sensor and End sensor see something (shouldn't happen).
                break;
            case 0x6:
                ballHandler.runsyncIntdex(0.4); //End sensor and Index sensor sees something (happens when 4 balls are in the system).
                break;
            case 0x7:
                end = true; //All sensors are saturated (happens with 5 balls).
                break;
        }
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Intake Complete");
        ballHandler.stopMotors();
        ballHandler.incrementIndexCount();
    }

    @Override
    public boolean isFinished() {
        return end;
    }
}
