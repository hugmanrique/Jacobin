package me.hugmanrique.jacobin.util;

/**
 * @author Hugo Manrique
 * @since 09/06/2018
 */
public final class ByteExceptions {
  private ByteExceptions() {}

  public static IllegalArgumentException NEGATIVE_SIZE = new IllegalArgumentException("BinaryReader encountered an embedded string with negative size.");

  public static IllegalStateException TRUNCATED_DATA = new IllegalStateException("Input ended unexpectedly in the middle of a field.");
}
