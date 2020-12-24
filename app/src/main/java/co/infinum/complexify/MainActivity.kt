package co.infinum.complexify

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.infinum.complexify.databinding.ActivityMainBinding
import co.infinum.complexify.ui.setComplexityListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.editText.setComplexityListener(
            Complexify(
                ComplexifyBanMode.LOOSE,
                0.5,
                6
            ),
            object : ComplexityListener {
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
        )
    }
}
