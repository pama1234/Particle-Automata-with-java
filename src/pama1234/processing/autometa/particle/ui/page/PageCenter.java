package pama1234.processing.autometa.particle.ui.page;

import java.util.LinkedList;

import pama1234.math.Tools;
import pama1234.math.vec.Vec2f;
import pama1234.processing.autometa.particle.ui.UITools;
import pama1234.processing.autometa.particle.ui.component.TextBoard;
import pama1234.processing.util.app.UtilApp;
import processing.core.PConstants;

public class PageCenter extends TextBoard{
  public final LinkedList<Page<?>> list=new LinkedList<Page<?>>();
  public Page<?> main,select;
  public int index;
  public float x,y;
  public void setSelect(Page<?> select) {
    if(this.select!=null) this.select.hide();
    this.select=select;
    index=list.indexOf(select);
    select.show();
  }
  public PageCenter(UtilApp p,Page<?> main,float x,float y) {
    super(p,0,0,1,1);
    this.x=x;
    this.y=y;
    textAlignX=PConstants.CENTER;
    this.main=main;
    list.add(main);
    setSelect(main);
  }
  @Override
  public void create() {
    select.create();
  }
  public void refresh() {
    if(layer==null) layer=p.createGraphics(1,1);
    initLayer();
    int tw=w;
    w=1;
    for(Page<?> tab:list) {
      final int t=(int)Math.ceil(layer.textWidth(tab.name)+textSize);
      if(t>w) w=t;
    }
    int th=h;
    h=(int)(textSize*(list.size()+0.25f)+layer.textDescent());
    if(tw!=w||th!=h) {
      layer=p.createGraphics(w,h);
      initLayer();
    }
    drawLayer();
  }
  public void postSetDes() {
    point.des.set(-w/2,-h/2);
  }
  @Override
  public void update() {
    point.update();
    select.update();
    //    for(Page<?> i:list) i.update();
  }
  public void drawLayer() {
    layer.beginDraw();
    layer.background(0xffF66104);
    UITools.border(layer,0,0,layer.width,layer.height);
    float ty=0;
    final int ts_d2=layer.width/2;
    for(Page<?> i:list) {
      final float tby=ty+layer.textDescent()-1;
      layer.fill(i==select?0xff6FEDFB:0xffDDF4C4);
      layer.rect(0,tby,w,textSize);
      UITools.border(layer,0,tby,w,textSize);
      layer.fill(0);
      layer.text(i.name,ts_d2,ty);
      ty+=textSize;
    }
    layer.endDraw();
  }
  @Override
  public void display() {
    select.display();
    //    p.image(layer,point.pos.x-w/2,point.pos.y-h/2);
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
    //    System.out.println(Tools.inBox(p.cam.mouseX,p.cam.mouseY,pos.x,pos.y,w,h));
    if(Tools.inBox(p.cam.mouseX,p.cam.mouseY,pos.x,pos.y,w,h)) {
      if(button==PConstants.LEFT) {
        final int index=(int)Math.floor((p.cam.mouseY-pos.y-layer.textDescent()+1)/textSize);
        if(index>=0&&index<list.size()) {
          setSelect(list.get(index));
          refresh();
          if(select==main) point.des.set(-w/2,-h/2);
          else point.des.set(x,y);
          //          drawLayer();
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
