package com.shell.navalbattle;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author YC 03/29/2020
 */

public class Ship {
    public static final int SPEED = 5;
    private int xPos, yPos;
    private Directions dir;
    private Groups group;
    private boolean pressL, pressR, pressU, pressD;
    private boolean isMoving = false;
    private BufferedImage currImage;

    public Ship(int x, int y, Directions d, Groups g) {
        this.xPos = x;
        this.yPos = y;
        this.dir = d;
        this.group = g;

        switch (this.group) {
            case Friend:
                currImage = ResourceMgr.submarineYellowR;
                break;
            case Enemy:
                currImage = ResourceMgr.submarineGreenL;
                break;
        }
    }

    // paint ship
    public void paint(Graphics g) throws IOException {
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
        }
        setDirection();
    }

    private void shoot() {
        Weapon weapon = new Weapon(this.xPos + currImage.getWidth() / 2, this.yPos, this.group);
        NavalFrame.MAIN_FRAME.addWeapon(weapon);
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
                if (this.yPos + currImage.getHeight() < NavalFrame.FRAME_HEIGHT) this.yPos += SPEED;
                break;
            case D:
                if (this.yPos + currImage.getHeight() / 2 > NavalFrame.FRAME_LOCATION_Y) this.yPos -= SPEED;
                break;
        }
    }


}
