package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.SPI;
import frc.autonomousCommands.LeaveLine;
import frc.autonomousCommands.PerfectTurn;
import frc.commands.*;
import frc.motorFactory.*;

public class Robot extends TimedRobot {

  //Drivetrain
  MotorFactory hybridFactory = new MotorFactoryHybrid();
  private final SpeedController leftMain = hybridFactory.create(2, 1, 3);
  private final SpeedController rightMain = hybridFactory.create(5, 4 ,6);
  private final DifferentialDrive drive = new DifferentialDrive(leftMain, rightMain);

  //Subsystem Motors
  private final WPI_TalonSRX intake = new WPI_TalonSRX(7);

  //Sensors
  private final AHRS ahrs = new AHRS(SPI.Port.kMXP);
  private final Encoder leftEncoder = new Encoder(0, 1);
  private final Encoder rightEncoder = new Encoder(3, 2);

  //Inputs
  private final Joystick joystick = new Joystick(0);
  
  //Commands
  private final GyroDrive teleOp = new GyroDrive(drive, joystick, ahrs);
  private final TeleopDrive teleop = new TeleopDrive(drive, joystick);

  //Autonomous Commands
  private final LeaveLine leaveLine = new LeaveLine(drive, rightEncoder, ahrs, 10);
  private final PerfectTurn perfectTurn = new PerfectTurn(drive, ahrs, 90);

  @Override
  public void robotInit() {
    joystick.setTwistChannel(4);
    drive.setSafetyEnabled(false);
  }

  @Override
  public void robotPeriodic() {

  }

  @Override
  public void autonomousInit() {
    ahrs.reset();
    leftEncoder.reset();
    rightEncoder.reset();

    //leaveLine.schedule();
    perfectTurn.schedule();
  }

  @Override
  public void autonomousPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    ahrs.reset();
    leftEncoder.reset();
    rightEncoder.reset();
    
    if(joystick.getTwist() >= 0){
      teleOp.schedule();
    }
    else{
      teleop.schedule();
    }
  }

  @Override
  public void teleopPeriodic() {
    CommandScheduler.getInstance().run();
  }
}
