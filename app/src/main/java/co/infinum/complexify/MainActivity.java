package co.infinum.complexify;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import co.infinum.complexify_android.Complexify;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        EditText input = (EditText) findViewById(R.id.editText);
        final TextView tvComplexity = (TextView) findViewById(R.id.tvComplexity);
        final TextView tvValid = (TextView) findViewById(R.id.tvValid);

        Complexify c = new Complexify(input, new ComplexityListener() {
            @Override
            public void onSuccess(boolean isValid, double complexity) {
                tvComplexity.setText(String.valueOf(complexity));

                if (isValid) {
                    tvValid.setText(getString(R.string.valid));
                } else {
                    tvValid.setText(getString(R.string.non_valid));
                }
            }
        });
    }
}
