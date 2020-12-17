package com.learnboy;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Tank
 *
 * @author: cyx_jay
 * @date: 2020/12/14 15:02
 */
public class Tank {
    //坦克的位子
    private int x;
    private int y;
    //等级
    private int level = 1;
    int step = 0;
    private int frequency = random.nextInt(10)+1;

    private boolean bLeft = false, bUp = false,bRight = false,bDown = false;

    private TankClient tankClient;

    private Direction lastDir;
    static Random random = new Random();

    public void setLive(boolean live) {
        this.live = live;
    }

    //朝向
    private Direction toDir = Direction.D;

    private boolean goodTank;

    private boolean live = true;


    public boolean isLive() {
        return live;
    }

    public void cancleByKey(KeyEvent e) {
        int keyCode = e.getKeyCode();
        int vkRight = KeyEvent.VK_RIGHT;
        int vkLeft = KeyEvent.VK_LEFT;
        int vkUp = KeyEvent.VK_UP;
        int vkDown = KeyEvent.VK_DOWN;

        //右边
        if (keyCode == vkRight){
            bRight = false;

        }
        //左边
        if (keyCode == vkLeft){
            bLeft = false;

        }

        if (keyCode == vkUp){
            bUp = false;

        }

        if (keyCode == vkDown){
            bDown = false;

        }

        locateDirection();

    }

    enum Direction{L,LU,U,RU,R,RD,LD,D,STOP};

    private Direction dir = Direction.STOP;

    //坦克的速度
    private int speedX = 5;

    private int speedY = 5;

    public Tank(int x,int y,TankClient tc,boolean goodTank){
        this(x,y,goodTank);
        this.tankClient = tc;
    }


