package com.christsondev.utilities

import java.math.BigDecimal
import java.math.RoundingMode

class AppDecimal private constructor(value: BigDecimal) : Comparable<AppDecimal> {
    private val value: BigDecimal = value.setScale(2, RoundingMode.HALF_UP)

    // --- Arithmetic Operations ---
    operator fun plus(other: AppDecimal) = AppDecimal(this.value.add(other.value))
    operator fun plus(other: BigDecimal) = AppDecimal(this.value.add(other))
    operator fun plus(other: Float) = plus(other.toDouble())
    operator fun plus(other: Double) = AppDecimal(this.value.add(BigDecimal.valueOf(other)))
    operator fun plus(other: Int) = plus(other.toLong())
    operator fun plus(other: Long) = AppDecimal(this.value.add(BigDecimal.valueOf(other)))

    operator fun minus(other: AppDecimal) = AppDecimal(this.value.subtract(other.value))
    operator fun minus(other: BigDecimal) = AppDecimal(this.value.subtract(other))
    operator fun minus(other: Float) = minus(other.toDouble())
    operator fun minus(other: Double) = AppDecimal(this.value.subtract(BigDecimal.valueOf(other)))
    operator fun minus(other: Int) = minus(other.toLong())
    operator fun minus(other: Long) = AppDecimal(this.value.subtract(BigDecimal.valueOf(other)))

    operator fun times(other: AppDecimal) = AppDecimal(this.value.multiply(other.value))
    operator fun times(other: BigDecimal) = AppDecimal(this.value.multiply(other))
    operator fun times(other: Float) = times(other.toDouble())
    operator fun times(other: Double) = AppDecimal(this.value.multiply(BigDecimal.valueOf(other)))
    operator fun times(other: Int) = times(other.toLong())
    operator fun times(other: Long) = AppDecimal(this.value.multiply(BigDecimal.valueOf(other)))

    operator fun div(other: AppDecimal) = AppDecimal(this.value.divide(other.value, 2, RoundingMode.HALF_UP))
    operator fun div(other: BigDecimal) = AppDecimal(this.value.divide(other, 2, RoundingMode.HALF_UP))
    operator fun div(other: Float) = div(other.toDouble())
    operator fun div(other: Double) = AppDecimal(this.value.divide(BigDecimal.valueOf(other), 2, RoundingMode.HALF_UP))
    operator fun div(other: Int) = div(other.toLong())
    operator fun div(other: Long) = AppDecimal(this.value.divide(BigDecimal.valueOf(other), 2, RoundingMode.HALF_UP))

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

        fun parse(value: Float) = parse(value.toDouble())

        fun parse(value: Double) = AppDecimal(BigDecimal.valueOf(value))

        fun parse(value: Int) = parse(value.toLong())

        fun parse(value: Long) = AppDecimal(BigDecimal.valueOf(value))

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