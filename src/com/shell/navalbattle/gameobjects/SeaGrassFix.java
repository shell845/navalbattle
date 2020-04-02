/**
 * @author YC 04/02/2020
 */

package com.shell.navalbattle.gameobjects;

import com.shell.navalbattle.utils.PropertyMgr;
import com.shell.navalbattle.utils.ResourceMgr;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SeaGrassFix extends SeaGrass {
    private BufferedImage currImage;
    private static final int SPEED = 3;
    private static int START;
    private int xPos, yPos;
    private boolean alive;
    private Groups group;

    public SeaGrassFix() {
        currImage = ResourceMgr.seagrassFix;
        xPos = Integer.parseInt(PropertyMgr.getConfig("FrameWidth"));
        yPos = Integer.parseInt(PropertyMgr.getConfig("FrameHeight")) - currImage.getHeight();
        START = xPos;
        alive = true;
        group = Groups.Neutral;
    }

    public int getPosX() {
        return this.xPos;
    }

    public int getPosY() {
        return this.yPos;
    }

    public int getWidth() { return this.currImage.getWidth(); }

    public int getHeight() { return this.currImage.getHeight(); }

    public Groups getGroup() { return this.group; }

    public boolean isAlive() {
        return this.alive;
    }

    public void setAlive(Boolean status) {
        this.alive = status;
    }

    @Override
    public void paint(Graphics g) throws IOException {
        g.drawImage(currImage, xPos,
                yPos, null);
        move();
    }

    private void move() {
        if (xPos <= - getWidth()) xPos = START;
        xPos -= SPEED;
    }
}
