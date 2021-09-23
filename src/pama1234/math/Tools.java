package pama1234.math;

public class Tools{
  public static float sq(float in) {
    if(in<0) return -(in*in);
    return in*in;
  }
  public static float sigmoid(float in) {
    return (float)(2/(1+Math.pow(Math.E,-in))-1);
  }
  public static final int color(int r,int g,int b,int a) {
    r&=0xff;
    g&=0xff;
    b&=0xff;
    a&=0xff;
    return (a<<24)+(r<<16)+(g<<8)+b;
  }
  static public final float map(float value,float start1,float stop1,float start2,float stop2) {
    return start2+(stop2-start2)*((value-start1)/(stop1-start1));
  }
  public static boolean inRange(float a,float min,float max) {
    return a>min&&a<max;
  }
  public static boolean inBox(float a,float b,float x,float y,float w,float h) {
    return a>x&&a<x+w&&b>y&&b<y+h;
  }
  public static boolean inBox(int a,int b,int x,int y,int w,int h) {
    return a>x&&a<x+w&&b>y&&b<y+h;
  }
  public static boolean intersects(float x1,float y1,float w1,float h1,float x2,float y2,float w2,float h2) {
    if(w2<=0||h2<=0||w1<=0||h1<=0) return false;
    w2+=x2;
    h2+=y2;
    w1+=x1;
    h1+=y1;
    return ((w2<x2||w2>x1)&&
      (h2<y2||h2>y1)&&
      (w1<x1||w1>x2)&&
      (h1<y1||h1>y2));
  }
  public static boolean intersects(int x1,int y1,int w1,int h1,int x2,int y2,int w2,int h2) {
    if(w2<=0||h2<=0||w1<=0||h1<=0) return false;
    w2+=x2;
    h2+=y2;
    w1+=x1;
    h1+=y1;
    return ((w2<x2||w2>x1)&&
      (h2<y2||h2>y1)&&
      (w1<x1||w1>x2)&&
      (h1<y1||h1>y2));
  }
  public static float cutToLastDigit(float in) {
    return (int)(in*10)/10f;
  }
  public static String cutToLastDigitString(float in) {
    return String.valueOf((int)(in*10)/10f);
  }
  public static int color(float r,float g,float b,float a) {
    return color((int)r,(int)g,(int)b,(int)a);
  }
  public static String colorToString(int color) {
    return Integer.toHexString(color);
  }
}
