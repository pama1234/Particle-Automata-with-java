package pama1234.processing.autometa.particle.with3d.element;

import com.aparapi.Kernel;

public class CellSquareUpdater extends Kernel{
  public static final int G=0,MIN=1,MAX=2;
  public final float[] posX,posY,posZ,velX,velY,velZ;
  public final float dist,g;
  public final int size;
  public final int[] type;
  public final float[][][] forceMatrix;
  public CellSquareUpdater(
    float[] posX,float[] posY,float[] posZ,
    float[] velX,float[] velY,float[] velZ,
    float dist,float g,
    int size,int[] type,float[][][] forceMatrix) {
    this.posX=posX;
    this.posY=posY;
    this.posZ=posZ;
    this.velX=velX;
    this.velY=velY;
    this.velZ=velZ;
    this.dist=dist;
    this.g=g;
    this.size=size;
    this.type=type;
    this.forceMatrix=forceMatrix;
  }
  @Override
  public void run() {
    //--- 计算弹力与矩阵存储力
    int i=getGlobalId();
    int j=i/size;
    i%=size;
    if(i==j) return;
    float dx=posX[j]-posX[i];
    float dy=posY[j]-posY[i];
    float dz=posZ[j]-posZ[i];
    final float r=mag(dx,dy,dz);
    dx/=r;
    dy/=r;
    dz/=r;
    if(r<dist) {
      float f=-g/r;
      velX[i]+=dx*f;
      velY[i]+=dy*f;
      velZ[i]+=dz*f;
      return;
    }
    //    if(r>dist) {
    if(r>forceMatrix[type[i]][type[j]][MAX]||r<forceMatrix[type[i]][type[j]][MIN]) return;
    //    dx/=r;
    //    dy/=r;
    //    dz/=r;
    float f=forceMatrix[type[i]][type[j]][G]/r;
    velX[i]+=dx*f;
    velY[i]+=dy*f;
    velZ[i]+=dz*f;
    //    }else {
    //      float f=-g/r;
    //      dx/=r;
    //      dy/=r;
    //      dz/=r;
    //      velX[i]+=dx*f;
    //      velY[i]+=dy*f;
    //      velZ[i]+=dz*f;
    //    }
  }
  private float mag(final float x,final float y,final float z) {
    return sqrt(x*x+y*y+z*z);
  }
}
