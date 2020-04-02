/**
 * @author YC 03/30/2020
 */

package com.shell.navalbattle.model;

import com.shell.navalbattle.*;

public class DoubleWeaponModel implements WeaponModel {

    @Override
    public void shoot(Submarine s) {
        Weapon weapon1 = new Weapon(s.getPosX() + s.getWidth() / 2, s.getPosY() - s.getHeight() / 10, s.getGroup());
        weapon1.setSpeed(Integer.parseInt(PropertyMgr.getConfig("doubleSpeed")));
        weapon1.setImage(ResourceMgr.bubbleRainbowBig);
        NavalFrame.MAIN_FRAME.addGameObject(weapon1);
    }
}
