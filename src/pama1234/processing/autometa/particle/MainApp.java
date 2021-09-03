package pama1234.processing.autometa.particle;

import pama1234.processing.autometa.particle.util.Cell;
import pama1234.processing.util.app.UtilApp;

public class MainApp extends UtilApp{
  public static final int cam_box_r=480;
  ParticleAutomata automata;
  @Override
  public void settings() {
    super.settings();
    noSmooth();
    //    randomSeed(0);
  }
  @Override
  public void init() {
    strokeWeight(Cell.size/4);
    noStroke();
    background=color(0);
    center.add.add(automata=new ParticleAutomata(this));
  }
  @Override
  public void update() {
    cam.point.des.setInBox(-cam_box_r,-cam_box_r,cam_box_r,cam_box_r);
  }
  public static void main(String[] args) {
    System.setProperty("sun.java2d.uiScale","1");
    new MainApp().runSketch();
  }
}
