package pama1234.math.physics;

import java.nio.ByteBuffer;

import pama1234.nio.ByteData;

public class PathVar implements ByteData{
  public float pos,des;
  public float f;
  {
    f=0.2f;
  }
  public PathVar(float in) {
    pos=des=in;
  }
  public void update() {
    pos+=(des-pos)*f;
  }
  @Override
  public void fromData(ByteBuffer in,int offset,int size) {
    f=in.getFloat(offset);
    pos=in.getFloat(offset+=ByteData.FLOAT_SIZE);
    des=in.getFloat(offset+=ByteData.FLOAT_SIZE);
  }
  @Override
  public ByteBuffer toData(ByteBuffer in,int offset) {
    in.putFloat(offset,f);
    in.putFloat(offset+=ByteData.FLOAT_SIZE,pos);
    in.putFloat(offset+=ByteData.FLOAT_SIZE,des);
    return in;
  }
  @Override
  public int bufferSize() {
    return ByteData.FLOAT_SIZE*3;
  }
}
