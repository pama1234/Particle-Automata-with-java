package pama1234.processing.autometa.particle.util;

import com.aparapi.Range;

import pama1234.processing.autometa.particle.util.math.gpu.DampingUpdate;
import pama1234.processing.autometa.particle.util.math.gpu.DisplacementUpdate;

public class MassPointGroup{
  public final int size;
  public final float[] posX,posY,velX,velY;
  public final Range r;
  public final DisplacementUpdate disCal;
  public final DampingUpdate dampCal;
  //  public final LinkedList<Kernel> kernels=new LinkedList<Kernel>();
  public MassPointGroup(int size) {
    this(0.8f,size);
  }
  public MassPointGroup(float f,int size) {
    this.size=size;
    //---
    posX=new float[size];
    posY=new float[size];
    velX=new float[size];
    velY=new float[size];
    //---
    r=Range.create(size);
    //    kernels.add(disCal=new DisplacementUpdate(posX,posY,velX,velY));
    //    kernels.add(dampCal=new DampingUpdate(posX,posY,f));
    disCal=new DisplacementUpdate(posX,posY,velX,velY);
    dampCal=new DampingUpdate(velX,velY,f);
  }
  public void update() {
    //    for(Kernel i:kernels) i.execute(r);
    disCal.execute(r);
    dampCal.execute(r);
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
