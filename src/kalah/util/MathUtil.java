package kalah.util;

import static java.lang.Math.*;

/**
 * Contains utility methods for mathematical functions.
 */
public class MathUtil {

    /**
     * Gets the length of a number i.e. for a value = 10, the length is 2.
     * @param value
     * @return the length of a number
     */
    public static int getNumberLength(int value) {
        if (value  == 0) {
            return 1;
        } else {
            return (int) log10(abs(value)) + 1;
        }
    }

    /**
     * Check if 'a' is the same as 'b'.
     * @param a
     * @param b
     * @return true or false
     */
    public static boolean checkParity(int a, int b) {
        return ((a ^ b) & 1) == 0;
    }

}
