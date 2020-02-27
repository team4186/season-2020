package frc.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BallHandlingSubsystem extends SubsystemBase {
  private SpeedController intakeMotor = new WPI_VictorSPX(7);
  private SpeedController indexMotor = new WPI_TalonSRX(8);
  private SpeedController magMotor = new WPI_TalonSRX(9);
  private SpeedController leftShooter = new WPI_VictorSPX(10);
  private SpeedController rightShooter = new WPI_VictorSPX(11);
  private SpeedControllerGroup shooter = new SpeedControllerGroup(leftShooter, rightShooter);
  private DigitalInput intakeSensor = new DigitalInput(4);
  private DigitalInput indexSensor = new DigitalInput(5);
  private DigitalInput shooterSensor = new DigitalInput(6);
  public double indexCount = 0;

  public BallHandlingSubsystem() {
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("IndexCount", indexCount);
  }

  public void runintakeMotor(double value){
    intakeMotor.set(value);
  }

  public void runindexMotor(double value){
    indexMotor.set(value);
  }

  public void runmagMotor(double value){
    magMotor.set(value);
  }

  public void runShooter(double value) {
    shooter.set(value);
  }

  public boolean intakeSensorValue() {
    return intakeSensor.get();
  }

  public boolean indexSensorValue() {
    return indexSensor.get();
  }

  public boolean shooterSensorValue() {
    return shooterSensor.get();
  }

  public void increment() {
    indexCount = indexCount + 1;
  }

  public void decrement() {
    indexCount = indexCount - 1;
  }
}
