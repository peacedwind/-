package com.learnboy.snake;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;

/**
 * Snake
 *
 * @author: cyx_jay
 * @date: 2020/12/16 15:38
 */
public class Snake {

    private LinkedList<XY> snakeXYs = new LinkedList<>();

    public LinkedList<XY> getSnakeXYs() {
        return snakeXYs;
    }

    private SnakeClient snakeClient;
    public Snake(SnakeClient snakeClient) {
        this.snakeClient = snakeClient;
    }


    public void setSnakeXYs(LinkedList<XY> snakeXYs) {
        this.snakeXYs = snakeXYs;
    }

    public void draw(Graphics g) {
        if (!snakeClient.gameStop){
            move();
        }
        for (XY snakeXY : snakeXYs) {

            snakeXY.draw(g);

        }
    }

    enum Direction{L,U,R,D,STOP};

    public Direction dir = Direction.STOP;

    public static void main(String[] args) {


    }

    public void moveByKey(KeyEvent keyEvent){

        int keyCode = keyEvent.getKeyCode();
        int vkRight = KeyEvent.VK_RIGHT;
        int vkLeft = KeyEvent.VK_LEFT;
        int vkUp = KeyEvent.VK_UP;
        int vkDown = KeyEvent.VK_DOWN;
        int vkY = KeyEvent.VK_Y;
        int vkN = KeyEvent.VK_N;

        if (snakeClient.gameOverFlag){
            if (keyCode == vkN){
                System.exit(0);
            }
            if (keyCode == vkY){
                snakeClient.newGame();
                return;
            }
        }else {
            if (!snakeClient.gameStop){
                if (keyCode == KeyEvent.VK_S){
                    snakeClient.gameStop = true;
                    return;
                }
            }else {
                if (keyCode == KeyEvent.VK_S){
                    snakeClient.gameStop = false;
                    return;
                }
            }
        }



        //右边
        if (keyCode == vkRight){
            dir = Direction.R;
        }
        //左边
        if (keyCode == vkLeft){
            dir = Direction.L;
        }

        if (keyCode == vkUp){
            dir = Direction.U;
        }

        if (keyCode == vkDown){
            dir = Direction.D;
        }


    }

    private void move() {

        if (dir == Direction.STOP){
            return;
        }
        try {
            XY last = this.snakeXYs.removeLast();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        XY first = this.snakeXYs.getFirst();
        XY newFirst = createNewFirstXy(first);

        //边界检测
        boolean isBound = isBound(newFirst);
        if (isBound){
            snakeClient.gameOverFlag = true;
            return;
        }
        this.snakeXYs.addFirst(newFirst);


    }

    public void eatFood(){
        XY first = snakeXYs.getFirst();
        XY newFirstXy = createNewFirstXy(first);
        snakeXYs.addFirst(newFirstXy);
    }

    private XY createNewFirstXy(XY first) {
        //获取方向
        int x = first.x;
        int y = first.y;
        Direction dir = this.dir;
        switch (dir){
            case L: x -= 10;break;
            case R: x += 10;break;
            case U: y -= 10;break;
            case D: y += 10;break;
        }

        return new XY(x,y);

    }

    private boolean isBound(XY newFirst) {
        if (newFirst.x <=0 || newFirst.x >= SnakeClient.GAME_WIDTH-10){
            return true;
        }

        if (newFirst.y <=30 || newFirst.y>=SnakeClient.GAME_HEIGHT-10){
            return true;
        }
        return false;
    }

    public void cancleByKey(KeyEvent e) {


    }

    public void  addXy(int x,int y ){
        XY xy = new XY(x, y);
        this.snakeXYs.add(xy);
    }
}
