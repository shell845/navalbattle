/**
 * @author YC 04/01/2020
 */

package com.shell.navalbattle;

public abstract class Submarine extends AbstractGameObject{
     public abstract int getPosX();

     public abstract int getPosY();

     public abstract int getWidth();

     public abstract int getHeight();

     public abstract Groups getGroup();

     public abstract boolean isAlive();

     public abstract void setAlive(Boolean status);

     public abstract void reduceLives();
}
