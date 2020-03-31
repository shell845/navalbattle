/**
 * @author YC 03/30/2020
 */

package com.shell.navalbattle;

import java.awt.*;
import java.io.IOException;

public class Weapon {
    private int xPos, yPos;
    private Groups group;
    private Directions dir;
    public static final int SPEED = 10;

    public Weapon (int x, int y, Groups g) {
        this.xPos = x;
        this.yPos = y;
        this.group = g;

        if (g == Groups.Enemy) {
            this.dir = Directions.L;
        } else this.dir = Directions.R;
    }

    public void paint(Graphics g) throws IOException {
        g.drawImage(ResourceMgr.bubbleBlue, xPos + ResourceMgr.bubbleBlue.getWidth() / 2, yPos, null);
        move();
    }

    public int getPosX() {
        return this.xPos;
    }

    public int getPosY() {
        return this.yPos;
    }

    private void move() {
        switch (this.dir) {
            case L:
                this.xPos -= SPEED;
                break;
            case R:
                this.xPos += SPEED;
                break;
//            case U:
//                this.yPos += SPEED;
//                break;
//            case D:
//                this.yPos -= SPEED;
//                break;
        }
    }
}
