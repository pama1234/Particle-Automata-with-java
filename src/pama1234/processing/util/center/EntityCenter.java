package pama1234.processing.util.center;

import java.util.LinkedList;

import pama1234.processing.Entity;
import pama1234.processing.util.app.UtilApp;
import pama1234.processing.util.listener.EntityListener;

public class EntityCenter<T extends EntityListener>extends Entity{
  public final LinkedList<T> list=new LinkedList<T>(),
    add=new LinkedList<T>(),
    remove=new LinkedList<T>();
  public EntityCenter(UtilApp p) {
    super(p);
  }
  public void refresh() {
    list.addAll(add);
    add.clear();
    list.removeAll(remove);
    remove.clear();
  }
  @Override
  public void create() {
    for(T e:list) e.create();
  }
  @Override
  public void update() {
    refresh();
    for(T e:list) e.update();
  }
  @Override
  public void display() {
    for(T e:list) e.display();
  }
  @Override
  public void pause() {
    for(T e:list) e.pause();
  }
  @Override
  public void resume() {
    for(T e:list) e.resume();
  }
  @Override
  public void dispose() {
    for(T e:list) e.dispose();
  }
  @Override
  public void mousePressed(final int button) {
    for(T e:list) e.mousePressed(button);
  }
  @Override
  public void mouseReleased(final int button) {
    for(T e:list) e.mouseReleased(button);
  }
  @Override
  public void mouseClicked(final int button) {
    for(T e:list) e.mouseClicked(button);
  }
  @Override
  public void mouseMoved() {
    for(T e:list) e.mouseMoved();
  }
  @Override
  public void mouseDragged() {
    for(T e:list) e.mouseDragged();
  }
  @Override
  public void mouseWheel(final int c) {
    for(T e:list) e.mouseWheel(c);
  }
  @Override
  public void keyPressed(final char key,final int keyCode) {
    for(T e:list) e.keyPressed(key,keyCode);
  }
  @Override
  public void keyReleased(final char key,final int keyCode) {
    for(T e:list) e.keyReleased(key,keyCode);
  }
  @Override
  public void keyTyped(final char key) {
    for(T e:list) e.keyTyped(key);
  }
  @Override
  public void frameResized(final int w,final int h) {
    for(T e:list) e.frameResized(w,h);
  }
  @Override
  public void frameMoved(final int x,final int y) {
    for(T e:list) e.frameMoved(x,y);
  }
}
