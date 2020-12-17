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

    public Tank myTank = null;

    public int myKillTankSize = 0;

    //public Missile missile;

    Image offScreenImage = null;

    public List<Missile> missiles = new ArrayList<>();

    private List<Tank> robots = new ArrayList<>();

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
        g.drawString("坦克的坐标为["+myTank.getX()+","+myTank.getY()+"]",50,50);
        missiles = missiles.stream().filter(e->e.isLiveAble()).collect(Collectors.toList());
        robots = robots.stream().filter(e->e.isLive()).collect(Collectors.toList());
        for (Tank robot : robots) {
            robot.draw(g);
        }
        for (Missile missile : missiles) {
            missile.draw(g);
        }
        if (myTank.isLive()){
            myTank.draw(g);
        }

    }

    @Override
    public void update(Graphics g) {
        if(offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.GREEN);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    public List<Tank> getRobots() {
        return robots;
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
        tankClient.myTank = new Tank(500,500,tankClient,true);
        Tank robot1 = new Tank(80, 80, tankClient,false);
        Tank robot2 = new Tank(60, 200, tankClient,false);
        Tank robot3 = new Tank(88, 200, tankClient,false);
        Tank robot4 = new Tank(200, 100, tankClient,false);
        Tank robot5 = new Tank(200, 77, tankClient,false);
        Tank robot6 = new Tank(200, 444, tankClient,false);
        tankClient.robots.add(robot1);
        tankClient.robots.add(robot2);
        tankClient.robots.add(robot3);
        tankClient.robots.add(robot4);
        tankClient.robots.add(robot5);
        tankClient.robots.add(robot6);
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
