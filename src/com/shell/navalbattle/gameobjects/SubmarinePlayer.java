package com.shell.navalbattle.gameobjects;

import com.shell.navalbattle.*;
import com.shell.navalbattle.model.DefaultWeaponModel;
import com.shell.navalbattle.model.DoubleWeaponModel;
import com.shell.navalbattle.model.WeaponModel;
import com.shell.navalbattle.utils.PropertyMgr;
import com.shell.navalbattle.utils.ResourceMgr;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @author YC 03/29/2020
 */

public class SubmarinePlayer extends Submarine {
    public static final int SPEED = 5;
    public static final int TOP_MARGIN = 20;
    private int xPos, yPos;
    private Directions dir;
    private Directions currentLR;
    private Groups group;
    private boolean alive;
    private boolean pressL, pressR, pressU, pressD;
    private boolean isMoving;
    private BufferedImage currImage;
    private int lives;

    public SubmarinePlayer(int x, int y, Directions d) {
        this.xPos = x;
        this.yPos = y;
        this.dir = d;
        this.group = Groups.Friend;
        this.alive = true;
        this.isMoving = false;
        this.lives = Integer.parseInt(PropertyMgr.getConfig("defaultLives"));
        currImage = ResourceMgr.submarineYellowR;
        currentLR = d;

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

    public boolean isAlive() {
        return this.alive;
    }

    public void setAlive(Boolean status) {
        this.alive = status;
    }

    public void reduceLives() {
        this.lives--;
    }

    public int getLives() {
        return this.lives;
    }

    public Directions getDirection() { return currentLR; }

    // paint ship
    public void paint(Graphics g) throws IOException {
        if (!isAlive()) return;
        setImage();
        g.drawImage(currImage, xPos, yPos, null);
        move();
    }

    private void setImage() {
        if (currentLR == Directions.R) {
            if (lives >= 6) {
                currImage = ResourceMgr.submarineYellowR;
            } else if (lives <= 0) {
                    setAlive(false);
                } else if (lives < 3) {
                    currImage = ResourceMgr.submarineYellowRS;
                } else {
                    currImage = ResourceMgr.submarineYellowRM;
                }
            } else {
                if (lives >= 6) {
                    currImage = ResourceMgr.submarineYellowL;
                } else if (lives <= 0) {
                    setAlive(false);
                } else if (lives < 3) {
                    currImage = ResourceMgr.submarineYellowLS;
                } else {
                    currImage = ResourceMgr.submarineYellowLM;
                }
            }
    }

    // detect key press
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                pressL = true;
                break;
            case KeyEvent.VK_UP:
                pressU = true;
                break;
            case KeyEvent.VK_RIGHT:
                pressR = true;
                break;
            case KeyEvent.VK_DOWN:
                pressD = true;
                break;
        }
        setDirection();
    }

    // detect key release
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                pressL = false;
                break;
            case KeyEvent.VK_UP:
                pressU = false;
                break;
            case KeyEvent.VK_RIGHT:
                pressR = false;
                break;
            case KeyEvent.VK_DOWN:
                pressD = false;
                break;
            case KeyEvent.VK_SPACE:
                shoot();
                break;
            case KeyEvent.VK_CONTROL:
                resetLeftRight();
                break;
        }
        setDirection();
    }

    private void resetLeftRight() {
        if (currentLR == Directions.R) {
            currentLR = Directions.L;
            setImage();
            return;
        }
        if (currentLR == Directions.L) {
            currentLR= Directions.R;
            setImage();
            return;
        }
    }

    private Random random = new Random();
    // create weapon
    private void shoot() {
        WeaponModel model = null;
        if (random.nextInt(100) > 65) model = new DoubleWeaponModel();
        else model = new DefaultWeaponModel();
        model.shoot(this);
        NavalFrame.MAIN_FRAME.defaultWeaponNum--;
    }

    private void setDirection() {
        if (!pressL && !pressR && !pressU && !pressD) {
            isMoving = false; // stop when all keys are released
        } else {
            if (pressL && !pressR && !pressU && !pressD) dir = Directions.L;
            if (!pressL && pressR && !pressU && !pressD) dir = Directions.R;
            if (!pressL && !pressR && pressU && !pressD) dir = Directions.U;
            if (!pressL && !pressR && !pressU && pressD) dir = Directions.D;
            isMoving = true;
        }
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
    }

    private void boundaryCheck() {
        if (xPos > NavalFrame.FRAME_WIDTH || xPos < NavalFrame.FRAME_LOCATION_X
                || yPos > NavalFrame.FRAME_HEIGHT || yPos < NavalFrame.FRAME_LOCATION_Y) {
            //
        }
    }

}
