package pama1234.processing.autometa.particle.ui;

import pama1234.processing.autometa.particle.util.MetaCellCenter;
import pama1234.processing.util.Entity;
import pama1234.processing.util.app.UtilApp;

public class Tab<T extends Entity>extends Entity{
  public String name;
  public T e;
  public boolean update=true;
  public Tab(UtilApp p,String name,T e) {
    super(p);
    this.name=name;
    this.e=e;
  }
  public Tab(UtilApp p,String name2,MetaCellCenter metaCellCenter) {
    super(p);
  }
  @Override
  public void create() {
    e.create();
  }
  @Override
  public void update() {
    if(update) e.update();
  }
  @Override
  public void display() {
    e.display();
  }
  @Override
  public void pause() {
    e.pause();
  }
  @Override
  public void resume() {
    e.resume();
  }
  @Override
  public void dispose() {
    e.dispose();
  }
  @Override
  public void mousePressed(int button) {
    e.mousePressed(button);
  }
  @Override
  public void mouseReleased(int button) {
    e.mouseReleased(button);
  }
  @Override
  public void mouseClicked(int button) {
    e.mouseClicked(button);
  }
  @Override
  public void mouseMoved() {
    e.mouseMoved();
  }
  @Override
  public void mouseDragged() {
    e.mouseDragged();
  }
  @Override
  public void mouseWheel(int c) {
    e.mouseWheel(c);
  }
  @Override
  public void keyPressed(char key,int keyCode) {
    e.keyPressed(key,keyCode);
  }
  @Override
  public void keyReleased(char key,int keyCode) {
    e.keyReleased(key,keyCode);
  }
  @Override
  public void keyTyped(char key) {
    e.keyTyped(key);
  }
  @Override
  public void frameResized(int w,int h) {
    e.frameResized(w,h);
  }
  @Override
  public void frameMoved(int x,int y) {
    e.frameMoved(x,y);
  }
}
