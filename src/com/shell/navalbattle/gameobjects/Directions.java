package com.shell.navalbattle.gameobjects;

import java.util.Random;

/**
 * @author YC 03/29/2020
 */

public enum Directions {
    L, R, U, D;

    private static Random random = new Random();
    public static Directions randomDirection() {
        Directions[] dirSet = Directions.values();
        int dir = random.nextInt(dirSet.length);
        return dirSet[dir];
    }
}
