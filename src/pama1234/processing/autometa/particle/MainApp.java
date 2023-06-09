package pama1234.processing.autometa.particle;

import pama1234.processing.autometa.particle.ui.component.Info;
import pama1234.processing.autometa.particle.ui.page.GamePage;
import pama1234.processing.autometa.particle.ui.page.PageCenter;
import pama1234.processing.autometa.particle.ui.page.SettingsPage;
import pama1234.processing.autometa.particle.ui.page.StartPage;
import pama1234.processing.autometa.particle.util.Cell;
import pama1234.processing.util.app.UtilApp;

public class MainApp extends UtilApp{
  public static final int cam_box_r=720;
  public Info info;
  public PageCenter pageCenter;
  @Override
  public void settings() {
    super.settings();
    noSmooth();
  }
  @Override
  public void init() {
    strokeWeight(Cell.size/4);
    noStroke();
    background=color(0);
    center.add.add(pageCenter=new PageCenter(this,new StartPage(this),-640,0));
    pageCenter.list.add(new GamePage(this));
    pageCenter.list.add(new SettingsPage(this));
    pageCenter.refresh();
    pageCenter.postSetDes();
    center.add.add(info=new Info(this,520,-320));
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
