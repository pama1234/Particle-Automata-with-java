package pama1234.processing.autometa.particle.ui.page;

import pama1234.processing.autometa.particle.ui.page.content.ParticleAutomata;
import pama1234.processing.util.app.UtilApp;

public class GamePage extends Page<ParticleAutomata>{
  public GamePage(UtilApp p) {
    super(p,"开始",new ParticleAutomata(p));
  }
  @Override
  public void show() {}
  @Override
  public void hide() {}
}
