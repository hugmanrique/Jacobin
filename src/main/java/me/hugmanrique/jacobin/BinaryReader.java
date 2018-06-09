package me.hugmanrique.jacobin;

import java.io.IOException;

/**
 * Reads a data source
 * @author Hugo Manrique
 * @since 09/06/2018
 */
public interface BinaryReader {
  /** Read a {@code byte} from the stream. */
  byte readByte() throws IOException;

  /** Read a fixed size of {@code bytes} from the stream. */
  byte[] readBytes(final int size) throws IOException;

  /** Read a {@code int16} from the stream. */
  short readInt16() throws IOException;

  /** Read a {@code uint16} from the stream. */
  int readUInt16() throws IOException;

  /** Read a {@code int32} from the stream. */
  int readInt32() throws IOException;

  /** Read a {@code uint32} from the stream. */
  long readUInt32() throws IOException;

  /** Read a {@code int64} from the stream. */
  long readInt64() throws IOException;

  /** Read a {@code uint64} from the stream. */
  long readUInt64() throws IOException;

  /** Read a {@code float} from the stream. */
  float readFloat() throws IOException;

  /** Read a {@code double} from the stream. */
  double readDouble() throws IOException;

  /** Read a {@code bool} from the stream. */
  boolean readBool() throws IOException;

  /**
   * Read a {@code string} from the stream. If the stream contains malformed UTF-8,
   * replaces the offending bytes with the standard UTF-8 replacement character.
   */
  String readString() throws IOException;

  /**
   * Reads and discards {@code size} bytes from the stream
   */
  void skipBytes(final int size) throws IOException;

  /**
   * Returns true if the stream has reached the end of the input.
   */
  boolean isAtEnd() throws IOException;

  /**
   * The total bytes read up to the current position.
   */
  int getTotalBytesRead();
}
