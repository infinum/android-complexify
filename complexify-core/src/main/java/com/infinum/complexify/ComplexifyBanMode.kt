package com.infinum.complexify

/**
 * Which ban mode to use when using the [Complexify.banList].
 */
enum class ComplexifyBanMode {
    /**
     * Don't allow a substring of a banned password.
     */
    STRICT,

    /**
     * Only ban exact matches of a banned password.
     */
    LOOSE
}
