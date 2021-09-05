package pama1234.processing.autometa.particle.test;

import com.aparapi.Kernel;
import com.aparapi.Range;

public class GPUTestApp{
  public static void main(String[] args) {
    test(1);
    test(10);
    test(100);
    test(1000);
    test(10000);
  }
  private static void test(int size) {
    float[] inA=new float[size];
    float[] inB=new float[inA.length];
    float[] result=new float[inA.length];
    Kernel kernel=new Kernel() {
      @Override
      public void run() {
        int i=getGlobalId();
        inA[i]=i;
        inB[i]=inB.length-i*2;
        result[i]=inA[i]+inB[i];
      }
    };
    Range range=Range.create(result.length);
    long time=System.currentTimeMillis();
    kernel.execute(range);
    System.out.println(System.currentTimeMillis()-time);
  }
}
