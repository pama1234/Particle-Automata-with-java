package pama1234.processing.autometa.particle.ui.component;

import java.text.DecimalFormat;
import java.util.LinkedList;

import pama1234.math.physics.PathPoint;
import pama1234.processing.autometa.particle.ui.UITools;
import pama1234.processing.autometa.particle.util.Cell;
import pama1234.processing.autometa.particle.util.CellCenter;
import pama1234.processing.autometa.particle.util.MetaCellCenter;
import pama1234.processing.util.app.UtilApp;
import processing.core.PApplet;
import processing.core.PConstants;

public class Scoreboard extends TextBoard{
  public CellCenter cellCenter;
  public MetaCellCenter metaCenter;
  public TabCenter parent;
  public float score;
  public String text;
  public final DecimalFormat format=new DecimalFormat(
    "得分：00000,"+
      "00000,"+
      "00000,"+
      "00000."+
      "00000G");
  public final LinkedList<Cell> sons=new LinkedList<Cell>();
  public Scoreboard(UtilApp p,TabCenter parent,float x,float y) {
    super(p,x,y,1,1);
    textAlignX=PConstants.CENTER;
    layerInit();
    this.parent=parent;
    point=new PathPoint(0,0,x,y);
    cellCenter=parent.cellCenter;
    metaCenter=parent.metaCenter;
  }
  @Override
  public void create() {}
  public void refresh() {
    layerInit();
    final String tt=text;
    text=format.format(PApplet.sqrt(score));
    if(!text.equals(tt)) {
      final int tw=w;
      w=(int)Math.ceil(layer.textWidth(text))+textSize*2;
      final int th=h;
      h=(int)(textSize*(1.25f)+layer.textDescent());
      if(tw!=w||th!=h) {
        layer=p.createGraphics(w,h);
        layerInit();
      }
      drawLayer();
    }
  }
  public void drawLayer() {
    layer.beginDraw();
    layer.background(0xff00317A);
    UITools.rectFrame(layer,0,0,layer.width,layer.height);
    float ty=0;
    final int ts_d2=layer.width/2;
    final float tby=ty+layer.textDescent()-1;
    layer.fill(0xff005984);
    layer.rect(textSize/2,tby,w-textSize,textSize);
    UITools.rectFrame(layer,textSize/2,tby,w-textSize,textSize);
    layer.fill(0xffF9CC31);
    layer.text(text,ts_d2,ty);
    layer.endDraw();
  }
  @Override
  public void update() {
    point.update();
    if(parent.toolBar.select!=null) {
      score=parent.toolBar.select.score.pos;
    }
    refresh();
  }
  @Override
  public void display() {
    if(parent.index==1) p.image(layer,point.pos.x-layer.width/2,point.pos.y-layer.height/2);
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
