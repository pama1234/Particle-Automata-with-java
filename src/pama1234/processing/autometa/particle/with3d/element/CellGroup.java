package pama1234.processing.autometa.particle.with3d.element;

import com.aparapi.Range;
import com.aparapi.exception.CompileFailedException;
import com.aparapi.internal.kernel.KernelManager;

public class CellGroup{
  public static final int SIZE=2,DIST=SIZE*2;
  public final int size;
  public final float[] posX,posY,posZ,velX,velY,velZ;
  public final int[] type;
  public final float[][][] forceMatrix;
  public final Range r,rSquare;
  public final CellUpdater updater;
  public final CellSquareUpdater squareUpdater;
  public CellGroup(int size,float boxR,int[] type,float[][][] core) {
    this(0.8f,boxR,size,type,core);
  }
  public CellGroup(int size,int[] type,float[][][] core) {
    this(0.8f,160,size,type,core);
  }
  public CellGroup(float f,float boxR,int size,int[] type,float[][][] core) {
    this.size=size;
    this.type=type;
    this.forceMatrix=core;
    //---
    posX=new float[size];
    posY=new float[size];
    posZ=new float[size];
    //--
    velX=new float[size];
    velY=new float[size];
    velZ=new float[size];
    //---
    r=Range.create(size);
    rSquare=Range.create(size*size);
    //---
    updater=new CellUpdater(
      posX,posY,posZ,
      velX,velY,velZ,
      f,
      -boxR,-boxR,-boxR,
      boxR,boxR,boxR);
    squareUpdater=new CellSquareUpdater(
      posX,posY,posZ,
      velX,velY,velZ,
      DIST,DIST/4,
      size,type,core);
    //---
    try {
      updater.compile(KernelManager.instance().bestDevice());
      squareUpdater.compile(KernelManager.instance().bestDevice());
    }catch(CompileFailedException e) {
      e.printStackTrace();
    }
  }
  public void update() {
    squareUpdater.execute(rSquare);
    updater.execute(r);
  }
}