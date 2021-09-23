package pama1234.processing.autometa.particle.ui.component;

import java.nio.ByteBuffer;

import pama1234.math.Tools;
import pama1234.math.vec.Vec2f;
import pama1234.processing.autometa.particle.ui.UITools;
import pama1234.processing.autometa.particle.util.CellCenter;
import pama1234.processing.autometa.particle.util.MetaCellCenter;
import pama1234.processing.util.app.UtilApp;
import processing.core.PConstants;

public class LoadAndSave extends TextBoard{
  public CellCenter cellCenter;
  public MetaCellCenter metaCenter;
  public TabCenter parent;
  public String[][] names;
  public int[] state;
  public int cooling,coolingTime=10;
  public LoadAndSave(UtilApp p,TabCenter parent,float x,float y) {
    super(p,x,y,1,1);
    layerInit();
    this.parent=parent;
    names=new String[][] {{"暂无操作",},{"","存档","读档","另存为","打开",}};
    state=new int[2];
    cellCenter=parent.cellCenter;
    metaCenter=parent.metaCenter;
  }
  @Override
  public void create() {}
  public void refresh() {
    layerInit();
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
      layerInit();
    }
    drawLayer();
  }
  public void drawLayer() {
    layer.beginDraw();
    layer.background(0xffF66104);
    UITools.rectFrame(layer,0,0,layer.width,layer.height);
    float ty=0;
    final int ts_d2=textSize/2;
    final String[] tsa=names[parent.index];
    for(int i=0;i<tsa.length;i++) {
      String ts=tsa[i];
      final float tby=ty+layer.textDescent()-1;
      layer.fill(i==state[parent.index]?0xff6FEDFB:0xffDDF4C4);
      layer.rect(textSize/2,tby,w-textSize/2,textSize);
      UITools.rectFrame(layer,textSize/2,tby,w-textSize/2,textSize);
      layer.fill(0);
      layer.text(ts,ts_d2,ty);
      ty+=textSize;
    }
    layer.endDraw();
  }
  @Override
  public void update() {
    point.update();
    if(parent.index==1) {
      if(cooling>0) {
        cooling--;
        if(cooling==0&&names[1][0].charAt(0)=='*') {
          names[1][0]=names[1][0].substring(1);
          refresh();
        }
      }else {
        switch(state[parent.index]) {
          case 1: {
            p.saveBytes(System.getProperty("user.dir")+
              "/data/saved/data.bytes",
              cellCenter.toData().array());
            names[1][0]="*已保存";
            cooling=coolingTime;
          }
            break;
          case 2: {
            byte[] loadBytes=p.loadBytes(System.getProperty("user.dir")+
              "/data/saved/data.bytes");
            if(loadBytes!=null) {
              cellCenter.fromData(ByteBuffer.wrap(loadBytes));
              parent.toolBar.select=null;
              parent.toolBar.originalId=-1;
              parent.toolBar.near.clear();
              names[1][0]="*已加载";
              cooling=coolingTime;
            }else {
              names[1][0]="*无此文件";
              cooling=coolingTime;
            }
          }
            break;
          case 3: {
            names[1][0]="功能未实现";
          }
            break;
          case 4: {
            names[1][0]="功能未实现";
          }
            break;
        }
        if(state[parent.index]!=0) {
          state[parent.index]=0;
          refresh();
        }
      }
    }
  }
  @Override
  public void display() {
    p.image(layer,point.pos.x,point.pos.y);
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
