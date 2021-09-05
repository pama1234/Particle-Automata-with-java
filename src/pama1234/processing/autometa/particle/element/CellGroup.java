package pama1234.processing.autometa.particle.element;

import com.aparapi.Range;
import com.aparapi.exception.CompileFailedException;
import com.aparapi.internal.kernel.KernelManager;

import pama1234.processing.autometa.particle.util.MassPointGroup;
import pama1234.processing.autometa.particle.util.math.gpu.DampingUpdate;
import pama1234.processing.autometa.particle.util.math.gpu.DisplacementUpdate;
import pama1234.processing.autometa.particle.util.math.gpu.MultipleTypeForceUpdate;
import pama1234.processing.autometa.particle.util.math.gpu.RepulsionUpdate;
import pama1234.processing.autometa.particle.util.math.gpu.SetInBoxUpdate;
import pama1234.processing.autometa.particle.util.math.gpu.ToNumberUpdate;

public class CellGroup{
  public static final int SIZE=4,DIST=SIZE*2;
  public final int size;
  public final float[] posX,posY,velX,velY;
  public final int[] type;
  public final float[][][] forceMatrix;
  public final Range r,rSquare;
  public final DisplacementUpdate disCal;
  public final DampingUpdate dampCal;
  public final RepulsionUpdate repulsCal;
  public final SetInBoxUpdate boxUpdate;
  public final ToNumberUpdate toNumUpdate;
  public final MultipleTypeForceUpdate forceUpdate;
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
    velX=new float[size];
    velY=new float[size];
    //---
    r=Range.create(size);
    rSquare=Range.create(size*size);
    disCal=new DisplacementUpdate(posX,posY,velX,velY);
    dampCal=new DampingUpdate(velX,velY,f);
    repulsCal=new RepulsionUpdate(DIST/4,DIST,size,posX,posY,velX,velY);
    boxUpdate=new SetInBoxUpdate(-boxR,-boxR,boxR,boxR,posX,posY,velX,velY);
    toNumUpdate=new ToNumberUpdate(posX,posY,velX,velY);
    forceUpdate=new MultipleTypeForceUpdate(size,posX,posY,velX,velY,type,core);
    //---
    try {
      repulsCal.compile(KernelManager.instance().bestDevice());
      disCal.compile(KernelManager.instance().bestDevice());
      dampCal.compile(KernelManager.instance().bestDevice());
      boxUpdate.compile(KernelManager.instance().bestDevice());
      toNumUpdate.compile(KernelManager.instance().bestDevice());
      forceUpdate.compile(KernelManager.instance().bestDevice());
    }catch(CompileFailedException e) {
      e.printStackTrace();
    }
  }
  public void update() {
    repulsCal.execute(rSquare);
    forceUpdate.execute(rSquare);
    //---
    disCal.execute(r);
    dampCal.execute(r);
    //---
    boxUpdate.execute(r);
    toNumUpdate.execute(r);
  }
  public static void main(String[] args) {
    MassPointGroup group=new MassPointGroup(32);
    while(true) {
      long time=System.currentTimeMillis();
      group.update();
      System.out.println(System.currentTimeMillis()-time);
      try {
        Thread.sleep(1000);
      }catch(InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}