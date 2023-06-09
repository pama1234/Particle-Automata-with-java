package pama1234.processing.autometa.particle.ui.component;

import pama1234.processing.util.app.UtilApp;
import processing.core.PConstants;

public abstract class TextBoard extends Component{
  public int textSize=32,w,h;
  public int textAlignX=PConstants.LEFT,textAlignY=PConstants.TOP;
  public TextBoard(UtilApp p,float x,float y,int w,int h,int textSize) {
    super(p,x,y,w,h);
    this.textSize=textSize;
    this.w=w;
    this.h=h;
  }
  public TextBoard(UtilApp p,float x,float y,int w,int h) {
    super(p,x,y,w,h);
    this.w=w;
    this.h=h;
  }
  // @Override
  public void initLayer() {
    layer.beginDraw();
    layer.textFont(p.font);
    layer.textAlign(textAlignX,textAlignY);
    layer.textSize(textSize);
    layer.textLeading(textSize);
    layer.noStroke();
    layer.endDraw();
  }
}
