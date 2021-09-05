package pama1234.processing.autometa.particle.util.math.gpu;

import com.aparapi.Kernel;

public class SetInBoxUpdate extends Kernel{
  public final float[] posX,posY,velX,velY;
  public float x1,y1,x2,y2;
  public SetInBoxUpdate(float x1,float y1,float x2,float y2,float[] posX,float[] posY,float[] velX,float[] velY) {
    this.x1=x1;
    this.y1=y1;
    this.x2=x2;
    this.y2=y2;
    this.posX=posX;
    this.posY=posY;
    this.velX=velX;
    this.velY=velY;
  }
  @Override
  public void run() {
    int i=getGlobalId();
    if(posX[i]<x1) {
      posX[i]=x1;
      velX[i]*=-1;
    }else if(posX[i]>x2) {
      posX[i]=x2;
      velX[i]*=-1;
    }
    if(posY[i]<y1) {
      posY[i]=y1;
      velY[i]*=-1;
    }else if(posY[i]>y2) {
      posY[i]=y2;
      velY[i]*=-1;
    }
  }
}
