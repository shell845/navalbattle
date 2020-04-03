package com.shell.navalbattle.gameobjects;

import com.shell.navalbattle.NavalFrame;
import com.shell.navalbattle.weaponmode.DefaultWeaponModel;
import com.shell.navalbattle.weaponmode.WeaponModel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @author YC 03/29/2020
 */

public class SubmarineEnemy extends Submarine {
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

    public Groups getGroup() { return this.group; }

    public boolean isAlive() { return this.alive; }

    public void setAlive(Boolean status) {
        this.alive = status;
    }

    public void reduceLives() { }

    public Directions getDirection() { return this.dir; }

    public void moveBack() { move(); }

    // paint ship
    public void paint(Graphics g) throws IOException {
        if (!isAlive()) return;
        g.drawImage(currImage, xPos, yPos, null);
        move();
    }

    // create weapon
    private void shoot() {
        WeaponModel model = new DefaultWeaponModel();
        model.shoot(this);
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

        if (random.nextInt(100) > 95) {
            shoot();
        }
    }

    private static Random random = new Random();

    private void setRandomDirection() {
        this.dir = Directions.randomDirection();
    }

}
