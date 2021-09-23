package pama1234.processing.autometa.particle.test;

import java.nio.ByteBuffer;

import pama1234.processing.autometa.particle.ui.component.Info;
import pama1234.processing.autometa.particle.ui.page.GamePage;
import pama1234.processing.autometa.particle.util.Cell;
import pama1234.processing.util.app.UtilApp;

public class ByteDataTest extends UtilApp{
  Info info;
  GamePage page;
  @Override
  public void settings() {
    super.settings();
    noSmooth();
    randomSeed(0);
  }
  @Override
  public void init() {
    strokeWeight(Cell.size/4);
    noStroke();
    background=color(0);
    center.add.add(page=new GamePage(this));
  }
  @Override
  public void keyPressed() {
    switch(key) {
      case 'i': {
        long time=System.currentTimeMillis();
        page.e.cellList.fromData(ByteBuffer.wrap(loadBytes("data/saved/data.byte")));
        page.e.toolBar.select=null;
        page.e.toolBar.originalId=-1;
        page.e.toolBar.near.clear();
        System.out.println("loadBytes "+(System.currentTimeMillis()-time)+"ms");
      }
        break;
      case 'o': {
        long time=System.currentTimeMillis();
        saveBytes(
          "data/saved/data.byte",
          page.e.cellList.toData().array());
        System.out.println("saveBytes "+(System.currentTimeMillis()-time)+"ms");
      }
        break;
      case 'p': {
        long time=System.currentTimeMillis();
        saveBytes(
          "data/saved/"+System.currentTimeMillis()+".byte",
          page.e.cellList.toData().array());
        System.out.println("saveBytes "+(System.currentTimeMillis()-time)+"ms");
      }
        break;
    }
  }
  public static void main(String[] args) {
    System.setProperty("sun.java2d.uiScale","1");
    new ByteDataTest().runSketch();
  }
}
