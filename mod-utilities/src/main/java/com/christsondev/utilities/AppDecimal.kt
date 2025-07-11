package com.christsondev.utilities

import java.math.BigDecimal
import java.math.RoundingMode

class AppDecimal private constructor(private val value: BigDecimal): Comparable<AppDecimal> {
    // Primary constructor for internal use, ensures scale
    init {
        // Ensure the internal BigDecimal always maintains 2 decimal places with HALF_UP rounding
        // This is a good place to enforce invariants for your custom type.
        // We use a private constructor to ensure all instances are created via controlled means.
        this.value.setScale(2, RoundingMode.HALF_UP)
    }

    // --- Arithmetic Operations (delegating to internal BigDecimal) ---
    operator fun plus(other: AppDecimal) =  AppDecimal(this.value.add(other.value))

    operator fun minus(other: AppDecimal) = AppDecimal(this.value.subtract(other.value))

    operator fun times(multiplier: BigDecimal) = AppDecimal(this.value.multiply(multiplier))

    // When dividing, you might need to specify precision for the division itself
    // and then set the final scale.
    operator fun div(divisor: BigDecimal) = AppDecimal(this.value.divide(divisor, 2, RoundingMode.HALF_UP))

    operator fun times(value: Int) = times(BigDecimal(value))
    operator fun div(value: Int) = div(BigDecimal(value))

    // --- Comparison Operations ---
    fun isZero() = value.compareTo(BigDecimal.ZERO) == 0

    fun isPositive() = value > BigDecimal.ZERO

    fun isNegative() = value < BigDecimal.ZERO

    // Compare numerically, ignoring scale differences in the underlying BigDecimal
    override operator fun compareTo(other: AppDecimal): Int {
        return this.value.compareTo(other.value)
    }

    // --- Utility/Conversion Methods ---
    fun toPlainString(): String = value.toPlainString() // Ensures no scientific notation

    fun toBigDecimal() = value // Expose the underlying BigDecimal if needed (read-only)

    fun toFloat() = value.toFloat()

    fun toDouble() = value.toDouble()

    // --- Standard Kotlin Overrides ---
    override fun toString(): String {
        return "AppDecimal(${value.toPlainString()})"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AppDecimal

        // Compare the underlying BigDecimal values numerically, ignoring scale differences
        // Use compareTo for value equality, not equals() on BigDecimal itself
        return value.compareTo(other.value) == 0
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    // Factory method (or secondary constructor) to create from a String
    // This is the preferred way to create CurrencyAmount from external input.
    companion object {
        // Convenience for zero
        val ZERO = AppDecimal(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP))

        fun parse(bigDecimal: BigDecimal) = AppDecimal(bigDecimal)

        fun parse(value: Float) = parse(value.toString())

        fun parse(amountString: String, defaultValue: AppDecimal? = null): AppDecimal {
            return try {
                val bigDecimal = BigDecimal(amountString)
                AppDecimal(bigDecimal.setScale(2, RoundingMode.HALF_UP)) // Enforce 2 decimal places on creation
            } catch (e: NumberFormatException) {
                defaultValue?.let {
                    return defaultValue
                }.orElse {
                    throw IllegalArgumentException("Invalid currency amount string: $amountString")
                }
            }
        }
    }
}