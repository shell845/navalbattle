/**
 * @author YC 03/30/2020
 */

package com.shell.navalbattle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Weapon extends AbstractGameObject {
    private int xPos, yPos;
    private Groups group;
    private Directions dir;
    private boolean alive;
    private BufferedImage currImage;
    private int speed = Integer.parseInt(PropertyMgr.getConfig("defaultSpeed"));

    public Weapon (int x, int y, Groups g) {
        this.xPos = x;
        this.yPos = y;
        this.group = g;
        this.alive = true;
        if (g == Groups.Enemy) {
            this.currImage = ResourceMgr.bubbleBlue;
        } else this.currImage = ResourceMgr.bubbleRainbow;

        if (g == Groups.Enemy) {
            this.dir = Directions.L;
        } else this.dir = Directions.R;
    }

    public void paint(Graphics g) throws IOException {
        g.drawImage(currImage, xPos + currImage.getWidth() / 2, yPos, null);
        move();
    }

    public int getPosX() {
        return this.xPos;
    }

    public int getPosY() {
        return this.yPos;
    }

    public int getWidth() { return this.currImage.getWidth(); }

    public int getHeight() { return this.currImage.getHeight(); }

    public boolean isAlive() {
        return this.alive;
    }

    public void setAlive(Boolean status) {
        this.alive = status;
    }

    public void setSpeed(int i) {
        this.speed = i;
    }

    public void setImage(BufferedImage img) {
        this.currImage = img;
    }

    public Groups getGroup() {
        return this.group;
    }

    /*public void collideWithShip(SubmarineEnemy submarine) {
        if (!submarine.isAlive() || this.group == submarine.getGroup()) return;

        Rectangle rectWeapon = new Rectangle(xPos, yPos, currImage.getWidth(), currImage.getHeight());
        Rectangle rectShip = new Rectangle(submarine.getPosX() + submarine.getHeight() / 3,
                submarine.getPosY(), submarine.getWidth(), submarine.getHeight());
        if (rectShip.intersects(rectWeapon)) {
            NavalFrame.MAIN_FRAME.hit++;
            this.setAlive(false);
            submarine.setAlive(false);
            NavalFrame.MAIN_FRAME.addExplode(
                    new Explode(submarine.getPosX() - submarine.getWidth() / 2,
                            submarine.getPosY() + submarine.getHeight() / 2));
        }
    }*/

    private void move() {
        // check if out of boundary
        boundaryCheck();

        if (!this.alive) return;

        // move when alive
        switch (this.dir) {
            case L:
                this.xPos -= speed;
                break;
            case R:
                this.xPos += speed;
                break;
//            case U:
//                this.yPos += SPEED;
//                break;
//            case D:
//                this.yPos -= SPEED;
//                break;
        }
    }

    private void boundaryCheck() {
        if (xPos > NavalFrame.FRAME_WIDTH || xPos < NavalFrame.FRAME_LOCATION_X
                || yPos > NavalFrame.FRAME_HEIGHT || yPos < NavalFrame.FRAME_LOCATION_Y) {
            setAlive(false);
        }
    }
}
