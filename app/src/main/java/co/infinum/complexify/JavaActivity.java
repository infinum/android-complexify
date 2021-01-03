package co.infinum.complexify;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import co.infinum.complexify.databinding.ActivityJavaBinding;
import co.infinum.complexify.ui.EditTextExtKt;

class JavaActivity extends AppCompatActivity {

    private ActivityJavaBinding binding;

    private final Complexify complexify = new Complexify(
        ComplexifyBanMode.LOOSE,
        0.5,
        6,
        new String[] {"123", "password"}
    );

    private final ComplexityListener listener = new ComplexityListener() {
        @Override
        public void onSuccess(boolean isValid, double complexity) {
            binding.tvComplexity.setText(String.valueOf(complexity));
            binding.tvValid.setText(getString(isValid ? R.string.valid : R.string.non_valid));
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJavaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        EditTextExtKt.setComplexityListener(binding.editText, complexify, listener);
    }

    private void initManually() {
        binding.editText.addTextChangedListener(
            new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // no-op
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // no-op
                }

                @Override
                public void afterTextChanged(Editable s) {
                    complexify.checkComplexityOfPassword(s.toString(), listener);
                }
            }
        );
    }
}
