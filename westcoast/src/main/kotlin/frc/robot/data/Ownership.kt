package frc.robot.data


@Suppress("unused")
@JvmInline
value class DoubleParameter<Owner>(val value: Double)

inline fun <reified Owner> Double.param() = DoubleParameter<Owner>(this)

@JvmInline
value class Provider<T, Owner>(val provider: () -> T) {
  @Suppress("unused")
  operator fun <O : Owner> invoke() = provider()
}
