package pama1234.processing.autometa.particle.util;

import java.nio.ByteBuffer;

import pama1234.nio.ByteData;

public class MetaInfo implements ByteData{
  public float g;
  public float min=Cell.dist*3,max=Cell.dist*8;
  public float scoreR=Cell.dist*5,scoreG;
  public MetaInfo(float g) {
    this.g=g;
  }
  public MetaInfo(float g,float scoreG) {
    this.g=g;
    this.scoreG=scoreG;
  }
  public MetaInfo(float g,float min,float max,float scoreR,float scoreG) {
    this.g=g;
    this.min=min;
    this.max=max;
    this.scoreR=scoreR;
    this.scoreG=scoreG;
  }
  @Override
  public void fromData(ByteBuffer in,int offset,int size) {
    g=in.getFloat(offset);
    min=in.getFloat(offset+=ByteData.FLOAT_SIZE);
    max=in.getFloat(offset+=ByteData.FLOAT_SIZE);
  }
  @Override
  public ByteBuffer toData(ByteBuffer in,int offset) {
    in.putFloat(offset,g);
    in.putFloat(offset+=ByteData.FLOAT_SIZE,min);
    in.putFloat(offset+=ByteData.FLOAT_SIZE,max);
    return in;
  }
  @Override
  public int bufferSize() {
    return ByteData.FLOAT_SIZE*3;
  }
}