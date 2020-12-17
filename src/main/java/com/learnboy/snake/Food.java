package com.learnboy.snake;

import java.awt.*;
import java.util.LinkedList;

/**
 * Food
 *
 * @author: cyx_jay
 * @date: 2020/12/16 21:11
 */
public class Food {
    public int getX() {
        return x;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    private boolean live = true;

    public Food(int x, int y) {
        this.x = x;
        this.y = y;
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

    int x;
    int y;


    public void draw(Graphics g,Snake snake) {
        if (this.isLive()) {
            this.hit(snake);
            Color color = g.getColor();
            g.setColor(Color.BLUE);
            g.fillOval(x, y, 10, 10);
            g.setColor(color);

        }
    }

    public Rectangle getReange(){
       return new Rectangle(x,y,10,10);
    }

    public void hit(Snake snake){
        LinkedList<XY> snakeXYs = snake.getSnakeXYs();
        XY first = snakeXYs.getFirst();
        //判断是否相撞
        Rectangle rectangle = first.getRectangle();
        Rectangle reange = this.getReange();
        if (rectangle.intersects(reange)){
            //相撞
            snake.eatFood();
            this.setLive(false);

        }

    }



}
