package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {

  // Joystick
  Joystick joystick = new Joystick(0);
  // JoystickButton topTrigger = new JoystickButton(joystick, 1);
  Joystick joystick2 = new Joystick(1);
  
  // Drivetrain
  Spark rightMotor = new Spark(0);
  Spark leftMotor = new Spark(1);
  boolean stopgo = false;
  boolean buttonPushedLastFrame = false;
  DifferentialDrive drive = new DifferentialDrive(leftMotor, rightMotor);

  @Override
  public void robotInit() {

  }

  @Override
  public void teleopPeriodic() {
  //  double x = joystick.getX();
    double y = joystick.getY();
    double y2 = joystick2.getY();
    rightMotor.set(y2);
    leftMotor.set(-y);
  


    // boolean buttonPushedThisFrame = topTrigger.get();

    // if (buttonPushedThisFrame && !buttonPushedLastFrame){
    //   stopgo = !stopgo;
    // }
    
    // buttonPushedLastFrame = buttonPushedThisFrame;
  

  //  // if (stopgo){
  //     drive.arcadeDrive(0, 0);
  //   }
  //   else {
  //     drive.arcadeDrive(-y, x);
  //   }

    // SmartDashboard.putBoolean("StopGo", stopgo);
    // SmartDashboard.putBoolean("Pushed", buttonPushedThisFrame);
  }
}
