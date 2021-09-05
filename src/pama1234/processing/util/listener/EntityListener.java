package pama1234.processing.util.listener;

public interface EntityListener extends InputListener,LifecycleListener{
  @Override
  void create();
  @Override
  void update();
  @Override
  void display();
  @Override
  void pause();
  @Override
  void resume();
  @Override
  void dispose();
  @Override
  void mousePressed(int button);
  @Override
  void mouseReleased(int button);
  @Override
  void mouseClicked(int button);
  @Override
  void mouseMoved();
  @Override
  void mouseDragged();
  @Override
  void mouseWheel(int c);
  @Override
  void keyPressed(char key,int keyCode);
  @Override
  void keyReleased(char key,int keyCode);
  @Override
  void keyTyped(char key);
  @Override
  void frameResized(int w,int h);
  @Override
  void frameMoved(int x,int y);
}
