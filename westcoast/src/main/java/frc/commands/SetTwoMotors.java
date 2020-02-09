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
          Thread.currentThread().interrupt();
          System.out.println("Wot the fok did ye just say 2 me m8? i dropped out of newcastle primary skool im the sickest bloke ull ever meet & ive nicked ova 300 chocolate globbernaughts frum tha corner shop. im trained in street fitin' & im the strongest foker in tha entire newcastle gym. yer nothin to me but a cheeky lil bellend w/ a fit mum & fakebling. ill waste u and smash a fokin bottle oer yer head bruv, i swer 2 christ. ya think u can fokin run ya gabber at me whilst sittin on yer arse behind a lil screen? think again wanka. im callin me homeboys rite now preparin for a proper scrap. A roomble thatll make ur nan sore jus hearin about it. yer a waste bruv. me crew be all over tha place & ill beat ya to a proper fokin pulp with me fists wanka. if i aint satisfied w/ that ill borrow me m8s cricket paddle & see if that gets u the fok out o' newcastle ya daft kunt. if ye had seen this bloody fokin mess commin ye might a' kept ya gabber from runnin. but it seems yea stupid lil twat, innit? ima shite fury & ull drown in it m8. ur ina proper mess knob.");
        }
      }
    }
  };

  @Override
  public void initialize() {
    isRunning = true;
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
    isRunning = false;
    t.interrupt();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}