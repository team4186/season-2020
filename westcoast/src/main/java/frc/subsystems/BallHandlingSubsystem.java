package frc.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robotMaps.RobotMap;

import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BallHandlingSubsystem extends SubsystemBase {
  private SpeedController intakeMotor;
  private SpeedController indexMotor;
  private SpeedController magMotor;
  private WPI_TalonSRX leftShooter;
  private SpeedController rightShooter;
  private DigitalInput headSensor;
  private DigitalInput abSensor;
  private DigitalInput tailSensor;
  public double indexCount = 0;

  public BallHandlingSubsystem(RobotMap map) {
    this.intakeMotor = map.getIntakeMotor();
    this.indexMotor = map.getIndexMotor();
    this.magMotor = map.getMagMotor(); //should be mag motor, but hardware is off
    this.leftShooter = map.getMainShooter();
    this.rightShooter = map.getSecondaryShooter();
    this.headSensor = map.getIndexSensor();
    this.abSensor = map.getMagSensor();
    this.tailSensor = map.getShooterSensor();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Sensor", getSensorSwitch());
  }

  public void runintakeMotor(double value){
    intakeMotor.set(value);
  }

  public void runindexMotor(double value){
    indexMotor.set(value);
  }

  public void runsyncIntdex(double value){
    intakeMotor.set(value);
    indexMotor.set(value-0.2);
  }

  public void runmagMotor(double value){
    magMotor.set(value);
  }

  public void runsyncMagdex(double value){
    indexMotor.set(value);
    magMotor.set(value+0.5);
  }

  public void runShooter(double value) {
    leftShooter.set(-value);
    rightShooter.set(value);
  }

  public void stopMotors() {
    intakeMotor.stopMotor();
    indexMotor.stopMotor();
    magMotor.stopMotor();
    leftShooter.stopMotor();
    rightShooter.stopMotor();
  }

  public boolean headSensorValue() {
    return !headSensor.get();
  }

  public boolean abdomenSensorValue() {
    return !abSensor.get();
  }

  public boolean tailSensorValue() {
    return !tailSensor.get();
  }

  public int getSensorSwitch() {
    int head = headSensorValue() ? 0x1 : 0;
    int abdomen = abdomenSensorValue() ? 0x2 : 0;
    int tail = tailSensorValue() ? 0x4 : 0;
    return head | abdomen | tail;
  }
}
