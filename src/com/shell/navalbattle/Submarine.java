/**
 * @author YC 04/01/2020
 */

package com.shell.navalbattle;

public interface Submarine {
     int getPosX();

     int getPosY();

     int getWidth();

     int getHeight();

     Groups getGroup();

     boolean isAlive();

     void setAlive(Boolean status);
}
