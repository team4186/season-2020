package frc.robot.data

import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitCommand
import frc.commands.drive.GyroDrive
import frc.commands.drive.LeaveLine
import frc.commands.drive.PerfectTurn
import frc.commands.drive.TeleopDrive
import frc.commands.magazine.*
import frc.commands.targeting.AlignToTarget
import frc.commands.targeting.FindTarget
import frc.commands.targeting.StayOnTarget
import frc.subsystems.DriveTrainSubsystem
import frc.subsystems.MagazineSubsystem

data class Data(
    val name: String,
    val parameters: Parameters = Parameters(),
    val input: Input = input(
        forward = 1.0,
        joystick = Joystick(0),
    ),
    val motors: Motors = Motors(),
    val sensors: Sensors = Sensors(),
    val controllers: Controllers = Controllers(),
    val subsystems: Subsystems = Subsystems(
        driveTrain = DriveTrainSubsystem(
            left = motors.driveLeft,
            right = motors.driveRight,
            leftEncoder = sensors.drive.leftEncoder,
            rightEncoder = sensors.drive.rightEncoder,
            gyro = sensors.drive.gyro,
            vision = sensors.drive.vision,
        ),

        magazine = MagazineSubsystem(
            intakeMotor = motors.magazine.intake,
            indexMotor = motors.magazine.index,
            magazineMotor = motors.magazine.magazine,
            leftShooter = motors.shooter.main,
            rightShooter = motors.shooter.secondary,
            headSensor = sensors.magazine.head,
            abSensor = sensors.magazine.magazine,
            tailSensor = sensors.magazine.tail,
        ),
    ),
    val commands: Commands = Commands(
        teleop = TeleopCommands(
            raw = {
              TeleopDrive(
                  forward = input.forward,
                  joystick = input.joystick,
                  attenuate = input.attenuate,
                  drive = subsystems.driveTrain,
              )
            },
            assisted = {
              GyroDrive(
                  forward = input.forward,
                  controller = controllers.pid<GyroDrive>(),
                  joystick = input.joystick,
                  drive = subsystems.driveTrain,
              )
            },
        ),

        drive = DriveCommands(
            leaveLine = { distance ->
              LeaveLine(
                  distance = distance,
                  distanceMultiplier = parameters.leaveLineDistanceMultiplier,
                  left = controllers.profiledPid<LeaveLine>(),
                  right = controllers.profiledPid<LeaveLine>(),
                  drive = subsystems.driveTrain,
              )
            },
            perfectTurn = { angle ->
              PerfectTurn(
                  angle = angle,
                  angleMultiplier = parameters.perfectTurnAngleMultiplier,
                  right = controllers.profiledPid<PerfectTurn>(),
                  left = controllers.profiledPid<PerfectTurn>(),
                  drive = subsystems.driveTrain,
              )
            },
            alignToTarget = {
              AlignToTarget(
                  controller = controllers.pid<AlignToTarget>(),
                  drive = subsystems.driveTrain,
              )
            },

            stayOnTarget = {
              StayOnTarget(
                  controller = controllers.pid<StayOnTarget>(),
                  drive = subsystems.driveTrain
              )
            },
            findTarget = {
              FindTarget(
                  drive = subsystems.driveTrain
              )
            },
        ),

        magazine = MagazineCommands(
            index = { IndexLogic(subsystems.magazine) },
            intake = { IntakeLogic(subsystems.magazine) },
            intakeOut = { IntakeOut(subsystems.magazine) },
            everythingOut = { EverythingOut(subsystems.magazine) },
        ),

        shooter = ShooterCommands(
            shoot = { Shoot(subsystems.magazine) },
            shooterAccelerator = { ShooterAccelerator(subsystems.magazine) },
        ),
    ),

    val compoundCommands: Compound = Compound(
        intakeAndIndex = SequentialCommandGroup(
            commands.magazine.intake(),
            WaitCommand(0.2),
            commands.magazine.index(),
        ),

        shoot = SequentialCommandGroup(
            commands.drive.alignToTarget(),
            SequentialCommandGroup.race(
                commands.shooter.shooterAccelerator(),
                WaitCommand(1.5),
            ),
            SequentialCommandGroup.race(
                commands.shooter.shoot(),
                commands.drive.stayOnTarget(),
                WaitCommand(4.0),
            ),
        ),
    )
)
