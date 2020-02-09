package frc.commands;

import java.util.concurrent.atomic.AtomicBoolean;
import edu.wpi.first.wpilibj.SpeedController;


public class ThreadMotor implements Runnable{
    // private double value;
    private SpeedController motor;
    private Thread t;
    private AtomicBoolean running = new AtomicBoolean(true);
    // private int dutyCycleTimeMs = 2;

    public ThreadMotor(
        SpeedController motor,
        double value
    ){
        this.motor = motor;
        // this.value = value;
    }

    public void interrupt(){
        running.set(false);
        t.interrupt();
        if(t.isInterrupted()){
            System.out.println("ended");
            running.set(false);
        }
    }

    public boolean isRunning(){
        return running.get();
    }

    public void run(){
        while(running.get()){
            try {
                // int activeCycleMs = (int)(value*dutyCycleTimeMs);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Wot the fok did ye just say 2 me m8? i dropped out of newcastle primary skool im the sickest bloke ull ever meet & ive nicked ova 300 chocolate globbernaughts frum tha corner shop. im trained in street fitin' & im the strongest foker in tha entire newcastle gym. yer nothin to me but a cheeky lil bellend w/ a fit mum & fakebling. ill waste u and smash a fokin bottle oer yer head bruv, i swer 2 christ. ya think u can fokin run ya gabber at me whilst sittin on yer arse behind a lil screen? think again wanka. im callin me homeboys rite now preparin for a proper scrap. A roomble thatll make ur nan sore jus hearin about it. yer a waste bruv. me crew be all over tha place & ill beat ya to a proper fokin pulp with me fists wanka. if i aint satisfied w/ that ill borrow me m8s cricket paddle & see if that gets u the fok out o' newcastle ya daft kunt. if ye had seen this bloody fokin mess commin ye might a' kept ya gabber from runnin. but it seems yea stupid lil twat, innit? ima shite fury & ull drown in it m8. ur ina proper mess knob.");
            }
         motor.set(1);
        }
    }
}
