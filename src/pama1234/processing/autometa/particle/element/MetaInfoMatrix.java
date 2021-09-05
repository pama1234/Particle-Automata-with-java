package pama1234.processing.autometa.particle.element;

public class MetaInfoMatrix{
  public final float g[][],min[][],max[][];
  public MetaInfoMatrix(int size) {
    g=new float[size][size];
    min=new float[size][size];
    max=new float[size][size];
  }
}
