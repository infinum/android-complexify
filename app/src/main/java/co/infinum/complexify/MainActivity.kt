package co.infinum.complexify

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import co.infinum.complexify_android.Complexify

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        val input = findViewById<EditText>(R.id.editText)
        val tvComplexity = findViewById<TextView>(R.id.tvComplexity)
        val tvValid = findViewById<TextView>(R.id.tvValid)

        Complexify(
            input,
            { isValid, complexity ->
                tvComplexity.text = complexity.toString()
                tvValid.text = getString(
                    if (isValid) {
                        R.string.valid
                    } else {
                        R.string.non_valid
                    }
                )
            }
        )
    }
}
