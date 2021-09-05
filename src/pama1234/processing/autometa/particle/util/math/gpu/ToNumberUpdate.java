package pama1234.processing.autometa.particle.util.math.gpu;

import com.aparapi.Kernel;

public class ToNumberUpdate extends Kernel{
  public final float[] posX,posY,velX,velY;
  public ToNumberUpdate(float[] posX,float[] posY,float[] velX,float[] velY) {
    this.posX=posX;
    this.posY=posY;
    this.velX=velX;
    this.velY=velY;
  }
  @Override
  public void run() {
    int i=getGlobalId();
    if(!isFinite(posX[i])) posX[i]=0;
    if(!isFinite(posY[i])) posY[i]=0;
    if(!isFinite(velX[i])) velX[i]=0;
    if(!isFinite(velY[i])) velY[i]=0;
  }
  public boolean isFinite(float f) {
    return abs(f)<=Float.MAX_VALUE;
  }
}