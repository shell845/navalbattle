package com.shell.navalbattle;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author YC 03/29/2020
 */

public class NavalFrame extends Frame {
    public static final NavalFrame MAIN_FRAME = new NavalFrame("Naval Battle");

    public static final int FRAME_LOCATION_X = 0, FRAME_LOCATION_Y = 0;
    public static final int FRAME_WIDTH = 800, FRAME_HEIGHT = 600;

    private Ship myShip;
    private Ship enemyShip;
    private ArrayList<Weapon> weapons;

    private NavalFrame(String title) {
        this.setTitle(title);
        this.setLocation(FRAME_LOCATION_X, FRAME_LOCATION_Y);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);

        this.addKeyListener(new NavalKeyListener());

        myShip = new Ship(50, 50, Directions.R, Groups.Friend);
        enemyShip = new Ship(500, 100, Directions.R, Groups.Enemy);
        weapons = new ArrayList<>();
    }

    @Override
    public void paint(Graphics g) {
        // paint
        try {
            myShip.paint(g);
            enemyShip.paint(g);
            if (!weapons.isEmpty()) {
                for (Weapon w:weapons) {
                    w.paint(g);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // remove out of frame weapons
        if (!weapons.isEmpty()) {
            weapons.removeIf(w -> w.getPosX() > FRAME_WIDTH || w.getPosX() < FRAME_LOCATION_X
                    || w.getPosY() > FRAME_HEIGHT || w.getPosY() < FRAME_LOCATION_Y);
        }
    }

    public void addWeapon(Weapon w) {
        this.weapons.add(w);
    }

    // cache buffer to solve flashing display problem
    Image offScreenImage = null;

    @Override
    public void update(Graphics g) { // update is called by repaint
        if (offScreenImage == null) {
            offScreenImage = this.createImage(FRAME_WIDTH, FRAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.cyan);
        gOffScreen.fillRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    // key listener to control ship
    private class NavalKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            myShip.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            myShip.keyReleased(e);
        }
    }
}
