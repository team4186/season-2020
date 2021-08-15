package frc.hardware

import edu.wpi.first.hal.FRCNetComm
import edu.wpi.first.hal.HAL
import edu.wpi.first.wpilibj.DriverStation

object SaitekX52Axis {
  const val X = 0
  const val Y = 1
  const val THROTTLE = 2
  const val ROTARY_0 = 3
  const val ROTARY_1 = 4
  const val TWIST = 5
  const val SLIDER = 6

  const val AXES_COUNT = 7
}

object SaitekX52Pov {
  const val STICK_POV = 0
  const val STICK_HAT = 1 // fake
  const val THROTTLE_HAT = 2 // fake

  const val POV_COUNT = 3
}

object SaitekX52PovDirection {
  const val UP = 0
  const val RIGHT = 90
  const val DOWN = 180
  const val LEFT = 270
}

object SaitekX52Buttons {
  const val TRIGGER = 0
  const val LAUNCH = 1
  const val FIRE_A = 2
  const val FIRE_B = 3
  const val FIRE_C = 4
  const val PINKIE = 5
  const val FIRE_D = 6
  const val FIRE_E = 7
  const val TOGGLE_1 = 8
  const val TOGGLE_2 = 9
  const val TOGGLE_3 = 10
  const val TOGGLE_4 = 11
  const val TOGGLE_5 = 12
  const val TOGGLE_6 = 13
  const val SECOND_TRIGGER = 14
  const val MOUSE_FIRE = 15
  const val WHEEL_SCROLLDOWN = 16
  const val WHEEL_SCROLLUP = 17
  const val WHEEL_PRESS = 18

  const val JOY_HAT_UP = 19
  const val JOY_HAT_RIGHT = 20
  const val JOY_HAT_DOWN = 21
  const val JOY_HAT_LEFT = 22

  const val THROTTLE_HAT_UP = 23
  const val THROTTLE_HAT_RIGHT = 24
  const val THROTTLE_HAT_DOWN = 25
  const val THROTTLE_HAT_LEFT = 26

  const val MODE_1 = 27
  const val MODE_2 = 28
  const val MODE_3 = 29

  const val CLUTCH = 30
  const val FIRE_I = 30

  const val TIMER_PG_PRESS = 31

  // TODO check if anything beyond the Button 32 is reported
  const val TIMER_STARTSTOP = 32
  const val TIMER_RESET = 33
  const val TIMER_PG_UP = 34
  const val TIMER_PG_DOWN = 35

  const val TIMER_ARROW_UP = 36
  const val TIMER_ARROW_DOWN = 37
  const val TIMER_ARROW_PRESS = 38

  internal const val BUTTON_COUNT = 39
}

object SaitekX52Mode {
  const val RED = 0
  const val PURPLE = 1
  const val BLUE = 2
}

data class ButtonData(
    internal var state: Int = 0,
    internal var count: Int = 0
) {
  operator fun get(button: Int) = (state and (1 shl button)) > 0
}

fun saitekX52Adapter(port: Int) =
    SaitekX52Adapter(port).also {
      HAL.report(FRCNetComm.tResourceType.kResourceType_Joystick, port, 0, "")
    }

class SaitekX52Adapter internal constructor(
    private val port: Int
) {
  val buttons = ButtonData()

  inline val mode
    get() = when {
      buttons[SaitekX52Buttons.MODE_1] -> SaitekX52Mode.RED
      buttons[SaitekX52Buttons.MODE_2] -> SaitekX52Mode.PURPLE
      buttons[SaitekX52Buttons.MODE_3] -> SaitekX52Mode.BLUE
      else -> SaitekX52Mode.BLUE // TODO evaluate if error is a better choice
    }

  val axes = DoubleArray(SaitekX52Axis.AXES_COUNT)
  val povs = IntArray(SaitekX52Pov.POV_COUNT)

  fun DriverStation.update() {
    buttons.state = getStickButtons(port)
    buttons.count = getStickButtonCount(port)

    for (axis in axes.indices) axes[axis] = axis(axis)
    for (pov in povs.indices) povs[pov] = pov(pov)
  }

  // TODO getStickWhatever ops uses a lock, we can make a better job by accessing the original integer and create the array from there
  private fun DriverStation.axis(axis: Int) = getStickAxis(port, axis)

  private fun DriverStation.pov(pov: Int) = when (pov) {
    SaitekX52Pov.STICK_POV -> realPov(SaitekX52Pov.STICK_POV)
    SaitekX52Pov.STICK_HAT -> stickHat
    SaitekX52Pov.THROTTLE_HAT -> throttleHat
    else -> -1
  }


  private fun DriverStation.realPov(pov: Int) = getStickPOV(port, pov)
  private fun fakePov(up: Int, right: Int, down: Int, left: Int): Int {
    val upPress = buttons[up]
    val rightPress = buttons[right]
    val downPress = buttons[down]
    val leftPress = buttons[left]
    return when {
      upPress && rightPress -> 45
      upPress && leftPress -> 315
      downPress && rightPress -> 135
      downPress && leftPress -> 225
      upPress -> SaitekX52PovDirection.UP
      rightPress -> SaitekX52PovDirection.RIGHT
      downPress -> SaitekX52PovDirection.DOWN
      leftPress -> SaitekX52PovDirection.LEFT
      else -> -1
    }
  }

  private val stickHat get() = fakePov(SaitekX52Buttons.JOY_HAT_UP, SaitekX52Buttons.JOY_HAT_RIGHT, SaitekX52Buttons.JOY_HAT_DOWN, SaitekX52Buttons.JOY_HAT_LEFT)
  private val throttleHat get() = fakePov(SaitekX52Buttons.THROTTLE_HAT_UP, SaitekX52Buttons.THROTTLE_HAT_RIGHT, SaitekX52Buttons.THROTTLE_HAT_DOWN, SaitekX52Buttons.THROTTLE_HAT_LEFT)
}

class JoystickState(
    var mode: Int,
    val axes: DoubleArray,
    val povs: IntArray,
    val buttons: ButtonData
)

fun SaitekX52Adapter.copyTo(joystickState: JoystickState) {
  joystickState.mode = mode
  axes.copyInto(joystickState.axes)
  povs.copyInto(joystickState.povs)

  with(joystickState.buttons) {
    state = buttons.state
    count = buttons.count
  }

}
