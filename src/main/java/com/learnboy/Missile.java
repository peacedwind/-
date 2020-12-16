package com.learnboy;

import java.awt.*;

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

    private int speedX = 10;
    private int speedY = 10;

    public Missile(int x, int y, Tank.Direction dir,TankClient tankClient) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tankClient = tankClient;
    }

    public void draw(Graphics g){

        Color c = g.getColor();
        g.setColor(Color.BLACK);
        //内切原
        g.fillOval(x,y,5,5);
        g.setColor(c);
        move();
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
}
