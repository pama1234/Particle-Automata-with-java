package pama1234.processing.autometa.particle.ui.page;

import pama1234.processing.autometa.particle.ui.page.content.Settings;
import pama1234.processing.util.app.UtilApp;

public class SettingsPage extends Page<Settings>{
  public SettingsPage(UtilApp p) {
    super(p,"设置",new Settings(p));
  }
  @Override
  public void show() {}
  @Override
  public void hide() {}
}
