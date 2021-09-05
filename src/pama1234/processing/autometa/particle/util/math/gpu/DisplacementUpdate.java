package pama1234.processing.autometa.particle.util.math.gpu;

import com.aparapi.Kernel;

public class DisplacementUpdate extends Kernel{
  public final float[] posX,posY,velX,velY;
  public DisplacementUpdate(float[] posX,float[] posY,float[] velX,float[] velY) {
    this.posX=posX;
    this.posY=posY;
    this.velX=velX;
    this.velY=velY;
  }
  @Override
  public void run() {
    int i=getGlobalId();
    posX[i]+=velX[i];
    posY[i]+=velY[i];
  }
}