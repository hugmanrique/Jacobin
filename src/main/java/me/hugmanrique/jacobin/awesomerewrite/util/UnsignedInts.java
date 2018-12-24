package me.hugmanrique.jacobin.awesomerewrite.util;

import java.math.BigInteger;

/**
 * Utilities to read unsigned numbers.
 *
 * @author Hugo Manrique
 * @since 24/12/2018
 */
public final class UnsignedInts {

    private static final int SHORT_MASK = 0xFFFF;
    private static final long INT_MASK = 0xFFFFFFFFL;

    private UnsignedInts() {}

    /**
     * Zero-extends the {@code short} value to type {@code int}.
     *
     * @param value the signed 16-bit value.
     * @return the unsigned 16-bit value in the range {@code 0}
     *         through {@code 2<sup>16</sup>-1}.
     */
    public static int unsignedInt16(short value) {
        return value & SHORT_MASK;
    }

    /**
     * Zero-extends the {@code int} value to type {@code long}.
     *
     * @param value the signed 32-bit value.
     * @return the unsigned 32-bit value in the range {@code 0}
     *         through {@code 2<sup>32</sup>-1}.
     */
    public static long unsignedInt32(int value) {
        return value & INT_MASK;
    }

    /**
     * Returns a {@link BigInteger} equal to the unsigned value of
     * the argument.
     *
     * @param value the unsigned 64-bit value.
     * @return the unsigned 64-bit value in the range {@code 0}
     *         through {@code 2<sup>64</sup>-1}.
     */
    public static BigInteger unsignedInt64(long value) {
        if (value >= 0L) {
            return BigInteger.valueOf(value);
        }

        int upper = (int) (value >>> 32);
        int lower = (int) value;

        return unsignedInt32ToBigInt(upper)
                .shiftLeft(32)
                .add(unsignedInt32ToBigInt(lower));
    }

    private static BigInteger unsignedInt32ToBigInt(int value) {
        return BigInteger.valueOf(
            unsignedInt32(value)
        );
    }
}
