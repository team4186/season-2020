package frc.motorFactory;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
public class StopAllMotors {
    private SpeedControllerGroup motors;
    public StopAllMotors(
        SpeedController motor1,
        SpeedController motor2,
        SpeedController motor3
    ){
        motors = new SpeedControllerGroup(motor1, motor2, motor3);
    }
    public StopAllMotors(
        SpeedController motor1,
        SpeedController motor2
    ){
        this(motor1, motor2, motor1);
    }
    public void stop(){
        motors.stopMotor();
    }
}