package pama1234.processing.autometa.particle.with2d.test;

import pama1234.math.Tools;
import pama1234.processing.autometa.particle.with2d.element.CellGroup;
import pama1234.processing.autometa.particle.with2d.util.math.gpu.MultipleTypeForceUpdate;
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
    //    fullScreen();
    noSmooth();
  }
  public void init() {
    initFromMiniCore();
    //    randomInit();
    noStroke();
    doBackground=false;
    //---
    //    export=new VideoExport(this);
    //    export.startMovie();
  }
  public void initFromMiniCore() {
    float boxR=1280;
    float[][] miniCore=new float[][] {
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
    float[][][] core=new float[miniCore.length][miniCore[0].length][3];
    for(int i=0;i<core.length;i++) {
      for(int j=0;j<core[i].length;j++) {
        core[i][j][MultipleTypeForceUpdate.G]=miniCore[i][j];
        core[i][j][MultipleTypeForceUpdate.MIN]=CellGroup.DIST*2;
        core[i][j][MultipleTypeForceUpdate.MAX]=CellGroup.DIST*6;
      }
    }
    int cellType=core.length;
    colors=new int[cellType];
    colorMode(HSB);
    for(int i=0;i<colors.length;i++) colors[i]=color((float)i/cellType*255,0xff,0xff);
    colorMode(RGB);
    int range=1024;
    int arraySize=range*cellType;
    int[] type=new int[arraySize];
    for(int i=0;i<type.length;i++) type[i]=i/range;
    group=new CellGroup(arraySize,boxR,type,core);
    float randR=boxR;
    for(int i=0;i<group.size;i++) {
      group.posX[i]=random(-randR,randR);
      group.posY[i]=random(-randR,randR);
      //---
      //      group.velX[i]=random(-32,32);
      //      group.velY[i]=random(-32,32);
    }
  }
  public void randomInit() {
    float boxR=720;
    int cellType=128;
    colors=new int[cellType];
    colorMode(HSB);
    for(int i=0;i<colors.length;i++) colors[i]=color((float)i/colors.length*255,0xff,0xff);
    colorMode(RGB);
    int range=(int)(boxR*8/cellType);
    int arraySize=range*cellType;
    int[] type=new int[arraySize];
    for(int i=0;i<type.length;i++) type[i]=i/range;
    float[][][] core=new float[cellType][cellType][3];
    for(int i=0;i<core.length;i++) {
      for(int j=0;j<core[i].length;j++) {
        core[i][j][MultipleTypeForceUpdate.G]=random(-CellGroup.DIST,CellGroup.DIST);
        core[i][j][MultipleTypeForceUpdate.MIN]=random(0,CellGroup.DIST*4);
        core[i][j][MultipleTypeForceUpdate.MAX]=random(CellGroup.DIST*4,CellGroup.DIST*8);
      }
    }
    group=new CellGroup(arraySize,boxR,type,core);
    float randR=boxR;
    for(int i=0;i<group.size;i++) {
      group.posX[i]=random(-randR,randR);
      group.posY[i]=random(-randR,randR);
      //---
      //      group.velX[i]=random(-32,32);
      //      group.velY[i]=random(-32,32);
    }
  }
  @Override
  public void update() {
    group.update();
  }
  int chunkSize=8;
  @Override
  public void display() {
    //    fill(0x10ffffff);
    fill(0x40000000);
    rect(0,0,width,height);
    fill(255);
    cam.display();
    float x2=cam.point.pos.x-cam.screenWidth/2;
    float y2=cam.point.pos.y-cam.screenHeight/2;
    for(int i=0;i<group.size;i++) {
      if(!Tools.inBox(
        group.posX[i],
        group.posY[i],
        x2,
        y2,
        cam.screenWidth,
        cam.screenHeight)) continue;
      //      noStroke();
      fill(colors[group.type[i]]);
      ellipse(group.posX[i],group.posY[i],CellGroup.SIZE,CellGroup.SIZE);
      fill(colors[group.type[i]]&0x40ffffff);
      ellipse(group.posX[i],group.posY[i],CellGroup.DIST,CellGroup.DIST);
      //---
      //      stroke(colors[group.type[i]]&0x80ffffff);
      //      line(
      //        group.posX[i],
      //        group.posY[i],
      //        group.posX[i]+group.velX[i],
      //        group.posY[i]+group.velY[i]);
    }
    //---
    //    for(int i=0;i<group.size/6;i++) {
    //      int p=(int)random(0,group.size);
    //      fill(colors[group.type[p]]);
    //      ellipse(group.posX[p],group.posY[p],4,4);
    //    }
    //---
    //    int tp=group.size/chunkSize*(frameCount%chunkSize),ts=tp+group.size/chunkSize;
    //    for(int i=tp;i<ts;i++) {
    //      fill(colors[group.type[i]]);
    //      ellipse(group.posX[i],group.posY[i],CellGroup.SIZE,CellGroup.SIZE);
    //    }
    if(select!=-1) {
      noFill();
      stroke(0x80ffffff);
      rect(group.posX[select]-4,group.posY[select]-4,chunkSize,chunkSize);
      //      fill(255);
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
  //  @Override
  //  public void keyPressed() {
  //    //    long time=System.currentTimeMillis();
  //    //    group.update();
  //    //    for(int i=0;i<group.size;i++) if(!Float.isFinite(group.posX[i])||!Float.isFinite(group.posY[i])) println(group.posX[i],group.posY[i]);
  //    //    System.out.println(System.currentTimeMillis()-time);
  //  }
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
