package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveCommand extends Command {
    Joystick joystick;
    DifferentialDrive drive;

    public DriveCommand(
        Joystick joystick,
        DifferentialDrive drive
    ){
        this.joystick = joystick;
        this.drive = drive;
    }

    @Override
    protected void execute() {
       
        double x = joystick.getX();
        double y = joystick.getY();
        drive.arcadeDrive(y, x);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
    
}