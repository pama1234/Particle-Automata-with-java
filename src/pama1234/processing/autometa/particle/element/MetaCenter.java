package pama1234.processing.autometa.particle.element;

public class MetaCenter{
  public final MetaCellGroup cells;
  public final MetaInfoMatrix matrix;
  public MetaCenter(int size) {
    cells=new MetaCellGroup(size);
    matrix=new MetaInfoMatrix(size);
  }
}
