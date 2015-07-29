package co.infinum.damianmarusic.complexify_android;

/**
 * Callback listener
 *
 * Created by Damian Marusic on 29.07.15..
 */
public interface ComplexifyListener {

	/**
	 * Method is called when password complexity calculation is done.
	 *
	 * @param isValid true if password is valid, false otherwise
	 * @param complexity Number form range [0.0, 100.0] where greater number represents greater complexity
	 */
	void onSuccess(boolean isValid, double complexity);
}
