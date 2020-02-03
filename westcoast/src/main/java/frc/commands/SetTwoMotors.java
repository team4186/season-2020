package frc.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class SetTwoMotors extends CommandBase {
  private Joystick joystick;
  private SpeedController leftMotor;
  private SpeedController rightMotor;

  public SetTwoMotors(
    Joystick joystick,
    SpeedController leftMotor,
    SpeedController rightMotor
  ) {
    this.joystick = joystick;
    this.leftMotor = leftMotor;
    this.rightMotor = rightMotor;
  }


  long activeCycleMs = 0;
  int activeCycleNs = 0;
  long dutyCycleTimeMs = 2; 
  int dutyCycleTimeNs = 2 * 1_000_000;
  boolean isRunning = true;
  Thread t = new Thread() {
    @Override
    public void run() {
      while(isRunning) {
        try {
          leftMotor.set(1);
          rightMotor.set(1);
          Thread.sleep(0, activeCycleNs);
          leftMotor.set(0);
          rightMotor.set(0);
          Thread.sleep(0, dutyCycleTimeNs - activeCycleNs);
        }
        catch(InterruptedException e) { 
          // empty
        }
      }
    }
  };

  @Override
  public void initialize() {
    rightMotor.setInverted(true);
    t.start();
    
  }

  @Override
  public void execute() {
    double input = dutyCycleTimeNs * ((-joystick.getZ() + 1.0) * 0.5);
    activeCycleNs = (int)input;
 
    SmartDashboard.putNumber("Speed", activeCycleMs);

    

  }

  @Override
  public void end(boolean interrupted) {
    leftMotor.stopMotor();
    rightMotor.stopMotor(); 
    t.interrupt();
    isRunning = false;
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
