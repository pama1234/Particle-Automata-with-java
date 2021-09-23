package pama1234.processing.autometa.particle.ui.component;

import java.util.LinkedList;

import pama1234.math.Tools;
import pama1234.math.vec.Vec2f;
import pama1234.processing.autometa.particle.ui.Tab;
import pama1234.processing.autometa.particle.ui.UITools;
import pama1234.processing.autometa.particle.util.CellCenter;
import pama1234.processing.autometa.particle.util.MetaCellCenter;
import pama1234.processing.util.app.UtilApp;
import processing.core.PConstants;

public class TabCenter extends TextBoard{
  public final LinkedList<Tab<?>> list=new LinkedList<Tab<?>>();
  public Tab<?> select;
  public int index;
  public CellCenter cellCenter;
  public MetaCellCenter metaCenter;
  public ToolBar toolBar;
  public LoadAndSave lsHelper;
  public Scoreboard scoreboard;
  public void setSelect(Tab<?> select) {
    this.select=select;
    index=list.indexOf(select);
    toolBar.refresh();
    lsHelper.refresh();
    scoreboard.refresh();
  }
  public TabCenter(UtilApp p,float x,float y) {
    super(p,x,y,1,1);
  }
  @Override
  public void create() {
    select.create();
  }
  public void refresh() {
    layerInit();
    int tw=w;
    w=1;
    for(Tab<?> tab:list) {
      final int t=(int)Math.ceil(layer.textWidth(tab.name)+textSize);
      if(t>w) w=t;
    }
    int th=h;
    h=(int)(textSize*(list.size()+0.25f)+layer.textDescent());
    if(tw!=w||th!=h) {
      layer=p.createGraphics(w,h);
      layerInit();
    }
    drawLayer();
  }
  @Override
  public void update() {
    point.update();
    for(Tab<?> i:list) i.update();
  }
  public void drawLayer() {
    layer.beginDraw();
    layer.background(0xffF66104);
    UITools.rectFrame(layer,0,0,layer.width,layer.height);
    float ty=0;
    final int ts_d2=textSize/2;
    for(Tab<?> i:list) {
      layer.fill(i.update?0xff00ff00:0xffff0000);
      final float tby=ty+layer.textDescent()-1;
      layer.rect(0,tby,textSize/2,textSize);
      UITools.rectFrame(layer,0,tby,textSize/2,textSize);
      layer.fill(i==select?0xff6FEDFB:0xffDDF4C4);
      layer.rect(textSize/2,tby,w-textSize/2,textSize);
      UITools.rectFrame(layer,textSize/2,tby,w-textSize/2,textSize);
      layer.fill(0);
      layer.text(i.name,ts_d2,ty);
      ty+=textSize;
    }
    layer.endDraw();
  }
  @Override
  public void display() {
    select.display();
    p.image(layer,point.pos.x,point.pos.y);
  }
  @Override
  public void pause() {
    select.pause();
  }
  @Override
  public void resume() {
    select.resume();
  }
  @Override
  public void dispose() {
    select.dispose();
  }
  @Override
  public void mousePressed(final int button) {
    final Vec2f pos=point.pos;
    if(!Tools.inBox(p.cam.mouseX,p.cam.mouseY,pos.x,pos.y,w,h)) select.mousePressed(button);
  }
  @Override
  public void mouseReleased(final int button) {
    final Vec2f pos=point.pos;
    if(Tools.inBox(p.cam.mouseX,p.cam.mouseY,pos.x,pos.y,w,h)) {
      if(button==PConstants.LEFT) {
        final int index=(int)Math.floor((p.cam.mouseY-pos.y-layer.textDescent()+1)/textSize);
        if(index>=0&&index<list.size()) {
          if(p.cam.mouseX>pos.x+textSize*0.5) {
            setSelect(list.get(index));
          }else {
            list.get(index).update=!list.get(index).update;
          }
          refresh();
        }
      }
    }else select.mouseReleased(button);
  }
  @Override
  public void mouseClicked(final int button) {
    final Vec2f pos=point.pos;
    if(!Tools.inBox(p.cam.mouseX,p.cam.mouseY,pos.x,pos.y,w,h)) select.mouseClicked(button);
  }
  @Override
  public void mouseMoved() {
    final Vec2f pos=point.pos;
    if(!Tools.inBox(p.cam.mouseX,p.cam.mouseY,pos.x,pos.y,w,h)) select.mouseMoved();
  }
  @Override
  public void mouseDragged() {
    final Vec2f pos=point.pos;
    if(!Tools.inBox(p.cam.mouseX,p.cam.mouseY,pos.x,pos.y,w,h)) select.mouseDragged();
  }
  @Override
  public void mouseWheel(final int c) {
    final Vec2f pos=point.pos;
    if(!Tools.inBox(p.cam.mouseX,p.cam.mouseY,pos.x,pos.y,w,h)) select.mouseWheel(c);
  }
  @Override
  public void keyPressed(final char key,final int keyCode) {
    select.keyPressed(key,keyCode);
  }
  @Override
  public void keyReleased(final char key,final int keyCode) {
    select.keyReleased(key,keyCode);
  }
  @Override
  public void keyTyped(final char key) {
    select.keyTyped(key);
  }
  @Override
  public void frameResized(final int w,final int h) {
    select.frameResized(w,h);
  }
  @Override
  public void frameMoved(final int x,final int y) {
    select.frameMoved(x,y);
  }
}
