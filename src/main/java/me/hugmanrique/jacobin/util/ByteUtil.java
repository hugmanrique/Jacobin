package me.hugmanrique.jacobin.util;

/**
 * @author Hugo Manrique
 * @since 09/06/2018
 */
public final class ByteUtil {
  private ByteUtil() {}

  public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

  public static int unsignedInt16(short value) {
    return value & 0xFFFF;
  }

  public static long unsignedInt32(int value) {
    return value & 0xFFFFFFFFL;
  }

  public static long unsignedInt64(long value) {
    return value & 0xFFFFFFFFFFFFFFFL;
  }
}
