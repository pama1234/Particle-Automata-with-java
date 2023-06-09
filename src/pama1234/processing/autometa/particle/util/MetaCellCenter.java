package pama1234.processing.autometa.particle.util;

import java.util.LinkedList;

import pama1234.math.Tools;
import pama1234.math.vec.Vec2i;
import pama1234.processing.util.app.UtilApp;
import pama1234.processing.util.center.EntityCenter;
import processing.core.PConstants;
import processing.core.PGraphics;

public class MetaCellCenter extends EntityCenter<MetaCell>{
  private static final float minRefreshDist=1f/(1<<8);
  public static final int textSize=12;
  public final LinkedList<Integer> meta=new LinkedList<Integer>();
  public int x,y;
  public PGraphics[] layers;
  public Vec2i select=new Vec2i();
  public int refresh=2;
  public MetaCell[] cells;
  public MetaInfo[][] matrix;
  public MetaCellCenter(UtilApp p,int x,int y,int w,int h) {
    super(p);
    this.x=x;
    this.y=y;
    layers=new PGraphics[2];
    for(int i=0;i<layers.length;i++) {
      PGraphics layer=p.createGraphics(w-x,h-y);
      layers[i]=layer;
      layer.beginDraw();
      layer.textFont(p.font);
      layer.textSize(textSize);
      layer.textLeading(textSize);
      layer.textAlign(PConstants.CENTER,PConstants.CENTER);
      layer.strokeWeight(3);
      layer.endDraw();
    }
  }
  public int createId() {
    int out=0;
    while(meta.contains(out)) out++;
    meta.add(out);
    for(MetaCell i:list) i.createIdEvent(out);
    return out;
  }
  public void disposeId(final int in) {
    meta.remove(Integer.valueOf(in));
    for(MetaCell i:list) i.disposeIdEvent(in);
  }
  public void moveId(final int from,final int to) {
    meta.remove(Integer.valueOf(from));
    meta.add(to);
    for(MetaCell i:list) i.moveIdEvent(from,to);
  }
  @Override
  public void display() {
    boolean flag=false;
    for(MetaCell i:list) {
      if(Math.abs(i.point.pos.x-i.point.des.x)>minRefreshDist||
        Math.abs(i.point.pos.y-i.point.des.y)>minRefreshDist) {
        flag=true;
        break;
      }
    }
    if(flag) refreshLayer();
    else while(refresh>0) {
      refresh--;
      refreshLayer();
    }
    for(PGraphics i:layers) p.image(i,x,y);
  }
  public void refreshLayer() {
    for(PGraphics i:layers) {
      i.beginDraw();
      i.clear();
    }
    MetaCell txc=list.get(select.x),
      tyc=list.get(select.y);
    layers[0].fill(0xff6D5CB7);
    final float tx1=tyc.point.pos.x,
      ty1=tyc.point.pos.y;
    layers[0].ellipse(tx1,ty1,MetaCell.size*2,MetaCell.size*2);
    layers[0].fill(0xffFB6104);
    final float tx2=txc.point.pos.x,
      ty2=txc.point.pos.y;
    layers[0].ellipse(tx2,ty2,MetaCell.size*2,MetaCell.size*2);
    if(txc!=tyc) {
      layers[0].stroke(0x80D53569);
      layers[0].strokeWeight(MetaCell.size*2);
      layers[0].line(tx1,ty1,tx2,ty2);
      layers[0].strokeWeight(3);
    }
    super.display();
    MetaInfo txi=txc.list.get(select.y),
      tyi=tyc.list.get(select.x);
    layers[1].fill(0xff00ff00);
    StringBuilder sb=new StringBuilder();
    sb.append(select.toString());
    sb.append("\n\"");
    sb.append(txc.name);
    sb.append("\" to \"");
    sb.append(tyc.name);
    sb.append("\"\n");
    sb.append(txi.g);
    sb.append("<-->");
    sb.append(tyi.g);
    sb.append("\n");
    sb.append(txi.min);
    sb.append("<[radius of ");
    sb.append(txc.name);
    sb.append("]<");
    sb.append(txi.max);
    sb.append("\n");
    sb.append(tyi.min);
    sb.append("<[radius of ");
    sb.append(tyc.name);
    sb.append("]<");
    sb.append(tyi.max);
    sb.append("\n[color of ");
    sb.append(txc.name);
    sb.append("] #");
    sb.append(Tools.colorToString(txc.color));
    sb.append("\n[color of ");
    sb.append(tyc.name);
    sb.append("] #");
    sb.append(Tools.colorToString(tyc.color));
    layers[1].text(
      sb.toString(),
      layers[1].width/2,layers[1].height/2);
    for(PGraphics i:layers) i.endDraw();
  }
  @Override
  public void keyPressed(char key,int keyCode) {
    super.keyPressed(key,keyCode);
    switch(keyCode) {
      case PConstants.UP:
        select.y--;
        if(select.y<0) select.y+=list.size();
        refresh=1;
        break;
      case PConstants.DOWN:
        select.y++;
        if(select.y>=list.size()) select.y-=list.size();
        refresh=1;
        break;
      case PConstants.LEFT:
        select.x--;
        if(select.x<0) select.x+=list.size();
        refresh=1;
        break;
      case PConstants.RIGHT:
        select.x++;
        if(select.x>=list.size()) select.x-=list.size();
        refresh=1;
        break;
    }
  }
}
