package pama1234.processing.autometa.particle.test;

import java.util.ArrayList;

import pama1234.processing.autometa.particle.ui.Tab;
import pama1234.processing.autometa.particle.ui.component.Info;
import pama1234.processing.autometa.particle.ui.component.TabCenter;
import pama1234.processing.autometa.particle.ui.component.ToolBar;
import pama1234.processing.autometa.particle.util.Cell;
import pama1234.processing.autometa.particle.util.CellCenter;
import pama1234.processing.autometa.particle.util.MetaCell;
import pama1234.processing.autometa.particle.util.MetaCellCenter;
import pama1234.processing.autometa.particle.util.MetaInfo;
import pama1234.processing.util.Entity;
import pama1234.processing.util.app.UtilApp;

public class TestApp_NcTabs extends UtilApp{
  TabCenter tabs;
  Info info;
  ToolBar toolBar;
  @Override
  public void settings() {
    super.settings();
    noSmooth();
  }
  @Override
  public void init() {
    MetaCellCenter metaList;
    CellCenter cellList;
    MetaCell[] array;
    //--- [display config]
    strokeWeight(Cell.size/4);
    noStroke();
    background=color(0);
    //--- [add entitys to frameWork]
    center.add.add(tabs=new TabCenter(this,-640,-320));
    tabs.list.add(new Tab<Entity>(this,"元信息",tabs.metaCenter=metaList=new MetaCellCenter(this,-480,-480,480,480)));
    Tab<Entity> tab=new Tab<Entity>(this,"地图",tabs.cellCenter=cellList=new CellCenter(this,metaList));
    tabs.list.add(tab);
    toolBar=new ToolBar(this,tabs,-640,-160);
    tabs.toolBar=toolBar;
    center.add.add(toolBar);
    tabs.setSelect(tab);
    tabs.refresh();
    //--- [add meta]
    final float[][] rules=new float[][] {
      //      {0,1,-1,-1,0,0,0,1},
      //      {1,0,1,-1,-1,0,0,0},
      //      {0,1,0,1,-1,-1,0,0},
      //      {0,0,1,0,1,-1,-1,0},
      //      {0,0,0,1,0,1,-1,-1},
      //      {-1,0,0,0,1,0,1,-1},
      //      {-1,-1,0,0,0,1,0,1},
      //      {1,-1,-1,0,0,0,1,0},
      //---
      {0,1,-1},
      {-1,0,1},
      {1,-1,0},
      //---
      //      {0,1,-1,-1,0,0,0,0,0,0,0,1},
      //      {1,0,1,-1,-1,0,0,0,0,0,0,0},
      //      {0,1,0,1,-1,-1,0,0,0,0,0,0},
      //      {0,0,1,0,1,-1,-1,0,0,0,0,0},
      //      {0,0,0,1,0,1,-1,-1,0,0,0,0},
      //      {0,0,0,0,1,0,1,-1,-1,0,0,0},
      //      {0,0,0,0,0,1,0,1,-1,-1,0,0},
      //      {0,0,0,0,0,0,1,0,1,-1,-1,0},
      //      {0,0,0,0,0,0,0,1,0,1,-1,-1},
      //      {-1,0,0,0,0,0,0,0,1,0,1,-1},
      //      {-1,-1,0,0,0,0,0,0,0,1,0,1},
      //      {1,-1,-1,0,0,0,0,0,0,0,1,0},
    };
    //--- [set meta infos]
    array=new MetaCell[rules.length];
    String names="αβγδεζηθικλμ";
    for(int i=0;i<rules.length;i++) {
      String tn=String.valueOf(names.charAt(i));
      metaList.add.add(array[i]=new MetaCell(
        this,metaList,
        tn,createMetaInfo(rules[i])));
    }
    for(int i=0;i<array.length;i++) array[i].refresh(array.length);
    colorMode(HSB);
    for(int i=0;i<array.length;i++) array[i].color=color(255f/array.length*i,255,255);
    colorMode(RGB);
    //--- [add cells with random pos]
    //    final int size=1<<6;
    final int size=1<<7;
    for(int i=0;i<array.length;i++) for(int j=0;j<size;j++) cellList.add.add(new Cell(this,cellList,i,random(-320,320),random(-320,320)));
    //--- [add "Info"]
    center.add.add(info=new Info(this,520,-320));
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
  public static void main(String[] args) {
    System.setProperty("sun.java2d.uiScale","1");
    new TestApp_NcTabs().runSketch();
  }
}
