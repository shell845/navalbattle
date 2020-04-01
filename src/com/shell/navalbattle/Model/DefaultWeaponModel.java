/**
 * @author YC 03/30/2020
 */

package com.shell.navalbattle.Model;

import com.shell.navalbattle.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class DefaultWeaponModel implements WeaponModel {

    @Override
    public void shoot(Submarine s) {
        Weapon weapon = new Weapon(s.getPosX() + s.getWidth() / 2, s.getPosY(), s.getGroup());
        NavalFrame.MAIN_FRAME.addWeapon(weapon);
    }
}
