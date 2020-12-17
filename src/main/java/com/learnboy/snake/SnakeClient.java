package com.learnboy.snake;



import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.Random;


/**
 * SnakeClient
 * 贪吃蛇
 * @author: cyx_jay
 * @date: 2020/12/16 14:58
 */
public class SnakeClient extends Frame {


    public static final int GAME_WIDTH = 800;

    public static final int GAME_HEIGHT = 600;

    private Snake snake;

    public static final int snakeSpeed = 10;
    private Image offScreenImage;

    private static final Random random = new Random();
    private Food food = new Food(100,100);
    private int score = 0;

    public boolean gameOverFlag = false;

    public boolean gameStop = false;

    public boolean showLastPicture = true;

    private boolean newGame = true;

    @Override
    public void paint(Graphics g) {


        if (snake.dir == Snake.Direction.STOP){
            g.drawString("按下S暂停",60,60);
            //newGame = !newGame;
        }

        if (gameOverFlag){
            g.drawString("游戏结束了",GAME_HEIGHT/2,GAME_WIDTH/2);
            g.drawString("是否重新开始,按下Y重新开始,按下N结束",GAME_HEIGHT/2+10,GAME_HEIGHT/2+10);
            return;
        }

        if (gameStop){
            g.drawString("游戏暂停了",GAME_HEIGHT/2,GAME_WIDTH/2);
            g.drawString("按下S开始",GAME_HEIGHT/2+10,GAME_WIDTH/2+10);
            snake.draw(g);
            food.draw(g,snake);
            return;
        }
        showLastPicture = true;
        if (!food.isLive()){
            createNewFood(food);
        }
        g.drawString("已经吃到了"+score,50,50);
        snake.draw(g);
        food.draw(g,snake);
    }


    public void  newGame(){

        Snake snake = new Snake(this);
        snake.addXy(50,50);
        snake.addXy(60,50);
        this.snake = snake;
        score = 0;
        this.gameOverFlag = false;
        this.newGame = true;
        this.food = new Food(100,100);

    }
    private void createNewFood(Food food) {
        int x = random.nextInt(GAME_WIDTH);
        int y = random.nextInt(GAME_HEIGHT-30)+30;
        food.setX(x);
        food.setY(y);
        food.setLive(true);
        score++;
    }

    public static void main(String[] args) {
        SnakeClient snakeClient = new SnakeClient();
        Snake snake = new Snake(snakeClient);
        snake.addXy(50,50);
        snake.addXy(60,50);
        snakeClient.snake = snake;
        snakeClient.lauchFrame();
    }

    public void lauchFrame(){
        this.setLocation(400,300);
        this.setSize(GAME_WIDTH,GAME_HEIGHT);
        this.setTitle("贪吃蛇");

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
        //画出🐍
        new Thread(()->{
            while (true) {
                this.repaint();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();

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


    private class KeyMonitor extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            snake.moveByKey(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            snake.cancleByKey(e);
        }
    }



}
