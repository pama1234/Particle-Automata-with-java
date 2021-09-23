package pama1234.processing.autometa.particle.ui.page.content;

import pama1234.math.physics.PathPoint;
import pama1234.processing.Entity;
import pama1234.processing.util.app.UtilApp;
import processing.core.PConstants;
import processing.core.PGraphics;

public class Welcome extends Entity{
  private static final String[] slogan= {"————魔道中人专用","————玩虫子！","————加我微信！lizerun2017","————加我QQ！1507585905"};
  private static final String title="炼蛊模拟器";
  public final PathPoint point;
  public PGraphics layer;
  public int textSize=128;
  public static int sloganPos;
  public Welcome(UtilApp p,float x,float y) {
    super(p);
    point=new PathPoint(0,0,x,y);
  }
  public void refresh() {
    sloganPos=(int)p.random(slogan.length);
    if(layer==null) layer=p.createGraphics(1,1);
    layer.beginDraw();
    layer.textFont(p.font);
    layer.textAlign(PConstants.LEFT,PConstants.TOP);
    layer.textSize(textSize);
    layer.textLeading(textSize);
    layer.endDraw();
    layer=p.createGraphics((int)Math.ceil(layer.textWidth(title)),textSize*2);
    layer.beginDraw();
    layer.textFont(p.font);
    layer.textAlign(PConstants.LEFT,PConstants.TOP);
    layer.textSize(textSize);
    layer.textLeading(textSize);
    layer.endDraw();
    drawLayer();
  }
  public void drawLayer() {
    layer.beginDraw();
    layer.textSize(textSize);
    layer.text(title,0,0);
    layer.textSize(textSize/3);
    layer.text(slogan[sloganPos],layer.width-layer.textWidth(slogan[sloganPos]),textSize*1.33f);
    layer.endDraw();
  }
  @Override
  public void create() {}
  @Override
  public void update() {
    point.update();
  }
  @Override
  public void display() {
    p.image(layer,point.pos.x-layer.width/2,point.pos.y-layer.height/2);
  }
  @Override
  public void pause() {}
  @Override
  public void resume() {}
  @Override
  public void dispose() {}
  @Override
  public void mousePressed(int button) {}
  @Override
  public void mouseReleased(int button) {}
  @Override
  public void mouseClicked(int button) {}
  @Override
  public void mouseMoved() {}
  @Override
  public void mouseDragged() {}
  @Override
  public void mouseWheel(int c) {}
  @Override
  public void keyPressed(char key,int keyCode) {}
  @Override
  public void keyReleased(char key,int keyCode) {}
  @Override
  public void keyTyped(char key) {}
  @Override
  public void frameResized(int w,int h) {}
  @Override
  public void frameMoved(int x,int y) {}
}
