package com.learnboy;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TankClient
 * 坦克客户端
 * @author: cyx_jay
 * @date: 2020/12/14 11:40
 */
public class TankClient extends Frame {


    public static final int GAME_HEIGHT = 600;

    public static final int GAME_WIDTH = 800;

    public static final int TANK_HEIGHT = 30;

    public static final int TANK_WIDTH = 30;

    private Tank myTank = null;

    //public Missile missile;

    public List<Missile> missiles = new ArrayList<>();

    int x= 50 ,y=50;
    /**
     * @Description  画图
     * @Date 2020/12/14 11:59
     * @Param
     * @return
     */
    @Override
    public void paint(Graphics g){
       // this.setTitle("炮弹数量为"+missiles.size());
        g.drawString("炮弹数量为"+missiles.size(),50,50);
        missiles = missiles.stream().filter(e->e.isLiveAble()).collect(Collectors.toList());
        for (Missile missile : missiles) {
            missile.draw(g);
        }
        myTank.draw(g);

    }

    public void lauchFrame(){
        this.setLocation(400,300);
        this.setSize(GAME_WIDTH,GAME_HEIGHT);
        this.setTitle("坦克大战");
        //添加关闭事件  匿名类 不需要太多的业务为逻辑
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        //不允许改变大小
        this.setResizable(false);
        //可见
        this.setVisible(true);
        this.setBackground(Color.GREEN);
        //添加键盘监听
        this.addKeyListener(new KeyMonitor());
        //画出坦克
        new Thread(new PaintThread()).start();


    }

    public static void main(String[] args) {
        TankClient tankClient = new TankClient();
        tankClient.myTank = new Tank(50,50,tankClient);
        tankClient.lauchFrame();
    }



    private class PaintThread implements Runnable {

        @Override
        public void run() {
            while (true){
                repaint();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class KeyMonitor extends KeyAdapter{

        @Override
        public void keyPressed(KeyEvent e) {
            myTank.moveByKey(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            myTank.cancleByKey(e);
        }
    }


}
