/**
 * @author YC 04/01/2020
 */

package com.shell.navalbattle.gameobjects;

import java.awt.*;
import java.io.IOException;

public abstract class AbstractGameObject {
    public abstract void paint(Graphics g) throws IOException;

    public abstract int getPosX();

    public abstract int getPosY();

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract Groups getGroup();

    public abstract boolean isAlive();

    public abstract void setAlive(Boolean status);

    // public abstract void com.shell.navalbattle.collision(AbstractGameObject item);
}
