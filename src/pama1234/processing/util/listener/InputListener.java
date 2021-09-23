package pama1234.processing.util.listener;

public interface InputListener{
  public void mousePressed(final int button);
  public void mouseReleased(final int button);
  public void mouseClicked(final int button);
  public void mouseMoved();
  public void mouseDragged();
  public void mouseWheel(final int c);
  public void keyPressed(final char key,final int keyCode);
  public void keyReleased(char key,final int keyCode);
  public void keyTyped(final char key);
  public void frameResized(final int w,final int h);
  public void frameMoved(final int x,final int y);
}
