package com.infinum.complexify.ui

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.infinum.complexify.Complexify
import com.infinum.complexify.ComplexityListener

fun EditText.setComplexityListener(complexify: Complexify, complexityListener: ComplexityListener) {
    addTextChangedListener(
        object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

            override fun afterTextChanged(s: Editable?) {
                complexify.checkPasswordComplexity(s.toString(), complexityListener)
            }
        }
    )
}
