package pama1234.nio;

import java.nio.ByteBuffer;

public interface ByteDataFactory<T extends ByteData>extends DataFactory<ByteBuffer,T>{
  @Override
  ByteBuffer save(T in);
  @Override
  T load(ByteBuffer in);
  @Override
  ByteBuffer saveTo(T in,ByteBuffer data);
  @Override
  T loadTo(ByteBuffer in,T element);
}
