package frc.vision;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.math.*;

public class AlignToTarget extends CommandBase {
  private String name;
  private DifferentialDrive drive;
  private ProfiledPIDController turnRight;
  private ProfiledPIDController turnLeft;
  private Encoder leftEncoder;
  private Encoder rightEncoder;
  private double wait;
  private double value;
  private double goal;
  private boolean end;
  private boolean cache;
  private Constraints constraints;

  /**
   * Turns to a target found by GRIP.
   * @param drive The drivetrain.
   * @param leftEncoder The drivetrain leftEncoder.
   * @param rightEncoder The drivetrain rightEncoder.
   * @param name The name of the SmartDashboard published value
   */
  public AlignToTarget(
    DifferentialDrive drive,
    Encoder leftEncoder,
    Encoder rightEncoder,
    String name
  ) {
    this.name = name;
    this.drive = drive;
    this.leftEncoder = leftEncoder;
    this.rightEncoder = rightEncoder;
  }

  @Override
  public void initialize() {
    wait = 0;
    cache = false;

    leftEncoder.reset();
    rightEncoder.reset();

    constraints = new Constraints(150, 150);
    double P = 0.1;
    double I = 0;
    double D = 0;
    turnLeft = new ProfiledPIDController(P, I, D, constraints);
    turnRight = new ProfiledPIDController(P, I, D, constraints);

    turnLeft.reset(0, 0);
    turnLeft.setTolerance(10, 50);
    turnLeft.disableContinuousInput();

    turnRight.reset(0, 0);
    turnRight.setTolerance(10, 50);
    turnRight.disableContinuousInput();
  }

  @Override
  public void execute() {
    value = SmartDashboard.getNumber(name, 160);

    if(cache){
    }
    else{
      goal = scaler(value);
      cache = true;
    }

    double rightside = Maths.clamp(turnRight.calculate(-rightEncoder.getDistance(), goal),0.4);
    double leftside = Maths.clamp(turnLeft.calculate(-leftEncoder.getDistance(), -goal),0.4);

    drive.tankDrive(leftside, rightside, false);

    if(turnRight.atGoal() && turnLeft.atGoal()){
      wait = wait + 1;
      if(wait >= 10){
        end = true;
      }
      else{
        end = false;
      }
    }
    else{
      end = false;
    }

    SmartDashboard.putNumber("Offset", scaler(value));
  }

  @Override
  public void end(boolean interrupted) {
    turnLeft.reset(0, 0);
    turnRight.reset(0, 0);
    leftEncoder.reset();
    rightEncoder.reset();
    drive.stopMotor();
    System.out.println("what t-the fuck did you just fuckinyg say a-about me, y-you wittwe b-bitch? i-i’ww h-have you k-knyow i-i gwaduated top o-of m-my c-cwass iny the ny-nyavy s-seaws, anyd i-i’ve beeny inyvowved iny nyumewous secwet w-waids ony a-aw-quaeda, anyd i have uvw 300 conyfiwmed kiwws. i am twainyed iny gowiwwa wawfawe anyd i-i’m t-the top s-snyipew iny t-the enytiwe us a-awmed fowces. you awe ny-nyothinyg to me b-but just anyothew t-tawget. i-i w-wiww wipe you t-the fuck out w-with p-pwecisiony the wikes of which has nyevew b-beeny seeny b-befowe ony this e-eawth, mawk m-my fuckinyg wowds. you t-thinyk you c-cany get away with sayinyg that shit to m-me uvw t-the inytewnyet? thinyk againy, fuckew. a-as we speak i-i am conytactinyg my secwet ny-nyetwowk o-of spies acwoss t-the u-usa anyd youw ip is b-beinyg t-twaced w-wight nyow s-so you bettew p-pwepawe fow the s-stowm, m-maggot. the stowm that wipes o-out the pathetic wittwe thinyg you c-caww youw w-wife. y-you’we f-fuckinyg dead, kid. i cany be a-anyywhewe, anyytime, anyd i-i c-cany k-kiww y-you iny o-uvw seveny hunydwed ways, anyd that’s just with my bawe hanyds. nyot onywy am i extenysivewy t-twainyed iny unyawmed combat, but i-i h-have access to the e-enytiwe awsenyaw o-of the u-unyited states mawinye cowps a-anyd i wiww use it to its fuww extenyt to wipe youw m-misewabwe ass o-off the f-face o-of the c-conytinyenyt, y-you wittwe s-shit. i-if onywy y-you couwd h-have knyowny what unyhowy w-wetwibutiony y-youw wittwe “cwevew” commenyt was about to b-bwinyg downy upony y-you, maybe you w-wouwd have hewd youw fuckinyg t-tonygue. b-but you c-couwdny’t, y-you didny’t, anyd ny-nyow you’we p-payinyg the p-pwice, you goddamny i-idiot. i-i wiww s-shit fuwy a-aww uvw you anyd y-you w-wiww dwowny iny it. y-you’we f-fuckinyg dead, k-kiddo. x3");
    cache = false;
  }

  @Override
  public boolean isFinished() {
    if(end){
      return true;
    }
    else{
      return false;
    }
  }

  private double scaler(double value){
    return ((value-160)/160)*23.5*1.95;
  }
}
