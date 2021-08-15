package frc.commands.auto.demo

import edu.wpi.first.wpilibj.Encoder
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitCommand
import frc.commands.auto.demo.Square
import edu.wpi.first.wpilibj2.command.CommandBase
import edu.wpi.first.wpilibj.controller.ProfiledPIDController
import frc.robot.maps.RobotMap

class AVeryMarkCommand(
    map: RobotMap,
    drive: DifferentialDrive,
    rightEncoder: Encoder,
    leftEncoder: Encoder
) : SequentialCommandGroup(
    Square(map, drive, rightEncoder, leftEncoder, 10.0, 90.0),
    Square(map, drive, rightEncoder, leftEncoder, 10.0, 90.0),
    Square(map, drive, rightEncoder, leftEncoder, 10.0, 90.0),
    Square(map, drive, rightEncoder, leftEncoder, 10.0, 90.0),
    Square(map, drive, rightEncoder, leftEncoder, 10.0, 90.0),
    Square(map, drive, rightEncoder, leftEncoder, 10.0, 90.0),
    Square(map, drive, rightEncoder, leftEncoder, 10.0, 90.0),
    Square(map, drive, rightEncoder, leftEncoder, 10.0, 90.0),
    Square(map, drive, rightEncoder, leftEncoder, 10.0, 90.0),
    Square(map, drive, rightEncoder, leftEncoder, 10.0, 90.0),
    Square(map, drive, rightEncoder, leftEncoder, 10.0, 90.0),
    Square(map, drive, rightEncoder, leftEncoder, 10.0, 90.0)
)