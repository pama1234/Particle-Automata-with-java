package pama1234.processing.util;

import pama1234.processing.util.app.UtilApp;
import pama1234.processing.util.listener.InputListener;

public abstract class Entity extends Life implements InputListener{
  public Entity(UtilApp p) {
    super(p);
  }
}
