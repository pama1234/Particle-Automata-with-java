package pama1234.processing.autometa.particle.util.math.gpu;

import com.aparapi.Kernel;

public class RepulsionUpdate extends Kernel{
  public final float[] posX,posY,velX,velY;
  public final int size;
  public float g,dist;
  public RepulsionUpdate(float g,float dist,int size,float[] posX,float[] posY,float[] velX,float[] velY) {
    this.g=g;
    this.dist=dist;
    this.size=size;
    this.posX=posX;
    this.posY=posY;
    this.velX=velX;
    this.velY=velY;
  }
  @Override
  public void run() {
    int i=getGlobalId();
    int j=i/size;
    i%=size;
    if(i==j) return;
    float dx=posX[j]-posX[i];
    float dy=posY[j]-posY[i];
    final float r=mag(dx,dy);
    if(r>dist) return;
    dx/=r;
    dy/=r;
    final float f=-g/r;
    velX[i]+=dx*f;
    velY[i]+=dy*f;
  }
  //  private float dist(final float x1,final float y1,final float x2,final float y2) {
  //    final float dx=x2-x1;
  //    final float dy=y2-y1;
  //    return sqrt(dx*dx+dy*dy);
  //  }
  private float mag(final float x,final float y) {
    return sqrt(x*x+y*y);
  }
}
