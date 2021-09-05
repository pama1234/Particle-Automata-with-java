package pama1234.processing.autometa.particle.util.math.gpu;

import com.aparapi.Kernel;

public class DampingUpdate extends Kernel{
  public final float[] velX,velY;
  public float f;
  public DampingUpdate(float[] velX,float[] velY,float f) {
    this.velX=velX;
    this.velY=velY;
    this.f=f;
  }
  @Override
  public void run() {
    int i=getGlobalId();
    velX[i]*=f;
    velY[i]*=f;
  }
}