package pama1234.processing.autometa.particle;

import pama1234.processing.Entity;
import pama1234.processing.autometa.particle.util.Cell;
import pama1234.processing.autometa.particle.util.CellCenter;
import pama1234.processing.autometa.particle.util.MetaCell;
import pama1234.processing.autometa.particle.util.MetaCellCenter;
import pama1234.processing.autometa.particle.util.MetaInfo;
import pama1234.processing.util.app.UtilApp;
import pama1234.processing.util.center.EntityCenter;
import processing.core.PConstants;

public class ParticleAutomata extends EntityCenter<Entity>{
  public final MetaCellCenter metaCenter;
  public final CellCenter cellCenter;
  //  public final MetaCell[] array;
  public ParticleAutomata(UtilApp p) {
    super(p);
    MetaCell[] array;
    metaCenter=new MetaCellCenter(p,0,0,0,0);
    add.add(cellCenter=new CellCenter(p,metaCenter));
    final float[][] rules=new float[][] {
      {0,0,0,1,-1},
      {-1,0,0,0,1},
      {1,-1,0,0,0},
      {0,1,0,0,0},
      {0,0,0,-1,0},
    };
    array=new MetaCell[rules.length];
    String names="αβγδεζηθικλμ";
    p.colorMode(PConstants.HSB);
    for(int i=0;i<rules.length;i++) array[i]=new MetaCell(String.valueOf(names.charAt(i)),p.color(255f/array.length*i,255,255));
    metaCenter.cells=array;
    metaCenter.matrix=createMetaInfo(rules);
    p.colorMode(PConstants.RGB);
    final int size=256;
    for(int i=0;i<array.length;i++) for(int j=0;j<size;j++) cellCenter.add.add(new Cell(p,cellCenter,i,p.random(-320,320),p.random(-320,320)));
  }
  private MetaInfo[][] createMetaInfo(float[][] rules) {
    MetaInfo[][] out=new MetaInfo[rules.length][];
    for(int i=0;i<out.length;i++) {
      out[i]=new MetaInfo[rules[i].length];
      for(int j=0;j<out[i].length;j++) {
        MetaInfo info=new MetaInfo(rules[i][j]);
        info.min=0;
        out[i][j]=info;
      }
      int ti=i-1;
      if(ti<0) ti+=out[i].length;
      out[i][ti].scoreG=1;
    }
    return out;
  }
}
