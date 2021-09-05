package pama1234.processing.util.element;

import pama1234.math.physics.PathPoint;
import pama1234.math.physics.PathVar;
import pama1234.processing.Entity;
import pama1234.processing.util.app.UtilApp;
import processing.core.PConstants;
import processing.core.PMatrix2D;

public class Cam extends Entity{
  public PathPoint point;
  public PathVar scale;
  public float mouseX,mouseY,pmouseX,pmouseY,smouseX,smouseY,deltaX,deltaY;
  public boolean drag=true,active;
  public int dragButton=PConstants.RIGHT;
  public PMatrix2D matrix;
  public float min=0.25f,max=8f,step=0.25f;
  public Cam(UtilApp p,float x,float y,float z) {
    super(p);
    point=new PathPoint(x,y);
    scale=new PathVar(z);
    matrix=new PMatrix2D();
  }
  public float transX(float in) {
    return (in-p.width/2)/(scale.pos*p.u)+point.pos.x;
  }
  public float transY(float in) {
    return (in-p.height/2)/(scale.pos*p.u)+point.pos.y;
  }
  @Override
  public void create() {}
  @Override
  public void update() {
    if(active) point.des.add(-deltaX,-deltaY);
    //---
    point.update();
    scale.update();
    updateMatrix();
    //---
    pmouseX=transX(p.pmouseX);
    pmouseY=transY(p.pmouseY);
    mouseX=transX(p.mouseX);
    mouseY=transY(p.mouseY);
    deltaX=mouseX-pmouseX;
    deltaY=mouseY-pmouseY;
  }
  @Override
  public void display() {
    p.setMatrix(matrix);
    //    p.resetMatrix();
    //    p.translate(p.width/2,p.height/2);
    //    //---
    //    p.scale(scale.pos);
    //    p.translate(-point.pos.x,-point.pos.y);
  }
  private void updateMatrix() {
    matrix.reset();
    matrix.translate(p.width/2,p.height/2);
    //---
    matrix.scale(scale.pos*p.u);
    matrix.translate(-point.pos.x,-point.pos.y);
  }
  @Override
  public void pause() {}
  @Override
  public void resume() {}
  @Override
  public void dispose() {}
  @Override
  public void mousePressed(final int button) {
    smouseX=transX(p.smouseX);
    smouseY=transY(p.smouseY);
    if(drag) active=(button==dragButton);
  }
  @Override
  public void mouseReleased(final int button) {
    if(drag) active=false;
  }
  @Override
  public void mouseClicked(final int button) {}
  @Override
  public void mouseMoved() {}
  @Override
  public void mouseDragged() {}
  @Override
  public void mouseWheel(final int c) {
    scale.des+=c*step;
    if(scale.des<min) scale.des=min;
    if(scale.des>max) scale.des=max;
  }
  @Override
  public void keyPressed(final char key,final int keyCode) {}
  @Override
  public void keyReleased(final char key,final int keyCode) {}
  @Override
  public void keyTyped(final char key) {}
  @Override
  public void frameResized(final int w,final int h) {}
  @Override
  public void frameMoved(final int x,final int y) {}
}
