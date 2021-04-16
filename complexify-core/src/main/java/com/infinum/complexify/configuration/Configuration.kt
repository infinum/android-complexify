package com.infinum.complexify.configuration

import com.infinum.complexify.ComplexifyBanMode
import com.infinum.complexify.Constants.DEFAULT_BAN_LIST

interface Configuration {

    /**
     * Which [ComplexifyBanMode] to use when using the [banList].
     */
    var banMode: ComplexifyBanMode

    /**
     * This is a scale factor applied to the calculated password strength.
     * It can be used to increase or decrease the strength of passwords required.
     */
    var strengthScaleFactor: Double

    /**
     * The minimum number of characters that the password must have in order to be valid.
     */
    var minimumChars: Int

    /**
     * A list of banned passwords which will reduce input password's complexity to 0 if the password contains one of them.
     * The ban list is case-insensitive.
     */
    var banList: Array<String>
}

open class DefaultConfiguration(
    override var banMode: ComplexifyBanMode = ComplexifyBanMode.STRICT,
    override var strengthScaleFactor: Double = 1.0,
    override var minimumChars: Int = 8,
    // The default values are generated from 500 worst passwords and 370 Banned Twitter lists: https://wiki.skullsecurity.org/Passwords
    override var banList: Array<String> = DEFAULT_BAN_LIST
) : Configuration
