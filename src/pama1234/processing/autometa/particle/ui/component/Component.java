package pama1234.processing.autometa.particle.ui.component;

import pama1234.math.physics.PathPoint;
import pama1234.processing.util.Entity;
import pama1234.processing.util.app.UtilApp;
import processing.core.PGraphics;

public abstract class Component extends Entity{
  public PGraphics layer;
  public PathPoint point;
  public Component(UtilApp p,float x,float y,int w,int h) {
    super(p);
    point=new PathPoint(0,0,x,y);
    layer=p.createGraphics(w,h);
  }
  public abstract void drawLayer();
  public abstract void refresh();
  @Override
  public void update() {
    point.update();
  }
  @Override
  public void display() {
    p.image(layer,point.pos.x,point.pos.y);
  }
}
