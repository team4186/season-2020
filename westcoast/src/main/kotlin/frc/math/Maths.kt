package frc.math

object Maths {
  /**
   * Code originally from edu.wpi.first.wpiutil.math.MathUtil.
   * Returns value clamped between low and high boundaries.
   *
   * @param value Value to clamp.
   * @param out   The boundary to which to clamp value.
   */
  @JvmStatic fun clamp(value: Double, out: Double): Double {
    return Math.max(-out, Math.min(value, out))
  }

  /**
   * Returns values outside of a certain zone, so as to remove noise.
   *
   * @param value    Value given.
   * @param deadzone The boundry in whicxh the value doesn't register.
   */
  fun deadband(value: Double, deadzone: Double): Double {
    return if (value < deadzone && value > -deadzone) {
      0.0
    }
    else {
      value
    }
  }

  fun antiband(value: Double, antizone: Double): Double {
    return if (value < -antizone || value > antizone) {
      Math.signum(value)
    }
    else {
      value
    }
  }

  /**
   * Attenuates a value by some amount to make the control curve cleaner.
   *
   * @param value Value to be attenuated.
   */
  @JvmStatic fun attenuate(value: Double): Double {
    return Math.signum(value) * Math.pow(Math.abs(value), 1.3)
  }

  /**
   * Clamps and deadbands so as it creates a slower, less noisy PID loop.
   *
   * @param value Value to be cleaned.
   * @param out   The boundry to which to clamp value.
   * @param in    The boundry in which the value doesn't register.
   */
  @JvmStatic fun pidclean(value: Double, out: Double, `in`: Double): Double {
    return deadband(clamp(value, out), `in`)
  }

  /**
   * Attenuates and deadbands so as to avoid joystick drift and control problems.
   *
   * @param value Value to deadbanded and attenuated.
   * @param in    The boundry in whicxh the value doesn't register.
   */
  @JvmStatic fun joyclean(value: Double, `in`: Double): Double {
    return attenuate(deadband(value, `in`))
  }
}