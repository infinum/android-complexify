package co.infinum.complexify

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import co.infinum.complexify.databinding.ActivityMainBinding
import co.infinum.complexify.ui.setComplexityListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val complexify = Complexify(
        ComplexifyBanMode.LOOSE,
        0.5,
        6,
        arrayOf("123", "password")
    )

    private val listener = object : ComplexityListener {
        override fun onSuccess(isValid: Boolean, complexity: Double) {
            binding.tvComplexity.text = complexity.toString()
            binding.tvValid.text = getString(
                if (isValid) {
                    R.string.valid
                } else {
                    R.string.non_valid
                }
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.editText.setComplexityListener(complexify, listener)
    }

    private fun initManually() {
        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

            override fun afterTextChanged(s: Editable?) {
                complexify.checkComplexityOfPassword(s.toString(), listener)
            }
        })
    }
}
