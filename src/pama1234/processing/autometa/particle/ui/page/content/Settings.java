package pama1234.processing.autometa.particle.ui.page.content;

import pama1234.processing.autometa.particle.ui.UITools;
import pama1234.processing.autometa.particle.ui.component.TextBoard;
import pama1234.processing.util.app.UtilApp;

public class Settings extends TextBoard{
  // public JSONObject data;
  public String[][] names;
  public static int[] data;
  public Settings(UtilApp p) {
    super(p,-320,-320,640,640,32);
    names=new String[][] {{"界面:","跟随视角","定于桌面"}};
    data=new int[names.length];
    // data[0]=1;
    refresh();
  }
  @Override
  public void drawLayer() {
    initLayer();
    layer.beginDraw();
    layer.background(UITools.background);
    UITools.border(layer,0,0,w,h);
    draw();
    layer.endDraw();
  }
  // @Override
  // public void initLayer() {
  //   super.initLayer();
  //   layer.beginDraw();
  //   layer.fill(0);
  //   layer.endDraw();
  // }
  public void draw() {
    for(int i=0;i<names.length;i++) {
      float pos=textSize,ty=textSize*(i+1);
      for(int j=0;j<names[i].length;j++) {
        if(j==data[i]+1) {
          layer.fill(UITools.selectLine);
          float tw=UITools.textWidth(layer,textSize/2,names[i][j]);
          layer.rect(pos,ty,tw,textSize);
          UITools.border(layer,pos,ty,tw,textSize);
        }
        pos+=textSize/2+UITools.textLine(layer,pos,ty,textSize/2,textSize,names[i][j]);
      }
    }
    //  layer.text(data[i],0,i*textSize);
  }
  @Override
  public void refresh() {
    drawLayer();
  }
  @Override
  public void create() {}
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
