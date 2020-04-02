/**
 * @author YC 04/02/2020
 */

package com.shell.navalbattle;

import com.shell.navalbattle.AbstractGameObject;
import com.shell.navalbattle.PropertyMgr;
import com.shell.navalbattle.ResourceMgr;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SeaGrassFloating extends AbstractGameObject {
    private BufferedImage currImage;
    private static final int SPEED = 3;
    private static int START;
    private int xPos, yPos;

    public SeaGrassFloating() {
        currImage = ResourceMgr.seagrassFloat;
        xPos = Integer.parseInt(PropertyMgr.getConfig("FrameWidth")) / 2;
        yPos = Integer.parseInt(PropertyMgr.getConfig("FrameHeight")) / 2;
        START = xPos;
    }

    @Override
    public void paint(Graphics g) throws IOException {
        g.drawImage(currImage, xPos,
                yPos, null);
        move();
    }

    private void move() {
        if (xPos <= 0) xPos = START;
        xPos -= SPEED;
    }
}
