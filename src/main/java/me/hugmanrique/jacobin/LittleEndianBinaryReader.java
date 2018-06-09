package me.hugmanrique.jacobin;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Hugo Manrique
 * @since 09/06/2018
 */
public class LittleEndianBinaryReader extends UnsignedBinaryReader {
  public LittleEndianBinaryReader(InputStream input) {
    super(input);
  }

  public LittleEndianBinaryReader(InputStream input, int bufferSize) {
    super(input, bufferSize);
  }

  public LittleEndianBinaryReader(byte[] buffer) throws IOException {
    super(buffer);
  }

  public LittleEndianBinaryReader(byte[] buffer, int offset, int length) throws IOException {
    super(buffer, offset, length);
  }

  @Override
  public short readInt16() throws IOException {
    return (short) (((readByte() & 0xFF))
      | ((readByte() & 0xFF) << 8));
  }

  @Override
  public int readInt32() throws IOException {
    return ((readByte() & 0xFF))
      | ((readByte() & 0xFF) << 8)
      | ((readByte() & 0xFF) << 16)
      | ((readByte() & 0xFF) << 24);
  }

  @Override
  public long readInt64() throws IOException {
    return ((readByte() & 0xFFL)
      | ((readByte() & 0xFFL) << 8)
      | ((readByte() & 0xFFL) << 16)
      | ((readByte() & 0xFFL) << 24)
      | ((readByte() & 0xFFL) << 32)
      | ((readByte() & 0xFFL) << 40)
      | ((readByte() & 0xFFL) << 48)
      | ((readByte() & 0xFFL) << 56));
  }
}
