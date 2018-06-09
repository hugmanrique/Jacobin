package me.hugmanrique.jacobin;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Hugo Manrique
 * @since 10/06/2018
 */
abstract class UnsignedBinaryReader extends AbstractBinaryReader {
  UnsignedBinaryReader(InputStream input) {
    super(input);
  }

  UnsignedBinaryReader(InputStream input, int bufferSize) {
    super(input, bufferSize);
  }

  UnsignedBinaryReader(byte[] buffer) throws IOException {
    super(buffer);
  }

  UnsignedBinaryReader(byte[] buffer, int offset, int length) throws IOException {
    super(buffer, offset, length);
  }

  @Override
  public int readUInt16() throws IOException {
    return readInt16() & 0xFFFF;
  }

  @Override
  public long readUInt32() throws IOException {
    return readInt32() & 0xFFFFFFFFL;
  }

  @Override
  public long readUInt64() throws IOException {
    return readInt64() & 0xFFFFFFFFFFFFFFFL;
  }
}
