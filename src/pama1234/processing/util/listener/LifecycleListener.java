package pama1234.processing.util.listener;

public interface LifecycleListener{
  public void create();
  public void update();
  public void display();
  public void pause();
  public void resume();
  public void dispose();
}