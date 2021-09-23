package pama1234.processing.autometa.particle.test;

import pama1234.processing.util.app.UtilApp;
import processing.core.PGraphics;

public class ProcessingFeaturesTest extends UtilApp{
  @Override
  public void init() {
    PGraphics l=createGraphics(1,1);
    l.beginDraw();
    l.endDraw();
    l.clear();
  }
  public static void main(String[] args) {
    System.setProperty("sun.java2d.uiScale","1");
    new ProcessingFeaturesTest().runSketch();
  }
}
