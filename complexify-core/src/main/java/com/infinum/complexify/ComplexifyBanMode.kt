package com.infinum.complexify

/**
 * Which ban mode to use when using the [Complexify.banList].
 */
enum class ComplexifyBanMode {
    /**
     * Don't allow a substring of a banned password.
     *
     * If a password is contained in the banned list, or contained in any item of the banned list, the password will fail.
     * This means that "123456" will fail as it is in the banned list, but "123" and "345" will also fail as they are substrings of a banned password.
     */
    STRICT,

    /**
     * Only ban exact matches of a banned password.
     */
    LOOSE
}
