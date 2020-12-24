package co.infinum.complexify;

/**
 * Enum for banMode.
 * <p>
 * STRICT - don't allow a substring of a banned password
 * LOOSE - only ban exact matches
 * <p>
 * Created by Damian Marusic on 28.07.15..
 */
public enum ComplexifyBanMode {
    STRICT,
    LOOSE
}
