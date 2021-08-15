package frc.robot;

import com.analog.adis16448.frc.ADIS16448_IMU;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.maps.JankyMap;
import frc.robot.maps.RobotMap;
import frc.subsystems.drive.TeleopDrive;

public class Janky extends TimedRobot {

    //Robot Map
    private final RobotMap map = new JankyMap();

    // Drivetrain
    private final Spark rightMotor = new Spark(0);
    private final Spark leftMotor = new Spark(1);
    private final DifferentialDrive drive = new DifferentialDrive(leftMotor, rightMotor);

    // Inputs
    private final Joystick joystick = new Joystick(0);

    // Sensors
    private final ADIS16448_IMU adis = new ADIS16448_IMU();

    // Commands
    private final TeleopDrive teleop = new TeleopDrive(map, drive, joystick);
    //private final AdisDrive teleop = new AdisDrive(map, drive, joystick, adis);

    // Network Table
    private final NetworkTableInstance inst = NetworkTableInstance.getDefault();
    private final NetworkTable table = inst.getTable("Jetson");
    NetworkTableEntry view1 = table.getEntry("view1");
    NetworkTableEntry view2 = table.getEntry("view2");

    @Override
    public void robotInit() {
        drive.setSafetyEnabled(false);
    }

    @Override
    public void robotPeriodic() {
        view1.setBoolean(!joystick.getRawButton(5));
        view2.setBoolean(joystick.getRawButton(5));
        SmartDashboard.putNumber("ADIS Value", adis.getRate());
    }

    @Override
    public void teleopInit() {
        teleop.schedule();
    }

    @Override
    public void teleopPeriodic() {
        CommandScheduler.getInstance().run();
    }
}