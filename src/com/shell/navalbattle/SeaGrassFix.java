/**
 * @author YC 04/02/2020
 */

package com.shell.navalbattle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SeaGrassFix extends AbstractGameObject {
    private BufferedImage currImage;
    private static final int SPEED = 3;
    private static int START;
    private int xPos, yPos;

    public SeaGrassFix() {
        currImage = ResourceMgr.seagrassFix;
        xPos = Integer.parseInt(PropertyMgr.getConfig("FrameWidth"));
        yPos = Integer.parseInt(PropertyMgr.getConfig("FrameHeight")) - currImage.getHeight();
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
