package com.shell.navalbattle;

/**
 * @author YC 04/03/2020
 */

import com.shell.navalbattle.collision.CollisionChain;
import com.shell.navalbattle.gameobjects.*;
import com.shell.navalbattle.utils.PropertyMgr;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

/** MVC - model, view, controller */
public class GameModel {
    private static GameModel gameModelInstance = new GameModel();

    public static final int FRAME_LOCATION_X = 0; //Integer.parseInt(PropertyMgr.getConfig("FrameLocationX"));
    public static final int FRAME_LOCATION_Y = 0; //Integer.parseInt(PropertyMgr.getConfig("FrameLocationY"));
    public static final int FRAME_WIDTH = 800; // Integer.parseInt(PropertyMgr.getConfig("FrameWidth"));
    public static final int FRAME_HEIGHT = 600; // Integer.parseInt(PropertyMgr.getConfig("FrameHeight"));

    public static final int MARGIN_X = Integer.parseInt(PropertyMgr.getConfig("MarginX"));
    public static final int MARGIN_Y = Integer.parseInt(PropertyMgr.getConfig("MarginY"));

    public int defaultWeaponNum = Integer.parseInt(PropertyMgr.getConfig("defaultWeaponNum"));
    public int hit;
    public int hitTOWin = Integer.parseInt(PropertyMgr.getConfig("hitTOWin"));
    private int enemyNums = Integer.parseInt(PropertyMgr.getConfig("initEnemyNum"));

    public SubmarinePlayer mySubmarine;
    private ArrayList<AbstractGameObject> gameObjects;
    public boolean gameOver;

    private CollisionChain collisionChain;
    private static Random random = new Random();

    private GameModel() {
        this.hit = 0;
        this.collisionChain = new CollisionChain();
        this.gameOver = false;

        initiateFrame();
    }

    public static GameModel getNewInstance() {
        gameModelInstance = new GameModel();
        return gameModelInstance;
    }

    public static GameModel getInstance() {
        return gameModelInstance;
    }

    public void initiateFrame() {
        int initPosX = Integer.parseInt(PropertyMgr.getConfig("initPosX"));
        int initPosY = Integer.parseInt(PropertyMgr.getConfig("initPosY"));

        gameObjects = new ArrayList<>();

        SeaGrassFix grassfix = new SeaGrassFix();
        SeaGrassFloating grassFloating = new SeaGrassFloating();
        mySubmarine = new SubmarinePlayer(initPosX, initPosY, Directions.R);
        gameObjects.add(grassfix);
        gameObjects.add(mySubmarine);
        gameObjects.add(grassFloating);

        for (int i = 0; i < enemyNums; i++) {
            gameObjects.add(new SubmarineEnemy(FRAME_WIDTH - MARGIN_X * 2, FRAME_HEIGHT * i / 4 + MARGIN_Y, Directions.L));
        }

    }

    public void paint(Graphics g) {
        try {
            if (!checkStatus(g)) {
                this.gameOver = true;
                return;
            }

            for (int i = 0; i < gameObjects.size(); i++) {
                // remove death
                if (!gameObjects.get(i).isAlive()) {
                    gameObjects.remove(i);
                    break;
                }

                // collision check
                for (int j = 0; j < gameObjects.size(); j++) {
                    collisionChain.Collision(gameObjects.get(j), gameObjects.get(i));
                }

                // paint
                if (gameObjects.get(i).isAlive()) {
                    gameObjects.get(i).paint(g);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        // remove death
        // gameObjects.removeIf(s -> !s.isAlive());

        // randomly add enemy
        if (random.nextInt(100) > 94) {
            int n = random.nextInt(enemyNums);
            for (int i = 0; i < n; i++) {
                gameObjects.add(new SubmarineEnemy(FRAME_WIDTH - MARGIN_X * 2, FRAME_HEIGHT * i / 4 + MARGIN_Y, Directions.L));
            }
        }
    }

    // check status (win, lose, continue) and write text
    private boolean checkStatus(Graphics g) {
        if (!mySubmarine.isAlive() || (defaultWeaponNum <= 0 && hit < hitTOWin) ) {
            g.setColor(Color.red);
            g.setFont(new Font(PropertyMgr.getConfig("Font"), Font.PLAIN, 60));
            if (!mySubmarine.isAlive()) {
                g.drawString(PropertyMgr.getConfig("die"), FRAME_LOCATION_X + FRAME_WIDTH / 4, FRAME_LOCATION_Y + FRAME_HEIGHT / 2);
            } else {
                g.drawString(PropertyMgr.getConfig("lose"), FRAME_LOCATION_X + FRAME_WIDTH / 8, FRAME_LOCATION_Y + FRAME_HEIGHT / 2);
            }
            g.setFont(new Font(PropertyMgr.getConfig("Font"), Font.PLAIN, 30));
            g.drawString(PropertyMgr.getConfig("continue"), FRAME_LOCATION_X + FRAME_WIDTH / 5, FRAME_LOCATION_Y + FRAME_HEIGHT * 2 / 3);
            return false;
        }

        if (hit >= hitTOWin && defaultWeaponNum > 0) {
            g.setColor(Color.magenta);
            g.setFont(new Font(PropertyMgr.getConfig("Font"), Font.PLAIN, 80));
            g.drawString(PropertyMgr.getConfig("win"), FRAME_LOCATION_X + FRAME_WIDTH / 8, FRAME_LOCATION_Y + FRAME_HEIGHT / 2);
            g.setFont(new Font(PropertyMgr.getConfig("Font"), Font.PLAIN, 30));
            g.drawString(PropertyMgr.getConfig("continue"), FRAME_LOCATION_X + FRAME_WIDTH / 5, FRAME_LOCATION_Y + FRAME_HEIGHT * 2 / 3);
            return false;
        }

        g.setColor(Color.magenta);
        g.drawString(hit + " hits", FRAME_LOCATION_X + MARGIN_X, FRAME_LOCATION_Y + MARGIN_Y);
        g.drawString(defaultWeaponNum + " bubbles left", FRAME_LOCATION_X + MARGIN_X, (int) (FRAME_LOCATION_Y + MARGIN_Y * 1.3));
        g.drawString(mySubmarine.getLives() + " lives left", FRAME_LOCATION_X + MARGIN_X, (int) (FRAME_LOCATION_Y + MARGIN_Y * 1.7));

        return true;
    }

    public void addGameObject(AbstractGameObject item) {
        gameObjects.add(item);
    }

    public SubmarinePlayer getMySubmarine() {
        return this.mySubmarine;
    }

    public SubmarinePlayer findSubmarineByUUID(UUID id) {
        for(AbstractGameObject o : gameObjects) {
            if(o instanceof SubmarinePlayer) {
                SubmarinePlayer s = (SubmarinePlayer)o;
                if(id.equals(s.getUUID())) return s;
            }
        }

        return null;
    }
}
