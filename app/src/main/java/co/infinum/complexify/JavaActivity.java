package com.infinum.complexify;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.infinum.complexify.databinding.ActivityMainBinding;
import com.infinum.complexify.ui.EditTextExtKt;

class JavaActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private final Complexify complexify = new Complexify(
        ComplexifyBanMode.LOOSE,
        0.5,
        6,
        new String[] {"123", "password"}
    );

    private final ComplexityListener listener = new ComplexityListener() {
        @Override
        public void onSuccess(boolean isValid, double complexity) {
            binding.complexityLabel.setText(String.valueOf(complexity));
            binding.validityLabel.setText(getString(isValid ? R.string.valid : R.string.non_valid));
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        EditTextExtKt.setComplexityListener(binding.passwordInput, complexify, listener);
    }

    private void initManually() {
        binding.passwordInput.addTextChangedListener(
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
                    complexify.checkPasswordComplexity(s.toString(), listener);
                }
            }
        );
    }
}
