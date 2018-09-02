package me.hugmanrique.jacobin.util;

/**
 * @author Hugo Manrique
 * @since 02/09/2018
 */
public class Unsigned {
    public static int unsignedInt16(short value) {
        return value & 0xFFFF;
    }

    public static long unsignedInt32(int value) {
        return value & 0xFFFFFFFFL;
    }

    /**
     * Java can handle unsigned longs by using the utils contained in the
     * {@link Long} class such as {@link Long#compareUnsigned(long, long)}
     */
    public static long unsignedInt64(long value) {
        return value;
    }
}
