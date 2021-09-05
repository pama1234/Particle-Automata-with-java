package pama1234.processing.autometa.particle.test;

import pama1234.processing.autometa.particle.element.CellGroup;
import pama1234.processing.autometa.particle.util.math.gpu.MultipleTypeForceUpdate;
import pama1234.processing.util.app.UtilApp;

public class MassGroupTestApp extends UtilApp{
  CellGroup group;
  int select=-1;
  int[] colors;
  //---
  //  VideoExport export;
  @Override
  public void settings() {
    super.settings();
    fullScreen();
    noSmooth();
  }
  public void init() {
    //    group=new CellGroup(4000,640);
    int boxR=1280;
    int cellType=64;
    colors=new int[cellType];
    colorMode(HSB);
    for(int i=0;i<colors.length;i++) colors[i]=color((float)i/cellType*255,255,255);
    colorMode(RGB);
    int range=(int)(boxR*8f/cellType);
    int size=range*cellType;
    int[] type=new int[size];
    for(int i=0;i<type.length;i++) type[i]=i/range;
    float[][][] core=new float[cellType][cellType][3];
    for(int i=0;i<core.length;i++) {
      for(int j=0;j<core[i].length;j++) {
        core[i][j][MultipleTypeForceUpdate.G]=random(-CellGroup.SIZE,CellGroup.SIZE);
        core[i][j][MultipleTypeForceUpdate.MIN]=random(0,CellGroup.SIZE*4);
        core[i][j][MultipleTypeForceUpdate.MAX]=random(CellGroup.SIZE*4,CellGroup.SIZE*8);
      }
    }
    group=new CellGroup(size,boxR,type,core);
    for(int i=0;i<group.size;i++) {
      //      group.posX[i]=random(-320,320);
      //      group.posY[i]=random(-320,320);
      //      group.velX[i]=random(-32,32);
      //      group.velY[i]=random(-32,32);
      //---
      group.posX[i]=random(-32,32);
      group.posY[i]=random(-32,32);
    }
    noStroke();
    doBackground=false;
    //---
    //    export=new VideoExport(this);
    //    export.startMovie();
  }
  @Override
  public void update() {
    //    group.update();
  }
  int chunkSize=8;
  @Override
  public void display() {
    //    fill(0x10ffffff);
    fill(0x40000000);
    rect(0,0,width,height);
    fill(255);
    cam.display();
    for(int i=0;i<group.size;i++) {
      fill(colors[group.type[i]]);
      ellipse(group.posX[i],group.posY[i],4,4);
    }
    //---
    //    for(int i=0;i<group.size/6;i++) {
    //      int p=(int)random(0,group.size);
    //      fill(colors[group.type[p]]);
    //      ellipse(group.posX[p],group.posY[p],4,4);
    //    }
    //---
    int tp=group.size/chunkSize*(frameCount%chunkSize),ts=tp+group.size/chunkSize;
    for(int i=tp;i<ts;i++) {
      fill(colors[group.type[i]]);
      ellipse(group.posX[i],group.posY[i],CellGroup.SIZE,CellGroup.SIZE);
    }
    if(select!=-1) {
      noFill();
      stroke(255);
      rect(group.posX[select]-4,group.posY[select]-4,chunkSize,chunkSize);
      fill(255);
      noStroke();
    }
    //    export.saveFrame();
  }
  @Override
  public void mousePressed() {
    if(mouseButton!=LEFT) return;
    for(int i=0;i<group.size;i++) {
      if(dist(group.posX[i],group.posY[i],cam.mouseX,cam.mouseY)<4) {
        select=i;
        return;
      }
    }
    select=-1;
  }
  @Override
  public void mouseDragged() {
    if(select!=-1) {
      group.posX[select]=cam.mouseX;
      group.posY[select]=cam.mouseY;
    }
  }
  @Override
  public void keyPressed() {
    long time=System.currentTimeMillis();
    group.update();
    for(int i=0;i<group.size;i++) if(!Float.isFinite(group.posX[i])||!Float.isFinite(group.posY[i])) println(group.posX[i],group.posY[i]);
    System.out.println(System.currentTimeMillis()-time);
  }
  //  @Override
  //  public void dispose() {
  //    //    export.endMovie();
  //    super.dispose();
  //  }
  public static void main(String[] args) {
    System.setProperty("sun.java2d.uiScale","1");
    new MassGroupTestApp().runSketch();
  }
}
