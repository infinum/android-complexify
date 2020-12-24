package co.infinum.complexify;

/**
 * Callback listener
 * <p>
 * Created by Damian Marusic on 28.07.15..
 */
public interface ComplexityListener {

    /**
     * Method is called when password complexity calculation is done.
     *
     * @param isValid    true if password is valid, false otherwise
     * @param complexity Number form range [0.0, 100.0] where greater number represents greater complexity
     */
    void onSuccess(boolean isValid, double complexity);
}
