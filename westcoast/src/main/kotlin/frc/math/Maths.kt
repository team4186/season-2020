package frc.math

import kotlin.math.absoluteValue
import kotlin.math.pow
import kotlin.math.sign

fun Double.deadband(deadzone: Double) =
    when (this) {
      in -deadzone..deadzone -> 0.0
      else -> this
    }

fun Double.antiband(antizone: Double) =
    when (this) {
      !in -antizone..antizone -> sign
      else -> this
    }

inline val Double.attenuated get() = sign * absoluteValue.pow(1.3)

fun Double.pidclean(clamp: Double, deadband: Double) = coerceIn(-clamp, clamp).deadband(deadband)

fun Double.joyclean(deadband: Double) = deadband(deadband).attenuated