package kalah.util;

/**
 * Contains utility methods for formatting strings.
 */
public class StringUtil {

    /**
     * Concatenates the same string 'n' number of times, where 'n' is repeatAmount.
     * @param value
     * @param repeatAmount
     * @return String
     */
    public static String repeatString(String value, int repeatAmount) {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        while (count < repeatAmount) {
            sb.append(value);
            ++count;
        }
        return sb.toString();
    }

    /**
     * Aligns a integer value to the right by adding spacing depending on the length of a number.
     * @param value
     * @param numberLength
     * @return String
     */
    public static String rightAlignIntValue(int value, int numberLength) {
        StringBuilder sb = new StringBuilder(value + "");
        while (sb.length() < numberLength) {
            sb.insert(0, " ");
        }
        return sb.toString();
    }

}
