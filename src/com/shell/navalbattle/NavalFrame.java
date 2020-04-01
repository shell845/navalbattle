package com.shell.navalbattle;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author YC 03/29/2020
 */

public class NavalFrame extends Frame {
    public static final NavalFrame MAIN_FRAME = new NavalFrame(PropertyMgr.getConfig("Title"));

    public static final int FRAME_LOCATION_X = 0;//Integer.parseInt(PropertyMgr.getConfig("FrameLocationX"));
    public static final int FRAME_LOCATION_Y = 0; //Integer.parseInt(PropertyMgr.getConfig("FrameLocationY"));
    public static final int FRAME_WIDTH = 800; // Integer.parseInt(PropertyMgr.getConfig("FrameWidth"));
    public static final int FRAME_HEIGHT = 600; // Integer.parseInt(PropertyMgr.getConfig("FrameHeight"));
    public static final int MARGIN_X = Integer.parseInt(PropertyMgr.getConfig("MarginX"));
    public static final int MARGIN_Y = Integer.parseInt(PropertyMgr.getConfig("MarginY"));

    private SubmarinePlayer mySubmarine;
    private ArrayList<SubmarineEnemy> enemies;
    private ArrayList<Weapon> weapons;
    private ArrayList<Explode> explodes;
    private int enemyNums = Integer.parseInt(PropertyMgr.getConfig("initEnemyNum"));
    public int hit;

    private static Random random = new Random();

    private NavalFrame(String title) {
        this.setTitle(title);
        this.setLocation(FRAME_LOCATION_X, FRAME_LOCATION_Y);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.hit = 0;

        this.addKeyListener(new NavalKeyListener());

        initiateFrame();
    }

    private void initiateFrame() {
        int initPosX = Integer.parseInt(PropertyMgr.getConfig("initPosX"));
        int initPosY = Integer.parseInt(PropertyMgr.getConfig("initPosY"));

        mySubmarine = new SubmarinePlayer(initPosX, initPosY, Directions.R);

        weapons = new ArrayList<>();
        enemies = new ArrayList<>();
        explodes = new ArrayList<>();

        for (int i = 0; i < enemyNums; i++) {
            addEnemy(new SubmarineEnemy(FRAME_WIDTH - MARGIN_X, FRAME_HEIGHT * i / 4 + MARGIN_Y, Directions.L));
        }

    }

    @Override
    public void paint(Graphics g) {
        // paint
        try {
            g.setColor(Color.magenta);
            g.drawString(hit + " hits", FRAME_LOCATION_X + MARGIN_X, FRAME_LOCATION_Y + MARGIN_Y);

            mySubmarine.paint(g);

            for (SubmarineEnemy enemy : enemies) {
                enemy.paint(g);
            }

            for (Weapon weapon : weapons) {
                // check collision and paint
                for (SubmarineEnemy enemy : enemies) {
                    weapon.collideWithShip(enemy);
                }
                weapon.paint(g);
            }

            for (Explode explode : explodes) {
                explode.paint(g);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // remove death
        enemies.removeIf(s -> !s.isAlive());
        weapons.removeIf(w -> !w.isAlive());
        explodes.removeIf(e -> !e.isAlive());
        // randomly add enemy
        if (random.nextInt(100) > 95) {
            int n = random.nextInt(enemyNums);
            for (int i = 0; i < n; i++) {
                addEnemy(new SubmarineEnemy(FRAME_WIDTH - MARGIN_X * 3, FRAME_HEIGHT * i / 4 + MARGIN_Y, Directions.L));
            }
        }
    }

    public void addWeapon(Weapon w) {
        this.weapons.add(w);
    }

    public void addEnemy(SubmarineEnemy s) {
        this.enemies.add(s);
    }

    public void addExplode(Explode e) {
        this.explodes.add(e);
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
        gOffScreen.fillRect(FRAME_LOCATION_X, FRAME_LOCATION_Y, FRAME_WIDTH, FRAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, FRAME_LOCATION_X, FRAME_LOCATION_Y, null);
    }

    // key listener to control ship
    private class NavalKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            mySubmarine.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            mySubmarine.keyReleased(e);
        }
    }
}
