package pama1234.processing.util;

import static processing.core.PApplet.dist;

import java.util.function.IntUnaryOperator;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;

public class UITools{
  public static int weight=1;
  public static void reversedBorder(final PGraphics l,final float x,final float y,float w,float h) {
    border(l,x,y,w,h,0x80000000,0x80ffffff);
  }
  public static void border(final PGraphics l,final float x,final float y,float w,float h,int a,int b) {
    final int tc=l.fillColor;
    final boolean ts=l.stroke;
    l.noStroke();
    l.fill(a);
    l.rect(x,y,w,weight);
    l.rect(x,y,weight,h);
    l.fill(b);
    l.rect(x,y+h-weight,w,weight);
    l.rect(x+w-weight,y,weight,h);
    l.fill(tc);
    l.stroke=ts;
  }
  public static void border(final PGraphics l,final float x,final float y,float w,float h) {
    border(l,x,y,w,h,0x80ffffff,0x80000000);
  }
  public static void rectBorder(final PGraphics l) {
    border(l,0,0,l.width,l.height);
  }
  public static void cross(final PGraphics l,float x,float y,float a,float b) {
    l.line(x-a,y,x+a,y);
    l.line(x,y-b,x,y+b);
  }
  public static void arrow(final PGraphics l,float x,float y,float a,float b,float s) {
    a*=-1;
    b*=-1;
    float mag=s/PApplet.mag(a,b);
    float ang=(b>0?PApplet.atan(a/b)+PApplet.QUARTER_PI+PApplet.HALF_PI:PApplet.atan(a/b)+PApplet.QUARTER_PI);
    a*=mag;
    b*=mag;
    l.line(x+a,y+b,x+PApplet.sin(ang)*s,y+PApplet.cos(ang)*s);
    ang+=b>0?PApplet.HALF_PI:-PApplet.HALF_PI;
    l.line(x+a,y+b,x+PApplet.sin(ang)*s,y+PApplet.cos(ang)*s);
  }
  public static void rectArrow(PGraphics g,float x,float y,float w,float h,float size) {
    float scale=dist(0,0,w,h);
    drawRectArraw(g,x,y,w/scale*size,h/scale*size);
  }
  public static void drawRectArraw(PGraphics g,float x,float y,float w,float h) {
    g.line(x+h,y-w,x+w,y+h);
    g.line(x-h,y+w,x+w,y+h);
  }
  public static void textLine(PGraphics l,float x,float y,float w,float h,String s,IntUnaryOperator foreground,IntUnaryOperator background,LineTextWidthConsumer consumer) {
    float u=h/l.textFont.getSize();
    float ty=y;
    y+=(1-l.textFont.descent()+(1-(l.textFont.ascent()+l.textFont.descent()))/2)*h;
    for(int i=0;i<s.length();i++) {
      l.tint(foreground.applyAsInt(i));
      char tc=s.charAt(i);
      PFont.Glyph gp=l.textFont.getGlyph(tc);
      float tw=Math.round(gp.setWidth/w)*w;
      int bc=background.applyAsInt(i);
      if((bc>>>24)>0) {
        l.fill(bc);
        l.rect(x,ty,tw,h);
      }
      l.image(gp.image,x+gp.leftExtent*u,y-gp.topExtent*u,gp.width*u,gp.height*u);
      consumer.put(i,tw,x);
      x+=tw;
    }
  }
  public static void textWidth(PGraphics l,float w,String s,LineTextWidthConsumer consumer) {
    float x=0;
    for(int i=0;i<s.length();i++) {
      float tw=Math.round(l.textFont.getGlyph(s.charAt(i)).setWidth/w)*w;
      consumer.put(i,tw,x);
      x+=tw;
    }
  }
  public static float textWidth(PGraphics l,float w,String s) {
    float x=0;
    for(int i=0;i<s.length();i++) x+=Math.round(l.textFont.getGlyph(s.charAt(i)).setWidth/w)*w;
    return x;
  }
  @FunctionalInterface
  public interface LineTextWidthConsumer{
    public void put(int xPos,float charWidth,float TextWidth);
  }
}
