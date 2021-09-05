package pama1234.processing.autometa.particle.util.math.gpu;

import com.aparapi.Kernel;

public class MultipleTypeForceUpdate extends Kernel{
  public static final int G=0,MIN=1,MAX=2;
  public final float[] posX,posY,velX,velY;
  public final int size;
  public final int[] type;
  public final float[][][] forceMatrix;
  public MultipleTypeForceUpdate(int size,float[] posX,float[] posY,float[] velX,float[] velY,int[] type,float[][][] forceMatrix) {
    this.size=size;
    this.posX=posX;
    this.posY=posY;
    this.velX=velX;
    this.velY=velY;
    this.type=type;
    this.forceMatrix=forceMatrix;
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
    if(r>forceMatrix[type[i]][type[j]][MAX]||r<forceMatrix[type[i]][type[j]][MIN]) return;
    dx/=r;
    dy/=r;
    final float f=forceMatrix[type[i]][type[j]][G]/r;
    velX[i]+=dx*f;
    velY[i]+=dy*f;
  }
  private float mag(final float x,final float y) {
    return sqrt(x*x+y*y);
  }
}
