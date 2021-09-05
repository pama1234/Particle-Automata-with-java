package pama1234.processing.autometa.particle;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import pama1234.processing.autometa.particle.component.ParticleAutomata;
import pama1234.processing.util.app.UtilApp;
import processing.core.PApplet;

public class MainApp extends UtilApp{
  public static final int cam_box_r=480;
  ParticleAutomata automata;
  @Override
  public void settings() {
    super.settings();
    noSmooth();
    //    randomSeed(0);
    random(2);
    printSeed();
  }
  @Override
  public void init() {
    noStroke();
    background=color(0);
    center.add.add(automata=new ParticleAutomata(this));
  }
  @Override
  public void update() {
    cam.point.des.setInBox(-cam_box_r,-cam_box_r,cam_box_r,cam_box_r);
  }
  private void printSeed() {
    try {
      Field field=PApplet.class.getDeclaredField("internalRandom");
      field.setAccessible(true);
      Random r=(Random)field.get(this);
      field=Random.class.getDeclaredField("seed");
      field.setAccessible(true);
      AtomicLong l=(AtomicLong)field.get(r);
      println("random seed="+l.toString());
    }catch(NoSuchFieldException e) {
      e.printStackTrace();
    }catch(SecurityException e) {
      e.printStackTrace();
    }catch(IllegalArgumentException e) {
      e.printStackTrace();
    }catch(IllegalAccessException e) {
      e.printStackTrace();
    }
  }
  public static void main(String[] args) {
    System.setProperty("sun.java2d.uiScale","1");
    new MainApp().runSketch();
  }
}
