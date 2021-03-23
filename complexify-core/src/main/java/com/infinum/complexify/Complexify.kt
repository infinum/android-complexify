package com.infinum.complexify

import com.infinum.complexify.ComplexifyBanMode.*
import com.infinum.complexify.Constants.CHARSETS_ARRAY
import com.infinum.complexify.Constants.DEFAULT_BAN_LIST
import kotlin.math.ln
import kotlin.math.pow

/**
 * Class for calculation of password complexity.
 *
 * @param banMode             Use strict or loose comparisons for banned passwords.
 * @param strengthScaleFactor Required password strength multiplier. Bigger factor = more complex password required.
 * @param minimumChars        Minimum password length.
 * @param banList             A list of banned passwords which will reduce input password's complexity to 0 if the password contains one of them. The ban list is case-insensitive. The default values are generated from 500 worst passwords and 370 Banned Twitter lists: https://wiki.skullsecurity.org/Passwords
 */
class Complexify @JvmOverloads constructor(
    var banMode: ComplexifyBanMode = STRICT,
    var strengthScaleFactor: Double = 1.0,
    var minimumChars: Int = 8,
    var banList: Array<String> = DEFAULT_BAN_LIST
) {

    companion object {
        private const val MIN_COMPLEXITY = 49 // 12 chars with Upper, Lower and Number
        private const val MAX_COMPLEXITY = 120 // 25 chars, all charsets
        private const val MAX_PERCENTAGE = 100.0
    }

    /**
     * Check the complexity of a password.
     * Works synchronously.
     *
     * @param password The password to check
     * @param listener Callback
     */
    fun checkPasswordComplexity(password: String, listener: ComplexityListener) {
        var base = 0.0
        if (banList.isNotEmpty() && isInBanList(password)) {
            base = 1.0
        } else {
            // Add character complexity
            for (i in CHARSETS_ARRAY.indices.reversed()) {
                base += additionalComplexityForString(password, CHARSETS_ARRAY[i]).toDouble()
            }
        }

        val normalizedStrengthScaleFactor = 1.0 / strengthScaleFactor
        val complexity = ln(base.pow(password.length.toDouble())) // Use natural log to produce linear scale

        val scaledComplexity = complexity * normalizedStrengthScaleFactor
        val isValid = scaledComplexity > MIN_COMPLEXITY && password.length >= minimumChars

        // Scale to percentage, so it can be used for a progress bar
        val complexityPercentage = minOf(scaledComplexity / MAX_COMPLEXITY * MAX_PERCENTAGE, MAX_PERCENTAGE)
        listener.onSuccess(isValid, complexityPercentage)
    }

    /**
     * Check the complexity of a password.
     * Works asynchronously.
     *
     * @param password The password to check
     * @param listener Callback
     */
    fun checkPasswordComplexityAsync(password: String, listener: ComplexityListener) {
        Thread { checkPasswordComplexity(password, listener) }.start()
    }

    private fun additionalComplexityForString(string: String, charset: IntArray): Int {
        for (i in string.length - 1 downTo 0) {
            if (charset[0].toChar() <= string[i] && string[i] <= charset[1].toChar()) {
                return charset[1] - charset[0] + 1
            }
        }
        return 0
    }

    private fun isInBanList(password: String): Boolean {
        return when (banMode) {
            STRICT -> isInBanListStrict(password)
            LOOSE -> isInBanListLoose(password)
        }
    }

    private fun isInBanListStrict(password: String): Boolean {
        banList.forEach {
            if (password.contains(it, true)) {
                return true
            }
        }
        return false
    }

    private fun isInBanListLoose(password: String): Boolean {
        banList.forEach {
            if (password.equals(it, true)) {
                return true
            }
        }
        return false
    }
}
