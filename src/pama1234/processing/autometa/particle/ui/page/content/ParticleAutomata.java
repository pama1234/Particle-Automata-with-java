package pama1234.processing.autometa.particle.ui.page.content;

import java.util.ArrayList;

import pama1234.processing.Entity;
import pama1234.processing.autometa.particle.ui.Tab;
import pama1234.processing.autometa.particle.ui.component.LoadAndSave;
import pama1234.processing.autometa.particle.ui.component.Scoreboard;
import pama1234.processing.autometa.particle.ui.component.TabCenter;
import pama1234.processing.autometa.particle.ui.component.ToolBar;
import pama1234.processing.autometa.particle.util.Cell;
import pama1234.processing.autometa.particle.util.CellCenter;
import pama1234.processing.autometa.particle.util.MetaCell;
import pama1234.processing.autometa.particle.util.MetaCellCenter;
import pama1234.processing.autometa.particle.util.MetaInfo;
import pama1234.processing.util.app.UtilApp;
import pama1234.processing.util.center.EntityCenter;
import processing.core.PConstants;

public class ParticleAutomata extends EntityCenter<Entity>{
  public final TabCenter tabs;
  public final ToolBar toolBar;
  public final LoadAndSave lsHelper;
  public final Scoreboard scoreboard;
  public final MetaCellCenter metaList;
  public final CellCenter cellList;
  public ParticleAutomata(UtilApp p) {
    super(p);
    MetaCell[] array;
    add.add(tabs=new TabCenter(p,-640,-320));
    tabs.list.add(new Tab<Entity>(p,"元信息",tabs.metaCenter=metaList=new MetaCellCenter(p,-480,-480,480,480)));
    Tab<Entity> tab=new Tab<Entity>(p,"地图",tabs.cellCenter=cellList=new CellCenter(p,metaList));
    tabs.list.add(tab);
    toolBar=new ToolBar(p,tabs,-640,-160);
    tabs.toolBar=toolBar;
    add.add(toolBar);
    lsHelper=new LoadAndSave(p,tabs,-640,160);
    tabs.lsHelper=lsHelper;
    add.add(lsHelper);
    scoreboard=new Scoreboard(p,tabs,0,-360);
    tabs.scoreboard=scoreboard;
    add.add(scoreboard);
    tabs.setSelect(tab);
    tabs.refresh();
    final float[][] rules=new float[][] {
      {0,1,-1,-1,0,0,0,0,0,0,0,1},
      {1,0,1,-1,-1,0,0,0,0,0,0,0},
      {0,1,0,1,-1,-1,0,0,0,0,0,0},
      {0,0,1,0,1,-1,-1,0,0,0,0,0},
      {0,0,0,1,0,1,-1,-1,0,0,0,0},
      {0,0,0,0,1,0,1,-1,-1,0,0,0},
      {0,0,0,0,0,1,0,1,-1,-1,0,0},
      {0,0,0,0,0,0,1,0,1,-1,-1,0},
      {0,0,0,0,0,0,0,1,0,1,-1,-1},
      {-1,0,0,0,0,0,0,0,1,0,1,-1},
      {-1,-1,0,0,0,0,0,0,0,1,0,1},
      {1,-1,-1,0,0,0,0,0,0,0,1,0},};
    for(float[] fs:rules) {
      for(int i=0;i<fs.length;i++) {
        fs[i]*=0.5f;
      }
    }
    array=new MetaCell[rules.length];
    String names="αβγδεζηθικλμ";
    for(int i=0;i<rules.length;i++) {
      String tn=String.valueOf(names.charAt(i));
      ArrayList<MetaInfo> metaInfoList=createMetaInfo(rules[i]);
      int ti=i-1;
      if(ti<0) ti+=metaInfoList.size();
      metaInfoList.get(ti).scoreG=1;
      metaList.add.add(array[i]=new MetaCell(
        p,metaList,
        tn,metaInfoList));
    }
    for(int i=0;i<array.length;i++) array[i].refresh(array.length);
    p.colorMode(PConstants.HSB);
    for(int i=0;i<array.length;i++) array[i].color=p.color(255f/array.length*i,255,255);
    p.colorMode(PConstants.RGB);
    final int size=1<<6;
    for(int i=0;i<array.length;i++) for(int j=0;j<size;j++) cellList.add.add(new Cell(p,cellList,i,p.random(-320,320),p.random(-320,320)));
  }
  private ArrayList<MetaInfo> createMetaInfo(float... in) {
    ArrayList<MetaInfo> out=new ArrayList<MetaInfo>(in.length);
    for(int i=0;i<in.length;i++) {
      MetaInfo info=new MetaInfo(in[i]);
      info.max*=0.8f;
      out.add(info);
    }
    return out;
  }
}
