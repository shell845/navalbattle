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
    public static final NavalFrame MAIN_FRAME = new NavalFrame("Mini Submarine");

    public static final int FRAME_LOCATION_X = 0, FRAME_LOCATION_Y = 0;
    public static final int FRAME_WIDTH = 800, FRAME_HEIGHT = 600;

    private SubmarinePlayer mySubmarine;
    private ArrayList<SubmarineEnemy> enemies;
    private ArrayList<Weapon> weapons;
    private ArrayList<Explode> explodes;
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
        mySubmarine = new SubmarinePlayer(50, 50, Directions.R);

        weapons = new ArrayList<>();
        enemies = new ArrayList<>();
        explodes = new ArrayList<>();


        for (int i = 0; i < 3; i++) {
            addEnemy(new SubmarineEnemy(FRAME_WIDTH, FRAME_HEIGHT * i / 4, Directions.L));
        }

    }

    @Override
    public void paint(Graphics g) {
        // paint
        try {
            g.setColor(Color.magenta);
            g.drawString(hit + " hits", FRAME_LOCATION_X + 10, FRAME_LOCATION_Y + 40);

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

        if (random.nextInt(100) > 90) {
            int n = random.nextInt(2);
            for (int i = 0; i < n; i++) {
                addEnemy(new SubmarineEnemy(FRAME_WIDTH, FRAME_HEIGHT / (random.nextInt(5) + 1), Directions.L));
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
        gOffScreen.fillRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
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
