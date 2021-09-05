package pama1234.processing.autometa.particle.component;

import pama1234.processing.Entity;
import pama1234.processing.autometa.particle.element.CellGroup;
import pama1234.processing.autometa.particle.element.MetaCenter;
import pama1234.processing.util.app.UtilApp;

public class ParticleAutomata extends Entity{
  //---
  public final MetaCenter metaCenter;
  public final CellGroup cellCenter;
  //---
  public final int cellCount=64;
  public final int typeCount=16;
  public ParticleAutomata(UtilApp p) {
    super(p);
    metaCenter=new MetaCenter(typeCount);
    cellCenter=new CellGroup(cellCount,null,null);
  }
  @Override
  public void update() {
    cellCenter.update();
  }
  @Override
  public void display() {}
  //---
  @Override
  public void create() {}
  @Override
  public void pause() {}
  @Override
  public void resume() {}
  @Override
  public void dispose() {}
  @Override
  public void mousePressed(int button) {}
  @Override
  public void mouseReleased(int button) {}
  @Override
  public void mouseClicked(int button) {}
  @Override
  public void mouseMoved() {}
  @Override
  public void mouseDragged() {}
  @Override
  public void mouseWheel(int c) {}
  @Override
  public void keyPressed(char key,int keyCode) {}
  @Override
  public void keyReleased(char key,int keyCode) {}
  @Override
  public void keyTyped(char key) {}
  @Override
  public void frameResized(int w,int h) {}
  @Override
  public void frameMoved(int x,int y) {}
}
