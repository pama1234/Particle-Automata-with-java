package pama1234.processing.autometa.particle.util;

import static pama1234.processing.autometa.particle.util.CellCenter.layer_cell_size;

import java.nio.ByteBuffer;
import java.util.LinkedList;

import pama1234.math.physics.MassPoint;
import pama1234.math.physics.MassVar;
import pama1234.nio.ByteData;
import pama1234.nio.ByteDataFactory;
import pama1234.processing.Entity;
import pama1234.processing.util.app.UtilApp;

public class Cell extends Entity implements ByteData{
  public static final float damping=1f/8;
  public static final int buffer_size=MassPoint.buffer_size+ByteData.INT_SIZE;
  public static final float size=3,dist=size;
  public static final float minScore=1f/2048;
  public CellCenter parent;
  public int meta;
  public final MassPoint point;
  public final LinkedList<Cell> back=new LinkedList<Cell>();
  public final MassVar score=new MassVar(0);
  {
    score.f=0f;
  }
  public Cell(UtilApp p,CellCenter parent,int meta,float x,float y) {
    super(p);
    this.parent=parent;
    this.meta=meta;
    this.point=new MassPoint(x,y);
  }
  @Override
  public void create() {}
  @Override
  public void update() {
    point.update();
    score.pos*=damping;
    score.pos+=minScore;
    score.update();
  }
  @SuppressWarnings("static-access")
  @Override
  public void display() {
    parent.layer.fill(parent.meta.list.get(meta).color);
    if(parent.boxed) {
      parent.layer.ellipse(
        point.pos.x-parent.x1+layer_cell_size,
        point.pos.y-parent.y1+layer_cell_size,
        size,size);
    }else {
      float tx=point.pos.x,
        ty=point.pos.y;
      if(tx>0) tx-=parent.w;
      if(ty>0) ty-=parent.h;
      parent.layer.ellipse(
        tx-parent.x1+parent.w/4f,
        ty-parent.y1+parent.h/4f,
        size,size);
      parent.layer.ellipse(
        tx+parent.w-parent.x1+parent.w/4f,
        ty+parent.h-parent.y1+parent.h/4f,
        size,size);
      parent.layer.ellipse(
        tx+parent.w-parent.x1+parent.w/4f,
        ty-parent.y1+parent.h/4f,
        size,size);
      parent.layer.ellipse(
        tx-parent.x1+parent.w/4f,
        ty+parent.h-parent.y1+parent.h/4f,
        size,size);
    }
  }
  @Override
  public void pause() {}
  @Override
  public void resume() {}
  @Override
  public void dispose() {}
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
  public void fromData(ByteBuffer in,int offset,int size) {
    meta=in.getInt(offset);
    point.fromData(in,offset+=ByteData.INT_SIZE,offset+=point.bufferSize());
  }
  @Override
  public ByteBuffer toData(ByteBuffer in,int offset) {
    in.putInt(offset,meta);
    point.toData(in,offset+=ByteData.INT_SIZE);
    return in;
  }
  @Override
  public int bufferSize() {
    return buffer_size;
  }
  public static class CellFactory implements ByteDataFactory<Cell>{
    @Override
    public ByteBuffer save(Cell in) {
      return null;
    }
    @Override
    public Cell load(ByteBuffer in) {
      return null;
    }
    @Override
    public ByteBuffer saveTo(Cell in,ByteBuffer data) {
      return null;
    }
    @Override
    public Cell loadTo(ByteBuffer in,Cell element) {
      return null;
    }
  }
}
