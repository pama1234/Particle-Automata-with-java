package pama1234.processing.autometa.particle.with2d.util.math.gpu;

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
    if(!isFinite(posX[i])) posX[i]=(i-getGlobalSize()/2f)/getGlobalSize();
    if(!isFinite(posY[i])) posY[i]=(i-getGlobalSize()/2f)/getGlobalSize();
    if(!isFinite(velX[i])) velX[i]=(i-getGlobalSize()/2f)/getGlobalSize();
    if(!isFinite(velY[i])) velY[i]=(i-getGlobalSize()/2f)/getGlobalSize();
  }
  public boolean isFinite(float f) {
    return abs(f)<=Float.MAX_VALUE;
  }
}