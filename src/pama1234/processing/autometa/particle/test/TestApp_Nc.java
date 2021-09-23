package pama1234.processing.autometa.particle.test;

import java.util.ArrayList;

import pama1234.math.vec.Vec2f;
import pama1234.processing.autometa.particle.util.Cell;
import pama1234.processing.autometa.particle.util.CellCenter;
import pama1234.processing.autometa.particle.util.MetaCell;
import pama1234.processing.autometa.particle.util.MetaCellCenter;
import pama1234.processing.autometa.particle.util.MetaInfo;
import pama1234.processing.util.app.UtilApp;

public class TestApp_Nc extends UtilApp{
  MetaCellCenter metaList;
  CellCenter cellList;
  MetaCell[] array;
  Cell camPos;
  public static final int up=0,down=1,left=2,right=3;
  final boolean[] keys=new boolean[4];
  private static final float cellViewSpeed=Cell.dist/32,camViewSpeed=1;
  @Override
  public void settings() {
    super.settings();
    //    fullScreen();
    noSmooth();
  }
  @Override
  public void init() {
    //--- [display config]
    strokeWeight(1);
    noStroke();
    //    noCursor();
    //    background=color(255);
    background=color(0);
    //--- [add entitys to frameWork]
    center.add.add(metaList=new MetaCellCenter(this,-320,-320,320,320));
    center.add.add(cellList=new CellCenter(this,metaList));
    //--- [add meta]
    final float[][] rules=new float[][] {
      //      {0,1,-1,0,0,0,0},
      //      {0,0,1,-1,0,0,0},
      //      {0,0,0,1,-1,0,0},
      //      {0,0,0,0,1,-1,0},
      //      {0,0,0,0,0,1,-1},
      //      {-1,0,0,0,0,0,2},
      //      {0,0,0,0,0,0,0},
      //---
      //      {0,1,-1,0,0,0,0},
      //      {0,0,1,-1,0,0,0},
      //      {0,0,0,1,-1,0,0},
      //      {0,0,0,0,1,-1,0},
      //      {0,0,0,0,0,1,-1},
      //      {-1,0,0,0,0,0,1},
      //      {1,-1,0,0,0,0,0},
      //---
      //      {0,1,-1,0},
      //      {0,0,1,-1},
      //      {-1,0,0,1},
      //      {1,-1,0,0},
      //---
      {0,1,-1},
      {-1,0,1},
      {1,-1,0},
    };
    //--- [set meta infos]
    array=new MetaCell[rules.length];
    for(int i=0;i<rules.length;i++) metaList.add.add(array[i]=new MetaCell(this,metaList,Integer.toString(i),createMetaInfo(rules[i])));
    colorMode(HSB);
    for(int i=0;i<array.length;i++) array[i].color=color(255f/array.length*i,255,255);
    colorMode(RGB);
    //--- [add cells with random pos]
    final int size=1<<7;
    //    System.out.println(size);
    for(int i=0;i<array.length;i++) for(int j=0;j<size;j++) cellList.add.add(new Cell(this,cellList,i,random(-320,320),random(-320,320)));
    //    for(int i=0;i<array.length-1;i++) for(int j=0;j<size;j++) cellList.add.add(new Cell(this,array[i],random(-320,320),random(-320,320)));
    //    for(int j=0;j<8;j++) cellList.add.add(new Cell(this,array[array.length-1],random(-320,320),random(-320,320)));
  }
  private ArrayList<MetaInfo> createMetaInfo(float... in) {
    ArrayList<MetaInfo> out=new ArrayList<MetaInfo>(in.length);
    for(int i=0;i<in.length;i++) {
      MetaInfo info=new MetaInfo(in[i]);
      //      info.min+=random(Cell.dist)*4;
      //      info.max+=random(-Cell.dist,Cell.dist)*4;
      //      info.max*=2;
      out.add(info);
    }
    return out;
  }
  @Override
  public void update() {
    if(camPos!=null) cam.point.des.set(camPos.point.pos);
    final Vec2f vec;
    final float ts;
    if(camPos!=null) {
      vec=camPos.point.vel;
      ts=cellViewSpeed;
    }else {
      vec=cam.point.des;
      ts=camViewSpeed;
    }
    if(keys[left]!=keys[right]) {
      if(keys[left]) vec.x-=ts;
      else vec.x+=ts;
    }
    if(keys[up]!=keys[down]) {
      if(keys[up]) vec.y-=ts;
      else vec.y+=ts;
    }
  }
  @Override
  public void display() {
    cam.display();
    if(camPos!=null) {
      noFill();
      stroke(0x80ffffff);
      final float tx=camPos.point.pos.x,
        ty=camPos.point.pos.y,
        ts_m2=Cell.size*2,
        ts_d2=Cell.size/2,
        ts_d2m3=ts_d2*3;
      ellipse(tx,ty,ts_m2,ts_m2);
      if(keys[up]) {
        line(tx,ty-ts_m2,tx-ts_d2,ty-ts_d2m3);
        line(tx,ty-ts_m2,tx+ts_d2,ty-ts_d2m3);
      }
      if(keys[down]) {
        line(tx,ty+ts_m2,tx-ts_d2,ty+ts_d2m3);
        line(tx,ty+ts_m2,tx+ts_d2,ty+ts_d2m3);
      }
      if(keys[left]) {
        line(tx-ts_m2,ty,tx-ts_d2m3,ty-ts_d2);
        line(tx-ts_m2,ty,tx-ts_d2m3,ty+ts_d2);
      }
      if(keys[right]) {
        line(tx+ts_m2,ty,tx+ts_d2m3,ty-ts_d2);
        line(tx+ts_m2,ty,tx+ts_d2m3,ty+ts_d2);
      }
      noStroke();
    }
    //    else {
    //      noFill();
    //      stroke(0x80ffffff);
    //      final float tx=cam.point.pos.x;
    //      final float ty=cam.point.pos.y;
    //      line(tx+Cell.size,ty,tx-Cell.size,ty);
    //      line(tx,ty+Cell.size,tx,ty-Cell.size);
    //      noStroke();
    //    }
  }
  //  @Override
  //  public void ellipse(float a,float b,float c,float d) {
  //    if(cellList.display) super.ellipse(a,b,c,d);
  //    layer.fill(g.fillColor);
  //    layer.ellipse(a-cellList.x1,b-cellList.y1,layer_cell_size,layer_cell_size);
  //  }
  @Override
  public void background(int rgb) {
    super.background(rgb);
  }
  @Override
  public void mousePressed() {
    if(mouseButton==LEFT) {
      camPos=null;
      for(Cell i:cellList.list) {
        if(dist(i.point.pos.x,i.point.pos.y,cam.mouseX,cam.mouseY)<Cell.size) {
          camPos=i;
          break;
        }
      }
    }
  }
  @Override
  public void keyPressed() {
    key=Character.toLowerCase(key);
    switch(key) {
      case 'w':
        keys[up]=true;
        break;
      case 's':
        keys[down]=true;
        break;
      case 'a':
        keys[left]=true;
        break;
      case 'd':
        keys[right]=true;
        break;
    }
  }
  @Override
  public void keyReleased() {
    key=Character.toLowerCase(key);
    switch(key) {
      case 'w':
        keys[up]=false;
        break;
      case 's':
        keys[down]=false;
        break;
      case 'a':
        keys[left]=false;
        break;
      case 'd':
        keys[right]=false;
        break;
    }
  }
  public static void main(String[] args) {
    System.setProperty("sun.java2d.uiScale","1");
    new TestApp_Nc().runSketch();
  }
}
