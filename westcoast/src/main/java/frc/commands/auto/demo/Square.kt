package frc.commands.auto.demo

import edu.wpi.first.wpilibj.Encoder
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitCommand
import frc.commands.auto.LeaveLine
import frc.commands.auto.PerfectTurn
import frc.robot.maps.RobotMap

class Square
/**
 * Runs a group of commands sequentially.
 *
 * @param drive        The drivetrain.
 * @param rightEncoder The drivetrain rightEncoder.
 * @param leftEncoder  The drivetrain leftEncoder.
 * @param distance     The distance for a command to go to.
 * @param rotation     The angle for a command to turn to.
 */
(
    map: RobotMap,
    drive: DifferentialDrive,
    rightEncoder: Encoder,
    leftEncoder: Encoder,
    distance: Double,
    rotation: Double
) : SequentialCommandGroup(
    LeaveLine(map, drive, leftEncoder, rightEncoder, distance),
    WaitCommand(1.0),
    PerfectTurn(map, drive, leftEncoder, rightEncoder, rotation),
    WaitCommand(1.0),
    LeaveLine(map, drive, leftEncoder, rightEncoder, distance),
    WaitCommand(1.0),
    PerfectTurn(map, drive, leftEncoder, rightEncoder, rotation),
    WaitCommand(1.0),
    LeaveLine(map, drive, leftEncoder, rightEncoder, distance),
    WaitCommand(1.0),
    PerfectTurn(map, drive, leftEncoder, rightEncoder, rotation),
    WaitCommand(1.0),
    LeaveLine(map, drive, leftEncoder, rightEncoder, distance),
    WaitCommand(1.0),
    PerfectTurn(map, drive, leftEncoder, rightEncoder, rotation),
    WaitCommand(1.0)
)