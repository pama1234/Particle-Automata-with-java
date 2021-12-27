package pama1234.processing.autometa.particle.ui.component;

import java.util.LinkedList;

import pama1234.math.Tools;
import pama1234.math.vec.Vec2f;
import pama1234.processing.autometa.particle.ui.UITools;
import pama1234.processing.autometa.particle.util.Cell;
import pama1234.processing.autometa.particle.util.CellCenter;
import pama1234.processing.autometa.particle.util.MetaCellCenter;
import pama1234.processing.util.app.UtilApp;
import processing.core.PApplet;
import processing.core.PConstants;

public class ToolBar extends TextBoard{
  public CellCenter cellCenter;
  public MetaCellCenter metaCenter;
  public TabCenter parent;
  public String[][] names;
  public int[] state;
  public static final int up=0,down=1,left=2,right=3;
  public final boolean[] keys=new boolean[4];
  public static final float cellViewSpeed=Cell.dist/24;
  public Cell select;
  public LinkedList<Cell> near=new LinkedList<Cell>();
  public float dist=Cell.dist*6;
  public int originalId;
  public ToolBar(UtilApp p,TabCenter parent,float x,float y) {
    super(p,x,y,1,1);
    //    layerInit();
    this.parent=parent;
    names=new String[][] {{"暂无操作",},{"移动粒子","批量移动"},};
    state=new int[2];
    state[1]=1;
    cellCenter=parent.cellCenter;
    metaCenter=parent.metaCenter;
  }
  @Override
  public void create() {}
  public void refresh() {
    initLayer();
    int tw=w;
    w=1;
    final String[] tsa=names[parent.index];
    for(String i:tsa) {
      final int t=(int)Math.ceil(layer.textWidth(i)+textSize);
      if(t>w) w=t;
    }
    int th=h;
    h=(int)(textSize*(tsa.length+0.25f)+layer.textDescent());
    if(tw!=w||th!=h) {
      layer=p.createGraphics(w,h);
      initLayer();
    }
    drawLayer();
  }
  public void drawLayer() {
    layer.beginDraw();
    layer.background(0xffF66104);
    UITools.border(layer,0,0,layer.width,layer.height);
    float ty=0;
    final int ts_d2=textSize/2;
    final String[] tsa=names[parent.index];
    for(int i=0;i<tsa.length;i++) {
      String ts=tsa[i];
      final float tby=ty+layer.textDescent()-1;
      layer.fill(i==state[parent.index]?0xff6FEDFB:0xffDDF4C4);
      layer.rect(textSize/2,tby,w-textSize/2,textSize);
      UITools.border(layer,textSize/2,tby,w-textSize/2,textSize);
      layer.fill(0);
      layer.text(ts,ts_d2,ty);
      ty+=textSize;
    }
    layer.endDraw();
  }
  @SuppressWarnings("static-access")
  @Override
  public void update() {
    point.update();
    final int ti=parent.index;
    if(ti==1) {
      if(state[ti]==0) {
        if(select!=null) {
          if(keys[left]!=keys[right]) {
            if(keys[left]) select.point.vel.x-=cellViewSpeed;
            else select.point.vel.x+=cellViewSpeed;
          }
          if(keys[up]!=keys[down]) {
            if(keys[up]) select.point.vel.y-=cellViewSpeed;
            else select.point.vel.y+=cellViewSpeed;
          }
          p.cam.point.des.set(select.point.pos);
        }
      }else if(state[ti]==1) {
        if(select!=null) {
          float tdx=0,tdy=0;
          if(keys[left]!=keys[right]) {
            if(keys[left]) tdx=-cellViewSpeed;
            else tdx=cellViewSpeed;
          }
          if(keys[up]!=keys[down]) {
            if(keys[up]) tdy=-cellViewSpeed;
            else tdy=cellViewSpeed;
          }
          select.point.vel.add(tdx,tdy);
          final Vec2f pos2=select.point.pos;
          near.clear();
          for(Cell i:cellCenter.list) {
            if(i==select||i.meta!=select.meta) continue;
            final Vec2f pos1=i.point.pos;
            final float td;
            if(!parent.cellCenter.boxed) {
              float dx=pos1.x-pos2.x;
              float dy=pos1.y-pos2.y;
              if(dx>parent.cellCenter.w/2) dx=parent.cellCenter.w-dx;
              if(dy>parent.cellCenter.h/2) dy=parent.cellCenter.h-dy;
              td=PApplet.mag(dx,dy);
            }else {
              td=PApplet.dist(
                pos1.x,
                pos1.y,
                pos2.x,
                pos2.y);
            }
            if(td<dist) {
              near.add(i);
              i.point.vel.add(tdx,tdy);
              float tdx2=pos2.x-pos1.x,
                tdy2=pos2.y-pos1.y;
              if(tdx2>0) {
                tdx2-=dist/2;
                if(tdx2<0) tdx2=0;
              }else {
                tdx2+=dist/2;
                if(tdx2>0) tdx2=0;
              }
              if(tdy2>0) {
                tdy2-=dist/2;
                if(tdy2<0) tdy2=0;
              }else {
                tdy2+=dist/2;
                if(tdy2>0) tdy2=0;
              }
              i.point.vel.add(tdx2*0.2f,tdy2*0.2f);
            }
          }
          p.cam.point.des.set(select.point.pos);
        }
      }
    }
  }
  @Override
  public void display() {
    p.image(layer,point.pos.x,point.pos.y);
    if(select!=null) {
      p.noFill();
      p.stroke(0x80ffffff);
      final float tx=select.point.pos.x,
        ty=select.point.pos.y,
        ts_m2=Cell.size*2,
        ts_d2=Cell.size/2,
        ts_d2m3=ts_d2*3;
      final boolean flag=parent.index==1&&state[parent.index]==1;
      final float ts_m2_2=flag?ts_m2+dist:ts_m2;
      p.ellipse(tx,ty,ts_m2,ts_m2);
      if(flag) {
        p.ellipse(tx,ty,dist*2,dist*2);
        for(Cell i:near) {
          Vec2f pos=i.point.pos;
          p.ellipse(pos.x,pos.y,ts_m2,ts_m2);
        }
      }
      if(keys[up]) {
        p.line(tx,ty-ts_m2_2,tx-ts_d2,ty-ts_d2m3);
        p.line(tx,ty-ts_m2_2,tx+ts_d2,ty-ts_d2m3);
      }
      if(keys[down]) {
        p.line(tx,ty+ts_m2_2,tx-ts_d2,ty+ts_d2m3);
        p.line(tx,ty+ts_m2_2,tx+ts_d2,ty+ts_d2m3);
      }
      if(keys[left]) {
        p.line(tx-ts_m2_2,ty,tx-ts_d2m3,ty-ts_d2);
        p.line(tx-ts_m2_2,ty,tx-ts_d2m3,ty+ts_d2);
      }
      if(keys[right]) {
        p.line(tx+ts_m2_2,ty,tx+ts_d2m3,ty-ts_d2);
        p.line(tx+ts_m2_2,ty,tx+ts_d2m3,ty+ts_d2);
      }
      p.noStroke();
    }
  }
  @Override
  public void pause() {}
  @Override
  public void resume() {}
  @Override
  public void dispose() {}
  @Override
  public void mousePressed(int button) {
    final Vec2f pos=point.pos;
    final int ti=parent.index;
    if(Tools.inBox(p.cam.mouseX,p.cam.mouseY,pos.x,pos.y,w,h)) {
      if(button==PConstants.LEFT) {
        final int index=(int)Math.floor((p.cam.mouseY-pos.y-layer.textDescent()+1)/textSize);
        if(index>=0&&index<names[ti].length) {
          if(p.cam.mouseX>pos.x+textSize*0.5) {
            state[ti]=index;
            refresh();
          }
        }
      }
    }else {
      if(ti==1) {
        if(state[ti]==0||state[ti]==1) {
          if(button==PConstants.LEFT) {
            if(select!=null) {
              select.meta=originalId;
              select=null;
            }
            float minDist=Cell.size;
            for(Cell i:cellCenter.list) {
              final float td=PApplet.dist(i.point.pos.x,i.point.pos.y,p.cam.mouseX,p.cam.mouseY);
              if(td<minDist) {
                select=i;
                minDist=td;
              }
            }
            if(select!=null) originalId=select.meta;
            else originalId=-1;
          }
        }
      }
    }
  }
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
  public void keyPressed(char key,int keyCode) {
    if(key==' ') {
      parent.select.update=!parent.select.update;
      parent.refresh();
    }
    final int ti=parent.index;
    if(ti==1) {
      if(state[ti]==0||state[ti]==1) {
        key=Character.toLowerCase(key);
        switch(key) {
          case 'w':
            keys[up]=true;
            break;
          case 's':
            keys[down]=true;
            break;
          case 'a':
            keys[left]=true;
            break;
          case 'd':
            keys[right]=true;
            break;
        }
        if(select!=null) switch(key) {
          case 'e': {
            int index=select.meta-1;
            if(index<0) index+=select.parent.meta.list.size();
            select.meta=index;
          }
            break;
          case 'q': {
            int index=select.meta+1;
            final int ts=select.parent.meta.list.size();
            if(index>=ts) index-=ts;
            select.meta=index;
          }
            break;
        }
      }
    }
  }
  @Override
  public void keyReleased(char key,int keyCode) {
    final int ti=parent.index;
    if(ti==1) {
      if(state[ti]==0||state[ti]==1) {
        key=Character.toLowerCase(key);
        switch(key) {
          case 'w':
            keys[up]=false;
            break;
          case 's':
            keys[down]=false;
            break;
          case 'a':
            keys[left]=false;
            break;
          case 'd':
            keys[right]=false;
            break;
        }
      }
    }
  }
  @Override
  public void keyTyped(char key) {}
  @Override
  public void frameResized(int w,int h) {}
  @Override
  public void frameMoved(int x,int y) {}
}
