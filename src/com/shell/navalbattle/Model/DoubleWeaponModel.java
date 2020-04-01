/**
 * @author YC 03/30/2020
 */

package com.shell.navalbattle.Model;

import com.shell.navalbattle.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class DoubleWeaponModel implements WeaponModel {

    @Override
    public void shoot(Submarine s) {
        Weapon weapon1 = new Weapon(s.getPosX() + s.getWidth() / 2, s.getPosY() - s.getHeight() / 10, s.getGroup());
        weapon1.setSpeed(Integer.parseInt(PropertyMgr.getConfig("doubleSpeed")));
        weapon1.setImage(ResourceMgr.bubbleRainbowBig);
        NavalFrame.MAIN_FRAME.addWeapon(weapon1);

        Weapon weapon2 = new Weapon(s.getPosX() + s.getWidth() / 2, s.getPosY(), s.getGroup());
        NavalFrame.MAIN_FRAME.addWeapon(weapon2);
    }
}
