package com.shell.navalbattle;

import com.shell.navalbattle.collision.CollisionChain;

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

    public static final int FRAME_LOCATION_X = 0; //Integer.parseInt(PropertyMgr.getConfig("FrameLocationX"));
    public static final int FRAME_LOCATION_Y = 0; //Integer.parseInt(PropertyMgr.getConfig("FrameLocationY"));
    public static final int FRAME_WIDTH = 800; // Integer.parseInt(PropertyMgr.getConfig("FrameWidth"));
    public static final int FRAME_HEIGHT = 600; // Integer.parseInt(PropertyMgr.getConfig("FrameHeight"));
    public static final int MARGIN_X = Integer.parseInt(PropertyMgr.getConfig("MarginX"));
    public static final int MARGIN_Y = Integer.parseInt(PropertyMgr.getConfig("MarginY"));

    private SubmarinePlayer mySubmarine;
    private ArrayList<AbstractGameObject> gameObjects;
    private int enemyNums = Integer.parseInt(PropertyMgr.getConfig("initEnemyNum"));
    private int hitTOWin = Integer.parseInt(PropertyMgr.getConfig("HitTOWin"));
    public int defaultWeaponNum = Integer.parseInt(PropertyMgr.getConfig("defaultWeaponNum"));
    private SeaGrassFix grassfix;
    public int hit;
    private CollisionChain collisionChain;

    private static Random random = new Random();

    private NavalFrame(String title) {
        this.setTitle(title);
        this.setLocation(FRAME_LOCATION_X, FRAME_LOCATION_Y);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.hit = 0;
        this.collisionChain = new CollisionChain();

        this.addKeyListener(new NavalKeyListener());

        initiateFrame();
    }

    private void initiateFrame() {
        int initPosX = Integer.parseInt(PropertyMgr.getConfig("initPosX"));
        int initPosY = Integer.parseInt(PropertyMgr.getConfig("initPosY"));

        gameObjects = new ArrayList<>();

        grassfix = new SeaGrassFix();
        mySubmarine = new SubmarinePlayer(initPosX, initPosY, Directions.R);
        gameObjects.add(grassfix);
        gameObjects.add(mySubmarine);

        for (int i = 0; i < enemyNums; i++) {
            gameObjects.add(new SubmarineEnemy(FRAME_WIDTH - MARGIN_X * 2, FRAME_HEIGHT * i / 4 + MARGIN_Y, Directions.L));
        }

    }

    @Override
    public void paint(Graphics g) {
        // paint
        try {
            if (!mySubmarine.isAlive() || (defaultWeaponNum <= 0 && hit < hitTOWin) ) {
                g.setColor(Color.red);
                g.setFont(new Font(PropertyMgr.getConfig("Font"), Font.PLAIN, 80));
                g.drawString(PropertyMgr.getConfig("lose"), FRAME_LOCATION_X + FRAME_WIDTH / 8, FRAME_LOCATION_Y + FRAME_HEIGHT / 2);
                return;
            }

            if (hit >= hitTOWin && defaultWeaponNum > 0) {
                g.setColor(Color.magenta);
                g.setFont(new Font(PropertyMgr.getConfig("Font"), Font.PLAIN, 80));
                g.drawString(PropertyMgr.getConfig("win"), FRAME_LOCATION_X + FRAME_WIDTH / 8, FRAME_LOCATION_Y + FRAME_HEIGHT / 2);
                return;
            }

            g.setColor(Color.magenta);
            g.drawString(hit + " hits", FRAME_LOCATION_X + MARGIN_X, FRAME_LOCATION_Y + MARGIN_Y);
            g.drawString(defaultWeaponNum + " bubbles left", FRAME_LOCATION_X + MARGIN_X, (int) (FRAME_LOCATION_Y + MARGIN_Y * 1.3));
            g.drawString(mySubmarine.getLives() + " lives left", FRAME_LOCATION_X + MARGIN_X, (int) (FRAME_LOCATION_Y + MARGIN_Y * 1.6));

            for (int i = 0; i < gameObjects.size(); i++) {
                if (!gameObjects.get(i).isAlive()) {
                    gameObjects.remove(i);
                    break;
                }

                for (int j = 0; j < gameObjects.size(); j++) {
                    collisionChain.Collision(gameObjects.get(j), gameObjects.get(i));
                }

                if (gameObjects.get(i).isAlive()) {
                    gameObjects.get(i).paint(g);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        // remove death
        gameObjects.removeIf(s -> !s.isAlive());

        // randomly add enemy
        if (random.nextInt(100) > 94) {
            int n = random.nextInt(enemyNums);
            for (int i = 0; i < n; i++) {
                gameObjects.add(new SubmarineEnemy(FRAME_WIDTH - MARGIN_X * 2, FRAME_HEIGHT * i / 4 + MARGIN_Y, Directions.L));
            }
        }
    }

    public void addGameObject(AbstractGameObject item) {
        gameObjects.add(item);
    }

//    public void addWeapon(Weapon w) {
//        this.weapons.add(w);
//    }
//
//    public void addEnemy(SubmarineEnemy s) {
//        this.enemies.add(s);
//    }
//
//    public void addExplode(Explode e) {
//        this.explodes.add(e);
//    }

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
