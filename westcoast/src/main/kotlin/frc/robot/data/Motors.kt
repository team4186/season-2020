package frc.robot.data

import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.can.BaseMotorController
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX
import edu.wpi.first.wpilibj.SpeedController
import edu.wpi.first.wpilibj.VictorSP

data class DriveMotors(
    val lead: SpeedController,
    val follower0: SpeedController,
    val follower1: SpeedController,
) : SpeedController by lead

data class MagazineMotors(
    val intake: SpeedController = WPI_VictorSPX(7),
    val index: SpeedController = VictorSP(10),
    val magazine: SpeedController = VictorSP(12),
)

data class ShooterMotors(
    val main: WPI_TalonSRX = WPI_TalonSRX(8),
    val secondary: WPI_TalonSRX = WPI_TalonSRX(9),
)

data class Motors(
    val driveLeft: DriveMotors = driveCTRMotors(
        lead = WPI_TalonSRX(2),
        follower0 = WPI_VictorSPX(1),
        follower1 = WPI_VictorSPX(3)
    ),
    val driveRight: DriveMotors = driveCTRMotors(
        lead = WPI_TalonSRX(5),
        follower0 = WPI_VictorSPX(4),
        follower1 = WPI_VictorSPX(6)
    ),
    val magazine: MagazineMotors = MagazineMotors(),
    val shooter: ShooterMotors = ShooterMotors(),
)

fun <Main, Follower0, Follower1> driveCTRMotors(
    lead: Main,
    follower0: Follower0,
    follower1: Follower1,
): DriveMotors where
    Main : SpeedController,
    Main : BaseMotorController,
    Follower0 : SpeedController,
    Follower0 : BaseMotorController,
    Follower1 : SpeedController,
    Follower1 : BaseMotorController {

  follower0.follow(lead)
  follower1.follow(lead)

  if (lead is TalonSRX) with(lead) {
    configContinuousCurrentLimit(18)
    configPeakCurrentLimit(20)
    configPeakCurrentDuration(45)
    enableCurrentLimit(true)
  }

  lead.setNeutralMode(NeutralMode.Brake)

  return DriveMotors(lead, follower0, follower1)
}
