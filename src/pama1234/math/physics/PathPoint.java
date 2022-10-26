package pama1234.math.physics;

import java.nio.ByteBuffer;

import pama1234.math.vec.Vec2f;
import pama1234.nio.ByteData;

public class PathPoint implements ByteData{
  public Vec2f pos,des;
  public float f;
  {
    f=0.2f;
  }
  public PathPoint(Vec2f pos,Vec2f des) {
    this.pos=pos;
    this.des=des;
  }
  public PathPoint(Vec2f pos) {
    des=new Vec2f();
    this.pos=pos;
  }
  public PathPoint(float a,float b,Vec2f des) {
    pos=new Vec2f(a,b);
    this.des=des;
  }
  public PathPoint(float a,float b) {
    pos=new Vec2f(a,b);
    des=new Vec2f(pos);
  }
  public PathPoint(float a,float b,float c,float d) {
    pos=new Vec2f(a,b);
    des=new Vec2f(c,d);
  }
  public void update() {
    pos.add((des.x-pos.x)*f,(des.y-pos.y)*f);
  }
  @Override
  public void fromData(ByteBuffer in,int offset,int size) {
    f=in.getFloat(offset);
    pos.fromData(in,offset+=ByteData.FLOAT_SIZE,offset+=pos.bufferSize());
    des.fromData(in,offset,offset+=des.bufferSize());
  }
  @Override
  public ByteBuffer toData(ByteBuffer in,int offset) {
    in.putFloat(offset,f);
    pos.toData(in,offset+=ByteData.FLOAT_SIZE);
    des.toData(in,offset+=pos.bufferSize());
    return in;
  }
  @Override
  public int bufferSize() {
    return pos.bufferSize()+des.bufferSize()+ByteData.FLOAT_SIZE;
  }
}
