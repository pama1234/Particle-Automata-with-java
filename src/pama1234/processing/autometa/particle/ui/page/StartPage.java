package pama1234.processing.autometa.particle.ui.page;

import pama1234.processing.autometa.particle.ui.page.content.Welcome;
import pama1234.processing.util.app.UtilApp;

public class StartPage extends Page<Welcome>{
  public StartPage(UtilApp p) {
    super(p,"初始界面",new Welcome(p,0,-160));
    e.refresh();
  }
  @Override
  public void show() {
    e.refresh();
  }
  @Override
  public void hide() {}
}