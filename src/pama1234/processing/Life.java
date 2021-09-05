package pama1234.processing;

import pama1234.processing.util.app.UtilApp;
import pama1234.processing.util.listener.LifecycleListener;

public abstract class Life implements LifecycleListener{
  public UtilApp p;
  public Life(final UtilApp p) {
    this.p=p;
  }
}
