package com.infinum.complexify

/**
 * Password complexity calculation listener
 */
fun interface ComplexityListener {

    /**
     * Called when the password complexity calculation is done.
     *
     * @param isValid    true if the password is valid, false otherwise
     * @param complexity A number from range [0.0, 100.0] where greater number represents greater complexity
     */
    fun onSuccess(isValid: Boolean, complexity: Double)
}
