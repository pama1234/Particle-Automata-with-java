package pama1234.processing.autometa.particle.util;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import pama1234.math.Tools;
import pama1234.math.physics.PathPoint;
import pama1234.math.vec.Vec2f;
import pama1234.nio.ByteData;
import pama1234.processing.util.Entity;
import pama1234.processing.util.app.UtilApp;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;

public class MetaCell extends Entity implements ByteData{
  private static final int textColor=0xc0ffffff;
  public static final float size=Cell.size*4;
  public final PathPoint point;
  public final MetaCellCenter parent;
  public final String name;
  public final int id;
  public int color=hashCode();
  public final ArrayList<MetaInfo> list;
  public MetaCell(UtilApp p,MetaCellCenter parent,String name) {
    this(p,parent,name,new ArrayList<MetaInfo>(parent.list.size()));
  }
  public MetaCell(UtilApp p,MetaCellCenter parent,String name,ArrayList<MetaInfo> list) {
    super(p);
    this.point=new PathPoint(parent.layers[0].width/2,parent.layers[0].height/2);
    this.parent=parent;
    this.name=name;
    this.id=parent.createId();
    this.list=list;
  }
  public void createIdEvent(final int in) {}
  public void refresh(final int in) {
    final PGraphics l=parent.layers[0];
    final float ang=(id/(float)in)*PConstants.TWO_PI,
      tl=l.width*0.5f-size*4;
    point.des.set(PApplet.sin(ang)*tl,PApplet.cos(ang)*(l.height*0.5f-size*4));
    point.des.add(l.width/2,l.height/2);
  }
  public void disposeIdEvent(final int in) {}
  public void moveIdEvent(final int from,final int to) {}
  @Override
  public void create() {}
  @Override
  public void update() {
    point.update();
  }
  @Override
  public void display() {
    final PGraphics g=parent.layers[0],l=parent.layers[1];
    final Vec2f pos=point.pos;
    for(MetaCell i:parent.list) {
      final float tInfoX,tInfoY;
      if(i==this) {
        tInfoX=pos.x;
        tInfoY=pos.y;
      }else {
        final float dx=point.pos.x-i.point.pos.x,
          dy=point.pos.y-i.point.pos.y;
        final float mag=PApplet.mag(dx,dy);
        final float cellX=((point.pos.x-i.point.pos.x)/mag)*size,
          cellY=((point.pos.y-i.point.pos.y)/mag)*size;
        final float midX=i.point.pos.x+dx/2,midY=i.point.pos.y+dy/2;
        final float ox1=point.pos.x-cellX,
          oy1=point.pos.y-cellY;
        final float tx2=midX+cellX,
          ty2=midY+cellY;
        g.stroke(0x80ffffff&g.lerpColor(color,i.color,0.5f));
        g.line(ox1,oy1,tx2,ty2);
        tInfoX=(ox1+tx2)/2;
        tInfoY=(oy1+ty2)/2;
        g.fill(g.strokeColor);
        g.ellipse(tInfoX,tInfoY,size,size);
      }
      MetaInfo ti=list.get(i.id);
      final float tg=ti.g;
      String mark;
      if(tg<0) {
        l.fill(p.lerpColor(textColor,0xff0000ff,-tg/3));
        mark="←";
      }else if(tg>0) {
        l.fill(p.lerpColor(textColor,0xffff0000,tg/3));
        mark="→";
      }else {
        l.fill(textColor);
        mark="·";
      }
      String ts=Tools.cutToLastDigitString(tg);
      final StringBuilder sb=new StringBuilder();
      sb.append(id);
      sb.append(mark);
      sb.append(ts);
      sb.append(mark);
      sb.append(i.id);
      sb.append('\n');
      sb.append(ti.min);
      sb.append("to");
      sb.append(ti.max);
      l.text(sb.toString(),tInfoX,tInfoY);
      g.noStroke();
      g.fill(color);
      g.ellipse(pos.x,pos.y,size,size);
      g.textSize(MetaCellCenter.textSize*2);
      g.fill(127);
      g.text("\""+name+"\"",pos.x-1,pos.y);
      g.fill(0xff000000|(~color));
      g.text("\""+name+"\"",pos.x,pos.y);
      g.textSize(MetaCellCenter.textSize);
    }
  }
  @Override
  public void pause() {}
  @Override
  public void resume() {}
  @Override
  public void dispose() {
    parent.disposeId(id);
  }
  @Override
  public void mousePressed(final int button) {}
  @Override
  public void mouseReleased(final int button) {}
  @Override
  public void mouseClicked(final int button) {}
  @Override
  public void mouseMoved() {}
  @Override
  public void mouseDragged() {}
  @Override
  public void mouseWheel(final int c) {}
  @Override
  public void keyPressed(final char key,final int keyCode) {}
  @Override
  public void keyReleased(final char key,final int keyCode) {}
  @Override
  public void keyTyped(final char key) {}
  @Override
  public void frameResized(final int w,final int h) {}
  @Override
  public void frameMoved(final int x,final int y) {}
  @Override
  public void fromData(ByteBuffer in,int offset,int size) {}
  @Override
  public ByteBuffer toData(ByteBuffer in,int offset) {
    return in;
  }
  @Override
  public int bufferSize() {
    return 0;
  }
}
