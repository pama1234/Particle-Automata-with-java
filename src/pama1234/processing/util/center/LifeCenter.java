package pama1234.processing.util.center;

import java.util.LinkedList;

import pama1234.processing.Life;
import pama1234.processing.util.app.UtilApp;
import pama1234.processing.util.listener.LifecycleListener;

public class LifeCenter<T extends LifecycleListener>extends Life{
  public LinkedList<T> list,add,remove;
  public LifeCenter(UtilApp p) {
    super(p);
    list=new LinkedList<T>();
    add=new LinkedList<T>();
    remove=new LinkedList<T>();
  }
  public void refresh() {
    list.addAll(add);
    add.clear();
    list.removeAll(remove);
    remove.clear();
  }
  @Override
  public void create() {
    for(T l:list) l.create();
  }
  @Override
  public void update() {
    for(T l:list) l.update();
  }
  @Override
  public void display() {
    for(T l:list) l.display();
  }
  @Override
  public void pause() {
    for(T l:list) l.pause();
  }
  @Override
  public void resume() {
    for(T l:list) l.resume();
  }
  @Override
  public void dispose() {
    for(T l:list) l.dispose();
  }
}
