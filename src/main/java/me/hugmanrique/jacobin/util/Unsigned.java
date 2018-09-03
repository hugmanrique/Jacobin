package me.hugmanrique.jacobin.util;

/**
 * Unsigned integer utils.
 *
 * @author Hugo Manrique
 * @since 02/09/2018
 */
public class Unsigned {
    /**
     * Returns the two least-significant bits of {@code value} from
     * the zero-extended {@code int16}.
     *
     * @param value a zero-extended 16-bit value
     * @return A value in the range {@code 0} through {@code 65,535}
     */
    public static int unsignedInt16(short value) {
        return value & 0xFFFF;
    }

    /**
     * Returns the four least-significant bits of {@code value} from
     * the zero-extended {@code int32}.
     *
     * @param value a zero-extended 32-bit value
     * @return A value in the range {@code 0} through {@code 4,294,967,295}
     */
    public static long unsignedInt32(int value) {
        return value & 0xFFFFFFFFL;
    }

    /**
     * Returns the eight least-significant bits of {@code value} from
     * the zero-extended {@code int64}.
     *
     * Java can handle unsigned longs by using the utils contained in the
     * {@link Long} class such as {@link Long#compareUnsigned(long, long)}.
     *
     * @param value a zero-extended 64-bit value
     * @return A value in the range {@code 0} through {@code 18,446,744,073,709,551,615}
     */
    public static long unsignedInt64(long value) {
        return value;
    }
}