    public Tank(int x, int y,boolean goodTank) {
        this.x = x;
        this.y = y;
        this.goodTank = goodTank;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void draw(Graphics g){
        Color c = g.getColor();
        if (this.goodTank){
            g.setColor(Color.RED);
        }else {
            g.setColor(Color.BLUE);
        }
        //内切原
        g.fillOval(x,y,30,30);
        g.setColor(c);
        //画炮筒
        drawToDir(g);
        move();



    }

    private void drawToDir(Graphics g) {

        switch (toDir){
            case L:
                g.drawLine(getTankCenterX(),getTankCenterY(),getTankLeftX(),getTankLeftY());
                break;
            case LU:
                g.drawLine(getTankCenterX(),getTankCenterY(),getTankLeftUpX(),getTankUpY());
                break;
            case LD:
                g.drawLine(getTankCenterX(),getTankCenterY(),getTankLeftDownX(),getTankLeftDownY());
                break;
            case R:
                g.drawLine(getTankCenterX(),getTankCenterY(),getTankRightX(),getTankRightY());
                break;
            case RU:
                g.drawLine(getTankCenterX(),getTankCenterY(),getTankRightUpX(),getTankRightUpY());
                break;
            case RD:
                g.drawLine(getTankCenterX(),getTankCenterY(),getTankRightDownX(),getTankRightDownY());
                break;
            case U:
                g.drawLine(getTankCenterX(),getTankCenterY(),getTankUpX(),getTankUpY());
                break;
            case D:
                g.drawLine(getTankCenterX(),getTankCenterY(),getTankDownX(),getTankDownY());
                break;

        }

    }


    void  reset(){
        this.bRight = false;
        this.bUp = false;
        this.bDown = false;
        this.bLeft = false;
        this.dir = Direction.STOP;
    }

    public boolean isGoodTank() {
        return goodTank;
    }

    private void move(){
       this.newDir();

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

        if (dir != Direction.STOP){
            //改变炮筒方向
            toDir = dir;
        }
    }

    private void newDir() {
        if (this.goodTank){
            return;
        }
        step++;
        if (step % frequency == 0){
            Direction[] values = Direction.values();
            int i = random.nextInt(values.length);
            this.dir = values[i];
        }
        if (step % 3 == 0){

            //this.shooting();
        }
    }


    public void moveByKey(KeyEvent keyEvent){

        int keyCode = keyEvent.getKeyCode();
        int vkRight = KeyEvent.VK_RIGHT;
        int vkLeft = KeyEvent.VK_LEFT;
        int vkUp = KeyEvent.VK_UP;
        int vkDown = KeyEvent.VK_DOWN;

        //右边
        if (keyCode == vkRight){
            bRight = true;
        }
        //左边
        if (keyCode == vkLeft){
            bLeft = true;
        }

        if (keyCode == vkUp){
           bUp = true;
        }

        if (keyCode == vkDown){
            bDown = true;
        }

        if (keyCode == KeyEvent.VK_X && isLive()){
            //发射子弹
            shooting();
            return;
        }

        locateDirection();


    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    void shooting(){
        if (lastDir == null ){
            lastDir = Direction.U;
        }

        if (lastDir == Direction.STOP){
            lastDir = dir;
        }
        for (int i = 0; i < level; i++) {
            Missile missile = new Missile(getTankCenterX()+i*10, getTankCenterY()+i*10, toDir,this.tankClient,this.goodTank);
            tankClient.missiles.add(missile);
        }
    }

    //向左移动
    public void moveLeft(){
        if (x != 0){
            this.x -= speedX;
        }
    }

    public void moveRight(){
        if (x < TankClient.GAME_WIDTH- TankClient.TANK_WIDTH){
            this.x += speedX;
        }
    }

    //向左移动
    public void moveUp(){
        if (y > 30){
            this.y -= speedY;
        }
    }

    //向左移动
    public void moveDown(){
        if (y < TankClient.GAME_HEIGHT - TankClient.TANK_HEIGHT){
            this.y += speedY;
        }
    }


    public void locateDirection(){
        this.lastDir = dir;
        //上
        if (bUp && !bLeft && !bDown && !bRight){
            dir = Direction.U;
        }

        //下
        if (!bUp && !bLeft && bDown && !bRight){
            dir = Direction.D;
        }
        //左
        if (!bUp && bLeft && !bDown && !bRight){
            dir = Direction.L;
        }
        //右
        if (!bUp && !bLeft && !bDown && bRight){
            dir = Direction.R;
        }
        //左上
        if (bUp && bLeft && !bDown && !bRight){
            dir = Direction.LU;
        }
        //左下
        if (!bUp && bLeft && bDown && !bRight){
            dir = Direction.LD;
        }
        //右上
        if (bUp && !bLeft && !bDown && bRight){
            dir = Direction.RU;
        }
        //右下
        if (!bUp && !bLeft && bDown && bRight){
            dir = Direction.RD;
        }
        //STOP
        if (!bUp && !bLeft && !bDown && !bRight){
            dir = Direction.STOP;
        }



    }



    int getTankCenterX(){
        return x+TankClient.TANK_WIDTH/2;
    }

    int getTankCenterY(){
        return y+TankClient.TANK_HEIGHT/2;
    }

    int getTankLeftUpX(){
        return x;
    }

    int getTankLeftUpY(){
        return y;
    }

    int getTankLeftDownX(){
        return x;
    }

    int getTankLeftDownY(){
        return y+TankClient.TANK_HEIGHT;
    }

    //右上
    int getTankRightUpX(){
        return x+TankClient.TANK_WIDTH;
    }

    int getTankRightUpY(){
        return y;
    }
    //右下
    int getTankRightDownX(){
        return x+TankClient.TANK_WIDTH;
    }

    int getTankRightDownY(){
        return y+TankClient.TANK_HEIGHT;
    }

    int getTankLeftX(){
        return x;
    }

    int getTankLeftY(){
        return getTankCenterY();
    }

    int getTankUpX(){
        return getTankCenterX();
    }

    int getTankUpY(){
        return y;
    }

    int getTankRightX(){

        return x + TankClient.TANK_WIDTH;
    }

    int getTankRightY(){
        return getTankCenterY();
    }

    int getTankDownX(){
        return getTankCenterX();
    }

    int getTankDownY(){
        return y + TankClient.TANK_HEIGHT;
    }

   public Rectangle getRectangle(){
        return new Rectangle(x,y,TankClient.TANK_WIDTH,TankClient.TANK_HEIGHT);
   }

}
