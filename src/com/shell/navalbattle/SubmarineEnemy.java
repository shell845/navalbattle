package com.shell.navalbattle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @author YC 03/29/2020
 */

public class SubmarineEnemy {
    public static final int SPEED = 10;
    public static final int TOP_MARGIN = 20;
    private int xPos, yPos;
    private Directions dir;
    private Groups group;
    private boolean alive;
    private boolean isMoving;
    private BufferedImage currImage;

    public SubmarineEnemy(int x, int y, Directions d) {
        this.xPos = x;
        this.yPos = y;
        this.dir = d;
        this.group = Groups.Enemy;
        this.alive = true;
        this.isMoving = true;

        currImage = Submarines.getRandomSubmarine();
        /*switch (this.group) {
            case Friend:
                currImage = ResourceMgr.submarineYellowR;
                break;
            case Enemy:
                currImage = ResourceMgr.submarineGreenL;
                break;
        }*/
    }

    public int getPosX() {
        return this.xPos;
    }

    public int getPosY() {
        return this.yPos;
    }

    public int getWidth() { return this.currImage.getWidth(); }

    public int getHeight() { return this.currImage.getHeight(); }

    public Groups getGroup() { return this.group; };

    public boolean isAlive() {
        return this.alive;
    }

    public void setAlive(Boolean status) {
        this.alive = status;
    }

    // paint ship
    public void paint(Graphics g) throws IOException {
        if (!isAlive()) return;
        g.drawImage(currImage, xPos, yPos, null);
        /*switch (this.dir) {
            case L:
                g.drawImage(ResourceMgr.submarineRedL, xPos, yPos, null);
                currImage = ResourceMgr.submarineRedL;
                break;
            case R:
                g.drawImage(ResourceMgr.submarineRedR, xPos, yPos, null);
                currImage = ResourceMgr.submarineRedR;
                break;
            case U:
            case D:
                g.drawImage(currImage, xPos, yPos, null);
                break;
        }*/

        move();
    }

    // create weapon
    private void shoot() {
        Weapon weapon = new Weapon(this.xPos + currImage.getWidth() / 4, this.yPos + currImage.getHeight() / 8, this.group);
        NavalFrame.MAIN_FRAME.addWeapon(weapon);
    }

    private void move() {
        // stop
        if (!isMoving) return;

        // move
        switch (this.dir) {
            case L:
                if (this.xPos > NavalFrame.FRAME_LOCATION_X) this.xPos -= SPEED;
                break;
            case R:
                if (this.xPos + currImage.getWidth() < NavalFrame.FRAME_WIDTH) this.xPos += SPEED;
                break;
            case U:
                if (this.yPos - TOP_MARGIN > NavalFrame.FRAME_LOCATION_Y) this.yPos -= SPEED;
                break;
            case D:
                if (this.yPos + currImage.getHeight() < NavalFrame.FRAME_HEIGHT) this.yPos += SPEED;
                break;
        }

        if (random.nextInt(100) >= 90) {
            // generate random direction
            setRandomDirection();
        }

        if (random.nextInt(100) >= 90) {
            shoot();
        }
    }

    private static Random random = new Random();

    private void setRandomDirection() {
        this.dir = Directions.randomDirection();
    }

}
