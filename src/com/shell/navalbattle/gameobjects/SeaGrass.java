/**
 * @author YC 04/02/2020
 */

package com.shell.navalbattle.gameobjects;

public abstract class SeaGrass extends AbstractGameObject {
    public abstract int getPosX();

    public abstract int getPosY();

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract Groups getGroup();

    public abstract boolean isAlive();

    public abstract void setAlive(Boolean status);
}
