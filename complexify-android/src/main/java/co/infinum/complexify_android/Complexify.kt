package co.infinum.complexify_android

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import co.infinum.complexify.Complexify
import co.infinum.complexify.ComplexifyBanMode
import co.infinum.complexify.ComplexityListener

/**
 * Class for calculation of password complexity.
 *
 * This class acts like adapter and encapsulates java complexify in order to use it on android.
 *
 * @param editText            EditText for which text complexity will be calculated
 * @param listener            ComplexityListener which is triggered on every text change, after complexity is calculated
 * @param banMode             Use strict or loose comparisons for banned passwords. (default: ComplexifyBanMode.STRICT)
 * @param executeMode         Defines whether execution is synchronous or asynchronous. (default: ComplexifyExecuteMode.SYNC)
 * @param strengthScaleFactor Required password strength multiplier (default: 1)
 * @param minimumChars        Minimum password length (default: 8)
 */
class Complexify @JvmOverloads constructor(
    editText: EditText,
    listener: ComplexityListener?,
    banMode: ComplexifyBanMode? = ComplexifyBanMode.STRICT,
    var executeMode: ComplexifyExecuteMode = ComplexifyExecuteMode.SYNC,
    strengthScaleFactor: Int = 1,
    minimumChars: Int = 8
) {
    private val javaComplexify: Complexify = Complexify(banMode, strengthScaleFactor, minimumChars)

    init {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) = Unit

            override fun afterTextChanged(s: Editable) {
                val passwordString = s.toString()
                when (executeMode) {
                    ComplexifyExecuteMode.SYNC -> javaComplexify.checkComplexityOfPassword(passwordString, listener)
                    ComplexifyExecuteMode.ASYNC -> javaComplexify.checkComplexityOfPasswordAsync(passwordString, listener)
                }
            }
        })
    }

    var banList: Array<String>
        get() = javaComplexify.banList
        set(banList) {
            javaComplexify.banList = banList
        }

    var banMode: ComplexifyBanMode?
        get() = javaComplexify.banMode
        set(banMode) {
            javaComplexify.banMode = banMode
        }

    var minimumChars: Int
        get() = javaComplexify.minimumChars
        set(minimumChars) {
            javaComplexify.minimumChars = minimumChars
        }

    var shouldUseBanList: Boolean
        get() = javaComplexify.shouldUseBanList()
        set(shouldUseBanList) {
            javaComplexify.setShouldUseBanList(shouldUseBanList)
        }

    var strengthScaleFactor: Int
        get() = javaComplexify.strengthScaleFactor
        set(strengthScaleFactor) {
            javaComplexify.strengthScaleFactor = strengthScaleFactor
        }
}
