package frc.robot.data

import com.kauailabs.navx.frc.AHRS
import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj.Encoder
import edu.wpi.first.wpilibj.SPI
import edu.wpi.first.wpilibj.interfaces.Gyro
import frc.vision.LimelightRunner
import frc.vision.VisionRunner


data class DriveSensors(
    val gyro: Gyro = AHRS(SPI.Port.kMXP),
    val leftEncoder: Encoder = Encoder(9, 8).apply { distancePerPulse = 0.390625 },
    val rightEncoder: Encoder = Encoder(6, 7).apply { distancePerPulse = 0.390625 },
    val vision: VisionRunner = LimelightRunner(),
)

data class MagazineSensors(
    val head: DigitalInput = DigitalInput(0),
    val magazine: DigitalInput = DigitalInput(1),
    val tail: DigitalInput = DigitalInput(2),
)

data class Sensors(
    val drive: DriveSensors = DriveSensors(),
    val magazine: MagazineSensors = MagazineSensors(),
)
