package com.learnboy.snake;

import java.awt.*;

/**
 * XY
 *
 * @author: cyx_jay
 * @date: 2020/12/16 16:13
 */
public class XY {
    int x;
    int y;

    public XY(int x, int y) {
        this.x = x;
        this.y = y;
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
        //活出自己
        Color c = g.getColor();

        g.setColor(Color.RED);
        //内切原
        g.drawRect(x,y,10,10);
        g.setColor(c);

    }

    public Rectangle getRectangle(){
        return new Rectangle(x,y,10,10);
    }
}
