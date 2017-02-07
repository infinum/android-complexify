package co.infinum.complexify_android;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import co.infinum.complexify.ComplexifyBanMode;
import co.infinum.complexify.ComplexityListener;

/**
 * Class for calculation of password complexity.
 *
 * This class acts like adapter and encapsulates java complexify in order to use it on android.
 *
 * Created by Damian Marusic on 29.07.15..
 */
public class Complexify {

    private co.infinum.complexify.Complexify javaComplexify;

    private ComplexifyExecuteMode executeMode;

    /**
     * Default constructor.
     *
     * Default params are:
     * banMode - ComplexifyBanMode.STRICT
     * executeMode - ComplexifyExecuteMode.SYNC
     * strengthScaleFactor - 1
     * minimumChars - 8
     *
     * @param editText EditText for which text complexity will be calculated
     * @param listener ComplexifyListener which is triggered on every text change, after complexity is calculated
     */
    public Complexify(EditText editText, ComplexityListener listener) {
        this(editText, listener, ComplexifyBanMode.STRICT, ComplexifyExecuteMode.SYNC, 1, 8);
    }

    /**
     * Constructor.
     *
     * @param editText            EditText for which text complexity will be calculated
     * @param listener            ComplexityListener which is triggered on every text change, after complexity is calculated
     * @param banMode             Use strict or loose comparisons for banned passwords. (default: ComplexifyBanMode.STRICT)
     * @param executeMode         Defines whether execution is synchronous or asynchronous. (default: ComplexifyExecuteMode.SYNC)
     * @param strengthScaleFactor Required password strength multiplier (default: 1)
     * @param minimumChars        Minimum password length (default: 8)
     */
    public Complexify(EditText editText, final ComplexityListener listener, ComplexifyBanMode banMode,
            final ComplexifyExecuteMode executeMode, int strengthScaleFactor, int minimumChars) {
        javaComplexify = new co.infinum.complexify.Complexify(banMode, strengthScaleFactor, minimumChars);
        this.executeMode = executeMode;

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (executeMode == ComplexifyExecuteMode.SYNC) {
                    javaComplexify.checkComplexityOfPassword(s.toString(), listener);
                } else {
                    javaComplexify.checkComplexityOfPasswordAsync(s.toString(), listener);
                }
            }
        });
    }

    public ComplexifyBanMode getBanMode() {
        return javaComplexify.getBanMode();
    }

    public void setBanMode(ComplexifyBanMode banMode) {
        javaComplexify.setBanMode(banMode);
    }

    public ComplexifyExecuteMode getExecuteMode() {
        return executeMode;
    }

    public void setExecuteMode(ComplexifyExecuteMode executeMode) {
        this.executeMode = executeMode;
    }

    public int getStrengthScaleFactor() {
        return javaComplexify.getStrengthScaleFactor();
    }

    public void setStrengthScaleFactor(int strengthScaleFactor) {
        javaComplexify.setStrengthScaleFactor(strengthScaleFactor);
    }

    public int getMinimumChars() {
        return javaComplexify.getMinimumChars();
    }

    public void setMinimumChars(int minimumChars) {
        javaComplexify.setMinimumChars(minimumChars);
    }

    public String[] getBanList() {
        return javaComplexify.getBanList();
    }

    public void setBanList(String[] banList) {
        javaComplexify.setBanList(banList);
    }

    public boolean shouldUseBanList() {
        return javaComplexify.shouldUseBanList();
    }

    public void setShouldUseBanList(boolean shouldUseBanList) {
        javaComplexify.setShouldUseBanList(shouldUseBanList);
    }
}
