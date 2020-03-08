package frc.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.maps.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BallHandlingSubsystem extends SubsystemBase {
  private SpeedController intakeMotor;
  private SpeedController indexMotor;
  private SpeedController magMotor;
  private WPI_TalonSRX leftShooter;
  private WPI_TalonSRX rightShooter;
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
    magMotor.set(value);
  }

  public void runShooter(double value) {
    leftShooter.set(-value);
    rightShooter.set(value);
  }

  public void shooterTune(){
    leftShooter.configNominalOutputForward(0);
    leftShooter.configNominalOutputReverse(0);
    leftShooter.configPeakOutputForward(1);
    leftShooter.configPeakOutputReverse(-1);
    leftShooter.setNeutralMode(NeutralMode.Coast);
    
    leftShooter.config_kP(0, 0.1);
    leftShooter.config_kI(0, 0.2);
    leftShooter.config_kD(0, 0.0);

    rightShooter.configNominalOutputForward(0);
    rightShooter.configNominalOutputReverse(0);
    rightShooter.configPeakOutputForward(1);
    rightShooter.configPeakOutputReverse(-1);
    rightShooter.setNeutralMode(NeutralMode.Coast);

    
    rightShooter.config_kP(0, 0.0);
    rightShooter.config_kI(0, 0.0);
    rightShooter.config_kD(0, 0.0);
  }

  public void runShooterCC(double value){
    leftShooter.set(ControlMode.Current, value*9);
    rightShooter.set(ControlMode.Current, value*9);
  }

  public void runShooterPercent(double value){
    leftShooter.set(ControlMode.PercentOutput, value);
    rightShooter.set(ControlMode.PercentOutput, value);
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
