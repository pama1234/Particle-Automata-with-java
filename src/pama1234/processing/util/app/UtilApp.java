package pama1234.processing.util.app;

import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import pama1234.math.vec.Vec2i;
import pama1234.processing.Entity;
import pama1234.processing.util.center.EntityCenter;
import pama1234.processing.util.element.Cam;
import processing.awt.PSurfaceAWT.SmoothCanvas;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class UtilApp extends PApplet{
  public class MovedListener extends ComponentAdapter{
    @Override
    public void componentMoved(ComponentEvent e) {
      Insets tempInsets=frame.getInsets();
      Point tempLocation=frame.getLocation();
      framePos.set(
        tempInsets.left+tempLocation.x,
        tempInsets.top+tempLocation.y);
      moved++;
    }
  }
  public class PFocusListener implements FocusListener{
    @Override
    public void focusLost(FocusEvent e) {
      focusedLost();
    }
    @Override
    public void focusGained(FocusEvent e) {
      focusedGained();
    }
  }
  public class PResizedListener extends ComponentAdapter{
    @Override
    public void componentResized(ComponentEvent e) {
      resized++;
    }
  }
  public SmoothCanvas canvas;
  public JFrame frame;
  public final Vec2i framePos=new Vec2i();
  public int unitFrameSize;
  public float u,scale=640;
  public boolean shift,ctrl,alt;
  public int smouseX,smouseY,deltaX,deltaY;
  public EntityCenter<Entity> center=new EntityCenter<Entity>(this);
  public Cam cam;
  public int background=0xffffffff;
  public boolean doBackground=true;
  public ArrayList<PGraphics> layers=new ArrayList<PGraphics>();
  public PFont font;
  public int state;
  public String fontPath="data/font/unifont-13.0.06.ttf";
  public boolean loadFont;
  private int resized,moved;
  @Override
  public void settings() {
    unitFrameSize=Math.min(displayWidth,displayHeight)/3*2;
    size(unitFrameSize,unitFrameSize);
  }
  @Override
  public void setup() {
    surface.setResizable(true);
    canvas=(SmoothCanvas)surface.getNative();
    canvas.addComponentListener(new PResizedListener());
    canvas.addFocusListener(new PFocusListener());
    frame=(JFrame)canvas.getFrame();
    frame.addComponentListener(new MovedListener());
    Insets tempInsets=frame.getInsets();
    Point tempLocation=frame.getLocation();
    cam=new Cam(this,0,0,1);
    center.list.add(cam);
    if(loadFont) font=createFont(fontPath,32);
    else font=new PFont(new Font(Font.MONOSPACED,Font.PLAIN,32),true);
    textFont(font);
    textAlign(LEFT,TOP);
    textSize(32);
    strokeWeight(2);
    framePos.set(
      tempInsets.left+tempLocation.x,
      tempInsets.top+tempLocation.y);
    preFrameMoved(
      tempInsets.left+tempLocation.x,
      tempInsets.top+tempLocation.y);
    preFrameResized(width,height);
    init();
    center.refresh();
    frameMoved(
      tempInsets.left+tempLocation.x,
      tempInsets.top+tempLocation.y);
    frameResized(width,height);
  }
  private void preFrameMoved(int x,int y) {
    framePos.set(x,y);
  }
  private void preFrameResized(int w,int h) {
    u=Math.max(1,Math.min(w,h))/scale;
    //    strokeWeight(2/u);
    //    textSize(32/u);
    for(int i=0;i<layers.size();i++) {
      layers.get(i).dispose();
      PGraphics v=createGraphics(w,h);
      layers.set(i,v);
      initLayer(v);
    }
  }
  public void init() {}
  @Override
  public synchronized void draw() {
    deltaX=mouseX-pmouseX;
    deltaY=mouseY-pmouseY;
    if(resized>0) {
      resized--;
      for(int i=0;i<layers.size();i++) {
        layers.get(i).dispose();
        PGraphics v=createGraphics(width,height);
        layers.set(i,v);
        initLayer(v);
      }
      frameResized(width,height);
    }
    if(moved>0) {
      moved--;
      frameMoved(framePos.x,framePos.y);
    }
    center.refresh();
    center.update();
    update();
    if(doBackground) background(background);
    for(int i=0;i<layers.size();i++) {
      PGraphics v=layer(i);
      v.beginDraw();
      v.clear();
    }
    center.display();
    resetMatrix();
    for(int i=0;i<layers.size();i++) {
      PGraphics v=layer(i);
      v.endDraw();
      image(v,0,0);
    }
    //    cam.display();
    display();
  }
  public void display() {}
  public void update() {}
  public PGraphics createLayer(int pos) {
    PGraphics in=createGraphics(width,height);
    initLayer(in);
    layers.add(pos,in);
    return in;
  }
  private void initLayer(PGraphics in) {
    in.beginDraw();
    in.textFont(font);
    in.strokeWeight(g.strokeWeight);
    in.textSize(g.textSize);
    in.textLeading(g.textLeading);
    in.textAlign(LEFT,TOP);
    in.endDraw();
  }
  public void removeLayer(int pos) {
    layers.remove(pos).dispose();
  }
  public PGraphics layer(int pos) {
    return layers.get(pos);
  }
  public void focusedGained() {}
  public void focusedLost() {}
  @Override
  public void frameResized(int w,int h) {
    u=Math.max(1,Math.min(w,h))/scale;
    //    strokeWeight(u/strokeScale);
    //    textSize(32/u);
    center.frameResized(w,h);
  }
  @Override
  public void frameMoved(int x,int y) {
    center.frameMoved(x,y);
  }
  @Override
  public void mousePressed(MouseEvent event) {
    smouseX=mouseX;
    smouseY=mouseY;
    center.mousePressed(mouseButton);
    super.mousePressed(event);
  }
  @Override
  public void mouseReleased(MouseEvent event) {
    center.mouseReleased(mouseButton);
    super.mouseReleased(event);
  }
  @Override
  public void mouseClicked(MouseEvent event) {
    super.mouseClicked(event);
    center.mouseClicked(mouseButton);
  }
  @Override
  public void mouseMoved(MouseEvent event) {
    super.mouseMoved(event);
    center.mouseMoved();
  }
  @Override
  public void mouseDragged(MouseEvent event) {
    super.mouseDragged(event);
    center.mouseDragged();
  }
  @Override
  public void mouseWheel(MouseEvent e) {
    super.mouseWheel(e);
    center.mouseWheel(e.getCount());
  }
  @Override
  public void keyPressed(KeyEvent event) {
    switch(keyCode) {
      case CONTROL: {
        ctrl=true;
      }
        break;
      case SHIFT: {
        shift=true;
      }
        break;
      case ALT: {
        alt=true;
      }
        break;
    }
    center.keyPressed(key,keyCode);
    super.keyPressed(event);
  }
  @Override
  public void keyReleased(KeyEvent event) {
    switch(keyCode) {
      case CONTROL: {
        ctrl=false;
      }
        break;
      case SHIFT: {
        shift=false;
      }
        break;
      case ALT: {
        alt=false;
      }
        break;
    }
    center.keyReleased(key,keyCode);
    super.keyReleased(event);
  }
  @Override
  public void keyTyped(KeyEvent event) {
    super.keyTyped(event);
    center.keyTyped(key);
  }
  @Override
  public void textSize(float size) {
    super.textSize(size);
    textLeading(size);
  }
  @Override
  public void exitActual() {
    center.dispose();
    super.exitActual();
  }
  public static void main(String[] args) {
    System.setProperty("sun.java2d.uiScale","1");
    new UtilApp() {
      @Override
      public void draw() {
        super.draw();
        cam.display();
        rect(0,0,32,32);
      }
    }.runSketch();
  }
}
