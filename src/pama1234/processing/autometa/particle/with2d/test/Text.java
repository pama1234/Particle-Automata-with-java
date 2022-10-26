package pama1234.processing.autometa.particle.with2d.test;

public class Text{
  public static void main(String[] args) {
    String data="12345";
    for(int i=0;i<data.length();i+=10) System.out.println("["+data.substring(i,Math.min(i+10,data.length()))+"]--"+i/10);
  }
}
