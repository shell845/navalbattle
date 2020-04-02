package com.shell.navalbattle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @author YC 03/29/2020
 */

public class Explode extends AbstractGameObject {
    private int xPos, yPos;
    private boolean alive;
    private BufferedImage[] currImages;
    private int num;

    public Explode(int x, int y) {
        this.xPos = x;
        this.yPos = y;
        this.alive = true;
        this.num = 1;

        currImages = ResourceMgr.explodes;
    }

    public int getPosX() {
        return this.xPos;
    }

    public int getPosY() {
        return this.yPos;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public void setAlive(Boolean status) {
        this.alive = status;
    }

    public Groups getGroup() { return Groups.Neutral; }

    // paint ship
    public void paint(Graphics g) throws IOException {
        if (!isAlive()) return;
        g.drawImage(currImages[num], xPos, yPos - currImages[num].getHeight() / 2, null);
        num++;
        if (num >= 6) {
            setAlive(false);
            num = 1;
        }
    }
}
