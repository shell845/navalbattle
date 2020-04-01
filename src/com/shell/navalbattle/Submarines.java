package com.shell.navalbattle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public enum Submarines {
    Green, DarkBlue, Pink;

    private static Random random = new Random();
    public static BufferedImage getRandomSubmarine() {
        BufferedImage image;
        Submarines[] sub = Submarines.values();
        Submarines s = sub[random.nextInt(sub.length)];
        switch (s) {
            case Green:
                image = ResourceMgr.submarineGreenL;
                break;
            case DarkBlue:
                image = ResourceMgr.submarineDarkBlueL;
                break;
            case Pink:
                image = ResourceMgr.submarinePinkL;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + s);
        }
        return image;
    }
}
