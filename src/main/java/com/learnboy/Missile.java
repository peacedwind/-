package com.learnboy;

import java.awt.*;
import java.util.List;

/**
 * Missile
 *  炮弹
 * @author: cyx_jay
 * @date: 2020/12/14 16:14
 */
public class Missile {

    private int x;
    private int y;
    Tank.Direction dir;
    TankClient tankClient;
    private boolean liveAble = true;
    //是否是自己家的子弹 ture表示是友军子弹    flase表示是敌人的子弹
    private boolean good;

    private int speedX = 10;
    private int speedY = 10;

    public Missile(int x, int y, Tank.Direction dir,TankClient tankClient,boolean good) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tankClient = tankClient;
        this.good = good;
    }

    public void draw(Graphics g){

        if (!this.liveAble){
            return;
        }
        Color c = g.getColor();
        if (this.good){
            g.setColor(Color.BLACK);
        }else {
            g.setColor(Color.BLUE);
        }
        //内切原
        g.fillOval(x,y,5,5);
        g.setColor(c);
        move();
        //碰撞检测
        List<Tank> robots = tankClient.getRobots();
        //robots.add(tankClient.myTank);
        for (Tank robot : robots) {
            boolean b = hitTank(robot);
            if (b){
                return;
            }
        }
        //检测是否自己中枪
        this.hitTank(tankClient.myTank);

    }

    private void move(){
        switch (dir){
            case L:
                moveLeft();
                break;
            case LU:
                moveLeft();
                moveUp();
                break;
            case LD:
                moveLeft();
                moveDown();
                break;
            case R:
                moveRight();
                break;
            case RU:
                moveRight();
                moveUp();
                break;
            case RD:
                moveRight();
                moveDown();
                break;
            case U:
                moveUp();
                break;
            case D:
                moveDown();
                break;
            case STOP:
                break;
        }

        this.liveAble = !isOver();

    }

    public boolean isLiveAble() {
        return liveAble;
    }

    public boolean isOver() {

        if (x < 0 || x > TankClient.GAME_WIDTH
                || y < 0 || y >TankClient.GAME_HEIGHT) {
            return true;
        }
        return false;

    }




    //向左移动
    public void moveLeft(){
        this.x -= speedX;
    }

    public void moveRight(){
        this.x += speedX;
    }

    //向左移动
    public void moveUp(){
        this.y -= speedY;
    }

    //向左移动
    public void moveDown(){
        this.y += speedY;
    }

    public boolean hitTank(Tank tank){

        boolean tankType = tank.isGoodTank();
        boolean missileType = this.good;
        if (tankType == missileType){
            return false;
        }

        boolean intersects = getRectangle().intersects(tank.getRectangle());
        if (intersects){
            this.liveAble = false;
            tank.setLive(false);
            if (!tankType){
                tankClient.myKillTankSize++;
                if (tankClient.myKillTankSize % 1 == 0){
                    //升级
                    tankClient.myTank.setLevel(tankClient.myTank.getLevel() +1);
                    System.out.println(tankClient.myTank);
                }
            }
        }


        return intersects;
    }

    public Rectangle getRectangle(){
        return new Rectangle(x,y,5,5);
    }
}
