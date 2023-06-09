package pama1234.processing;

import pama1234.processing.util.app.UtilApp;
import pama1234.processing.util.listener.EntityListener;

public abstract class Entity extends Life implements EntityListener{
  public Entity(UtilApp p) {
    super(p);
  }
}
