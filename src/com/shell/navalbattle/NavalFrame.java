package com.shell.navalbattle;

import com.shell.navalbattle.utils.PropertyMgr;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author YC 03/29/2020
 */

public class NavalFrame extends Frame {
    public static final NavalFrame MAIN_FRAME = new NavalFrame(PropertyMgr.getConfig("Title"));

    public static final int FRAME_LOCATION_X = 0; //Integer.parseInt(PropertyMgr.getConfig("FrameLocationX"));
    public static final int FRAME_LOCATION_Y = 0; //Integer.parseInt(PropertyMgr.getConfig("FrameLocationY"));
    public static final int FRAME_WIDTH = 800; // Integer.parseInt(PropertyMgr.getConfig("FrameWidth"));
    public static final int FRAME_HEIGHT = 600; // Integer.parseInt(PropertyMgr.getConfig("FrameHeight"));


    private GameModel gameModel;

    private NavalFrame(String title) {
        this.setTitle(title);
        this.setLocation(FRAME_LOCATION_X, FRAME_LOCATION_Y);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);

        this.addKeyListener(new NavalKeyListener());

        gameModel = GameModel.getInstance();
        gameModel.initiateFrame();
    }


    @Override
    public void paint(Graphics g) {
        // paint
        gameModel.paint(g);

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
            if (gameModel.gameOver && e.getKeyCode() == KeyEvent.VK_SPACE) {
                gameModel = GameModel.getNewInstance();
            }
            gameModel.mySubmarine.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            gameModel.mySubmarine.keyReleased(e);
        }
    }
}
