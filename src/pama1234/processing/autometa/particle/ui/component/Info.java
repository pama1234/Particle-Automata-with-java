package pama1234.processing.autometa.particle.ui.component;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import pama1234.math.Tools;
import pama1234.math.vec.Vec2f;
import pama1234.processing.autometa.particle.ui.UITools;
import pama1234.processing.util.app.UtilApp;
import processing.core.PConstants;

public class Info extends TextBoard implements ClipboardOwner{
  public static int TEXT_SIZE=16;
  String[] data;
  int state;
  float cw;
  public Info(UtilApp p,float x,float y) {
    this(p,x,y,new String[] {
      "(炼蛊模拟器)粒子自动机v1.2\n\n"+
        "目录：\n\n"+
        "1.“非游戏”声明\n"+
        "2. 更新日志\n"+
        "3.操作说明\n"+
        "4.相关链接\n"+
        "5.哲学什么的",
      "首先，请注意！这不是游戏！（如果你是老师，那你可以知道我没打游戏，如果你是家长，那你可以知道你的孩子只是在研究人工生命学）\n\n"+
        "这是一个用于模拟生物的粒子元胞自动机，原理很简单，就是通过使用“非能量守恒式的粒子相互作用”来模拟各种各样的生命形态。",
      "pama_1234: \r\n"+
        "//--- [v1.2更新]\r\n"+
        "1. 打分机制 √\r\n"+
        "2. 保存游戏 √\r\n"+
        "3. 游戏介绍 √\r\n"+
        "4. 开始和设置界面 √\r\n"+
        "5. 联机版\r\n"+
        "(以及更新日志 √)\r\n"+
        "//--- [v1.3更新]\r\n"+
        "1. 更好的美工\r\n"+
        "2. 完善设置界面\r\n"+
        "    a. 固定按钮位置",
      "操作说明：右键拖拽相机位置，滚轮调整相机距离，左键选择菜单或单个粒子，“wasd键”微调相机或粒子位置,“↑↓←→”键用于浏览元信息\n\n"+
        "v1.1添加特性：空格用于暂停当前游戏，e键和q键可以暂时的改变选中的粒子的颜色（在取消选择时会变回原来的颜色，以避免粒子系统失衡）\n\n"+
        "v1.2添加特性：添加了开始界面，加载及保存游戏，空的设置界面，以及打分机制，分数可以从沙盒顶上的盒子中看到，每个粒子都会被系统打分，而这块计分板只会显示你选中的粒子，所有粒子的分数都和其身后的“虫子”的长度和宽度正相关。存档工具栏的“存档”和“读档”按钮用于记录当前沙盒的数据，而后面两个按钮暂未实现，请注意！！你只有一个存档槽，请谨慎存档，每次存档都会覆盖原先的游戏记录",
      "这个模拟器是pama1234做的，但是原理是别人的，参考以下链接：\n"+
        "https://www.youtube.com/watch?v=Z_zmZ23grXE\n"+
        "http://www.ventrella.com/Clusters/",
      "抄自百度百科：\n\n格式塔作为心理学术语，具有两种含义：一指事物的一般属性，即形式；一指事物的个别实体，即分离的整体，形式仅为其属性之一。也就是说，“假使有一种经验的现象，它的每一成分都牵连到其他成分；而且每一成分之所以有其特性，即因为它和其他部分具有关系，这种现象便称为格式塔。”总之，格式塔不是孤立不变的现象，而是指通体相关的完整的现象。完整的现象具有它本身完整的特性，它既不能割裂成简单的元素，同时它的特性又不包含于任何元素之内。\n\n"+
        "这里的粒子元胞自动机，如果非得分类一下，则可以分为格式塔人工生命流派，别问我为什么，我也不知道，你当成我瞎猜的即可"});
  }
  public Info(UtilApp p,float x,float y,String[] data) {
    super(p,x,y,TEXT_SIZE*20,TEXT_SIZE*40,TEXT_SIZE);
    this.data=data;
    initLayer();
    refresh();
  }
  @Override
  public void drawLayer() {
    layer.beginDraw();
    layer.background(0xff4D3C94);
    UITools.border(layer,0,0,layer.width,layer.height);
    layer.fill(0xff006799);
    layer.rect(0,0,data.length*TEXT_SIZE,TEXT_SIZE);
    layer.fill(0xff2A00FF);
    layer.rect(state*TEXT_SIZE,0,TEXT_SIZE,TEXT_SIZE);
    layer.fill(255);
    float ty=-layer.textDescent()/2;
    for(int i=0;i<data.length;i++) {
      layer.text(i,i*TEXT_SIZE,ty);
      UITools.border(layer,i*TEXT_SIZE,0,TEXT_SIZE,TEXT_SIZE);
    }
    layer.text(data[state],TEXT_SIZE/2,TEXT_SIZE*2,layer.width-TEXT_SIZE/2,layer.height);
    layer.fill(0xffFB612E);
    layer.rect(layer.width-(cw=layer.textWidth("Ctrl-C")),0,cw,TEXT_SIZE);
    layer.fill(255);
    layer.text("Ctrl-C",layer.width-cw,ty);
    UITools.border(layer,layer.width-cw,0,cw,TEXT_SIZE);
    layer.endDraw();
  }
  public void refresh() {
    initLayer();
    drawLayer();
  }
  @Override
  public void create() {}
  @Override
  public void update() {
    point.update();
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
    if(button==PConstants.LEFT) {
      if(Tools.inBox(
        p.cam.mouseX,p.cam.mouseY,
        pos.x,pos.y,
        data.length*TEXT_SIZE-1,TEXT_SIZE)) {
        state=(int)Math.floor((Math.ceil(p.cam.mouseX)-pos.x)/TEXT_SIZE);
        refresh();
      }else if(Tools.inBox(
        p.cam.mouseX,p.cam.mouseY,
        pos.x+layer.width-cw,pos.y,
        cw,TEXT_SIZE)) {
          Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(data[state]),this);
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
  @Override
  public void lostOwnership(Clipboard clipboard,Transferable contents) {
    System.out.println(clipboard+" "+contents);
  }
}
