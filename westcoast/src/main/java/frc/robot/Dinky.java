package frc.robot;

import edu.wpi.first.wpilibj2.command.button.*;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.*;
import frc.subsystems.vision.*;
import frc.subsystems.vision.targeting.*;
import frc.subsystems.drive.motorfactory.*;
import frc.maps.*;
import frc.subsystems.drive.*;
import frc.commands.ballhandling.ShooterLogic;
import frc.commands.motors.*;

public class Dinky extends TimedRobot {

  //Robot Map
  private final RobotMap map = new DinkyMap();

  // Drivetrain

  // Subsystem Motors
  private final WPI_VictorSPX intake = new WPI_VictorSPX(7);
  private final WPI_TalonSRX rightShooter = new WPI_TalonSRX(8);
  private final WPI_TalonSRX index = new WPI_TalonSRX(9);
  private final WPI_TalonSRX leftShooter = new WPI_TalonSRX(5);
  private final VictorSP mag = new VictorSP(12);
  // private final WPI_TalonSRX leftShooter = new WPI_TalonSRX(8);
  // private final WPI_TalonSRX rightShooter = new WPI_TalonSRX(9);

  // Sensors
  private final AHRS ahrs = new AHRS(SPI.Port.kMXP);
  private final VisionRunner vision = new RioVisionRunner();

  // Inputs
  private final Joystick joystick = new Joystick(0);
  private final XboxController xboxController = new XboxController(0);
  private final JoystickButton lB = new JoystickButton(xboxController, 5);
  private final JoystickButton lT = new JoystickButton(xboxController, 7);
  private final JoystickButton rB = new JoystickButton(xboxController, 6);
  private final JoystickButton rT = new JoystickButton(xboxController, 8);
  
  // Commands
  // private final GyroDrive teleop = new GyroDrive(drive, joystick, ahrs);
  // private final Command setIntake = new SetMotor(intake, -0.4);

  // Autonomous Commands

  @Override
  public void robotInit() {
    ahrs.reset();
    vision.init();
  }

  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("alignX", vision.getAlignX());
  }

  @Override
  public void teleopInit() {
    final Command intakedex = new SetTwoMotors(intake, index, 0.5, true);
    final Command ingazine = new SetTwoMotors(index, mag, 0.4);
    final Command setShooter = new ShooterLogic(rightShooter, leftShooter);
    final Command constFeedShot = new SetMotor(mag, 0.3);
    final Command shoot = new ShooterLogic(rightShooter, leftShooter);

    lB.whileHeld(ingazine);
    lT.whileHeld(intakedex);
    rT.whileHeld((setShooter.raceWith(new WaitCommand(1))).andThen(shoot.alongWith(constFeedShot)));
  }

  @Override
  public void teleopPeriodic() {
    CommandScheduler.getInstance().run();
    SmartDashboard.putNumber("Amps", rightShooter.getSupplyCurrent());
  }
}
