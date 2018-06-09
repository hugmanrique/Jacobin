package me.hugmanrique.jacobin;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Hugo Manrique
 * @since 10/06/2018
 */
public class BigEndianBinaryReader extends UnsignedBinaryReader {
  public BigEndianBinaryReader(InputStream input) {
    super(input);
  }

  public BigEndianBinaryReader(InputStream input, int bufferSize) {
    super(input, bufferSize);
  }

  public BigEndianBinaryReader(byte[] buffer) throws IOException {
    super(buffer);
  }

  public BigEndianBinaryReader(byte[] buffer, int offset, int length) throws IOException {
    super(buffer, offset, length);
  }

  @Override
  public short readInt16() throws IOException {
    return (short) (((readByte() & 0xFF) << 8)
      | (readByte() & 0xFF));
  }

  @Override
  public int readInt32() throws IOException {
    return ((readByte() & 0xFF) << 24)
      | ((readByte() & 0xFF) << 16)
      | ((readByte() & 0xFF) << 8)
      | (readByte() & 0xFF);
  }

  @Override
  public long readInt64() throws IOException {
    return ((readByte() & 0xFFL) << 56)
      | ((readByte() & 0xFFL) << 48)
      | ((readByte() & 0xFFL) << 40)
      | ((readByte() & 0xFFL) << 32)
      | ((readByte() & 0xFFL) << 24)
      | ((readByte() & 0xFFL) << 16)
      | ((readByte() & 0xFFL) << 8)
      | (readByte() & 0xFFL);
  }
}
