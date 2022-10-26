package pama1234.processing.autometa.particle.with3d.test;

import pama1234.math.physics.PathVar;
import pama1234.processing.autometa.particle.with2d.util.math.gpu.MultipleTypeForceUpdate;
import pama1234.processing.autometa.particle.with3d.element.CellGroup;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import processing.opengl.PGraphics3D;

public class CellGroupAppWith3D extends PApplet{
  CellGroup group;
  int[] colors;
  float boxR;
  float x,y,z;
  // float h,v=-PI/2f;
  PathVar h=new PathVar(0,0.5f),v=new PathVar(-PI/2f,0.5f);
  //  float desH,desV;
  boolean[] keys=new boolean[6];
  float speed=4;
  boolean stop;
  PGraphics3D g3d;
  static final int UP=0,DOWN=1,LEFT=2,RIGHT=3,FRONT=4,BACK=5;
  @Override
  public void settings() {
    // size(640,640,P3D);
    fullScreen(P3D);
    noSmooth();
  }
  @Override
  public void setup() {
    initFromMiniCore();
    //    randomInit();
    noFill();
    hint(ENABLE_STROKE_PERSPECTIVE);
    // hint(ENABLE_DEPTH_SORT);
    // hint(ENABLE_DEPTH_MASK);
    // hint(ENABLE_DEPTH_TEST);
    // hint(ENABLE_OPTIMIZED_STROKE);
    // blendMode(mode);
    // hint(DISABLE_DEPTH_TEST);
    g3d=(PGraphics3D)g;
  }
  public void randomInit() {
    boxR=40;
    int cellType=64;
    colors=new int[cellType];
    colorMode(HSB);
    for(int i=0;i<colors.length;i++) colors[i]=color((float)i/colors.length*255,0xff,0xff);
    colorMode(RGB);
    int range=(int)(boxR*16/cellType);
    int arraySize=range*cellType;
    int[] type=new int[arraySize];
    for(int i=0;i<type.length;i++) type[i]=i/range;
    float[][][] core=new float[cellType][cellType][3];
    for(int i=0;i<core.length;i++) {
      for(int j=0;j<core[i].length;j++) {
        core[i][j][MultipleTypeForceUpdate.G]=random(-CellGroup.DIST,CellGroup.DIST)/4;
        //        core[i][j][MultipleTypeForceUpdate.G]=random(-CellGroup.DIST,CellGroup.DIST);
        core[i][j][MultipleTypeForceUpdate.MIN]=random(0,CellGroup.DIST*4);
        core[i][j][MultipleTypeForceUpdate.MAX]=random(CellGroup.DIST*4,CellGroup.DIST*8);
      }
    }
    group=new CellGroup(arraySize,boxR,type,core);
    float randR=boxR;
    for(int i=0;i<group.size;i++) {
      group.posX[i]=random(-randR,randR);
      group.posY[i]=random(-randR,randR);
      group.posZ[i]=random(-randR,randR);
      //---
      //      group.velX[i]=random(-32,32);
      //      group.velY[i]=random(-32,32);
    }
  }
  public void initFromMiniCore() {
    // boxR=240;
    // boxR=480;
    boxR=720;
    // boxR=2048;
    // boxR=4096;
    // float[][] miniCore=new float[][] {
    //   {0,1,-1,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,},
    //   {1,0,1,-1,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
    //   {0,1,0,1,-1,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
    //   {0,0,1,0,1,-1,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
    //   {0,0,0,1,0,1,-1,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
    //   {0,0,0,0,1,0,1,-1,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
    //   {0,0,0,0,0,1,0,1,-1,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
    //   {0,0,0,0,0,0,1,0,1,-1,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,},
    //   {0,0,0,0,0,0,0,1,0,1,-1,-1,0,0,0,0,0,0,0,0,0,0,0,0,},
    //   {0,0,0,0,0,0,0,0,1,0,1,-1,-1,0,0,0,0,0,0,0,0,0,0,0,},
    //   {0,0,0,0,0,0,0,0,0,1,0,1,-1,-1,0,0,0,0,0,0,0,0,0,0,},
    //   {0,0,0,0,0,0,0,0,0,0,1,0,1,-1,-1,0,0,0,0,0,0,0,0,0,},
    //   {0,0,0,0,0,0,0,0,0,0,0,1,0,1,-1,-1,0,0,0,0,0,0,0,0,},
    //   {0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,-1,-1,0,0,0,0,0,0,0,},
    //   {0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,-1,-1,0,0,0,0,0,0,},
    //   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,-1,-1,0,0,0,0,0,},
    //   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,-1,-1,0,0,0,0,},
    //   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,-1,-1,0,0,0,},
    //   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,-1,-1,0,0,},
    //   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,-1,-1,0,},
    //   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,-1,-1,},
    //   {-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,-1,},
    //   {-1,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,},
    //   {1,-1,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,},};
    // float[][] miniCore=new float[][] {
    //   {1,0,0,0,0,0,0,0,-1,-1,1,0},
    //   {0,0,0,0,0,0,0,-1,-1,1,0,1},
    //   {0,0,0,0,0,0,-1,-1,1,0,1,0},
    //   {0,0,0,0,0,-1,-1,1,0,1,0,0},
    //   {0,0,0,0,-1,-1,1,0,1,0,0,0},
    //   {0,0,0,-1,-1,1,0,1,0,0,0,0},
    //   {0,0,-1,-1,1,0,1,0,0,0,0,0},
    //   {0,-1,-1,1,0,1,0,0,0,0,0,0},
    //   {-1,-1,1,0,1,0,0,0,0,0,0,0},
    //   {-1,1,0,1,0,0,0,0,0,0,0,-1},
    //   {1,0,1,0,0,0,0,0,0,0,-1,-1},
    //   {0,1,0,0,0,0,0,0,0,-1,-1,1},};
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
        // core[i][j][MultipleTypeForceUpdate.G]=miniCore[i][j]/4;
        // core[i][j][MultipleTypeForceUpdate.G]=miniCore[i][j]/2;
        core[i][j][MultipleTypeForceUpdate.G]=miniCore[i][j];
        // core[i][j][MultipleTypeForceUpdate.MIN]=CellGroup.DIST*20;
        // core[i][j][MultipleTypeForceUpdate.MIN]=CellGroup.DIST/2;
        // core[i][j][MultipleTypeForceUpdate.MIN]=0;
        // core[i][j][MultipleTypeForceUpdate.MAX]=CellGroup.DIST*120;
        // core[i][j][MultipleTypeForceUpdate.MAX]=CellGroup.DIST*300;
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
    // int range=512;
    int arraySize=range*cellType;
    int[] type=new int[arraySize];
    for(int i=0;i<type.length;i++) type[i]=i/range;
    group=new CellGroup(arraySize,boxR,type,core);
    float randR=boxR;
    for(int i=0;i<group.size;i++) {
      // group.posX[i]=sq(random(-randR,randR));
      // group.posY[i]=sq(random(-randR,randR));
      // group.posZ[i]=sq(random(-randR,randR));
      group.posX[i]=random(-randR,randR)/4;
      group.posY[i]=random(-randR,randR)/4;
      group.posZ[i]=random(-randR,randR)/4;
      // group.posX[i]=random(-randR,randR);
      // group.posY[i]=random(-randR,randR);
      // group.posZ[i]=random(-randR,randR);
      //---
      //      group.velX[i]=random(-32,32);
      //      group.velY[i]=random(-32,32);
    }
  }
  @Override
  public void draw() {
    // for(int i=0;i<4;i++)
    if(!stop) group.update();
    if(keys[UP]!=keys[DOWN]) {
      //      if(keys[UP]) y+=speed;
      //      else y-=speed;
      if(keys[UP]) z+=speed;
      else z-=speed;
    }
    h.update();
    v.update();
    float dx=0,dy=0;
    float sinH=sin(h.pos);
    float cosH=cos(h.pos);
    if(keys[LEFT]!=keys[RIGHT]) {
      //      if(keys[LEFT]) x+=speed;
      //      else x-=speed;
      if(keys[LEFT]) {
        dx-=speed*sinH;
        dy-=speed*cosH;
      }else {
        dx+=speed*sinH;
        dy+=speed*cosH;
      }
    }
    if(keys[FRONT]!=keys[BACK]) {
      //      if(keys[FRONT]) z+=speed;
      //      else z-=speed;
      if(keys[FRONT]) {
        dy+=speed*sinH;
        dx-=speed*cosH;
      }else {
        dy-=speed*sinH;
        dx+=speed*cosH;
      }
    }
    x+=dx;
    y+=dy;
    //---
    background(0,128);
    //    translate(x,y,z);
    perspective(g3d.defCameraFOV,g3d.defCameraAspect,1f/16,2048);
    float sinV=sin(v.pos);
    camera(
      x,-z,-y,
      x+cosH*sinV,-z+cos(v.pos),-y+sinH*sinV,
      0,1,0);
    for(int i=0;i<group.size;i++) {
      stroke(colors[group.type[i]]);
      strokeWeight(CellGroup.SIZE);
      point(group.posX[i],group.posY[i],group.posZ[i]);
      //---
      stroke(colors[group.type[i]]&0x40ffffff);
      strokeWeight(CellGroup.DIST);
      point(group.posX[i],group.posY[i],group.posZ[i]);
    }
    stroke(0x80ffffff);
    box(boxR*2);
  }
  @Override
  public void keyPressed() {
    switch(Character.toLowerCase(key)) {
      case 'w':
        keys[FRONT]=true;
        break;
      case 's':
        keys[BACK]=true;
        break;
      case 'a':
        keys[LEFT]=true;
        break;
      case 'd':
        keys[RIGHT]=true;
        break;
      case 'r':
        keys[UP]=true;
        break;
      case 'f':
        keys[DOWN]=true;
        break;
      case ' ':
        keys[UP]=true;
        break;
      case 'o':
        x=0;
        y=0;
        z=0;
        break;
        case 'e':
        stop=!stop;
        break;

    }
    switch(keyCode) {
      case SHIFT:
        keys[DOWN]=true;
        break;
    }
  }
  @Override
  public void keyReleased(KeyEvent event) {
    switch(Character.toLowerCase(key)) {
      case 'w':
        keys[FRONT]=false;
        break;
      case 's':
        keys[BACK]=false;
        break;
      case 'a':
        keys[LEFT]=false;
        break;
      case 'd':
        keys[RIGHT]=false;
        break;
      case 'r':
        keys[UP]=false;
        break;
      case 'f':
        keys[DOWN]=false;
        break;
      case ' ':
        keys[UP]=false;
        break;
    }
    switch(keyCode) {
      case SHIFT:
        keys[DOWN]=false;
        break;
    }
  }
  @Override
  public void mouseDragged() {
    h.des+=(mouseX-pmouseX)/360f;
    v.des+=(mouseY-pmouseY)/360f;
    //---
    if(v.des<=-PI) v.des=0.0001f-PI;
    if(v.des>=0) v.des=-0.0001f;
  }
  @Override
  public void mouseWheel(MouseEvent e) {
    super.mouseWheel(e);
    speed+=e.getCount();
    if(speed<1) speed=1;
    if(speed>32) speed=32;
  }
  public static void main(String[] args) {
    System.setProperty("sun.java2d.uiScale","1");
    new CellGroupAppWith3D().runSketch();
  }
}
