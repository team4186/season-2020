package frc.robot.data

import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj2.command.button.Button
import edu.wpi.first.wpilibj2.command.button.JoystickButton
import frc.hardware.SaitekX52Buttons

data class Input(
    val forward: Double,
    val joystick: Joystick,
    val intake: Button,
    val cancelIntake: Button,
    val reverseIntake: Button,
    val shoot: Button,
    val ejectAll: Button,
    val attenuate: Button,
)

fun input(
    forward: Double = 1.0,
    joystick: Joystick,
    singleIntake: Button = JoystickButton(joystick, SaitekX52Buttons.TRIGGER + 1),
    cancelSingleIntake: Button = JoystickButton(joystick, SaitekX52Buttons.PINKIE + 1),
    keepEjecting: Button = JoystickButton(joystick, SaitekX52Buttons.FIRE_A + 1),
    shoot: Button = JoystickButton(joystick, SaitekX52Buttons.FIRE_C + 1),
    ejectAll: Button = JoystickButton(joystick, SaitekX52Buttons.FIRE_D + 1),
    attenuate: Button = JoystickButton(joystick, SaitekX52Buttons.FIRE_C + 1),
) = Input(
    forward = forward,
    joystick = joystick,
    intake = singleIntake,
    cancelIntake = cancelSingleIntake,
    reverseIntake = keepEjecting,
    shoot = shoot,
    ejectAll = ejectAll,
    attenuate = attenuate,
)