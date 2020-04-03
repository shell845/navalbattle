/**
 * @author YC 03/30/2020
 */

package com.shell.navalbattle.weaponmode;

import com.shell.navalbattle.*;
import com.shell.navalbattle.gameobjects.Submarine;
import com.shell.navalbattle.gameobjects.Weapon;
import com.shell.navalbattle.utils.PropertyMgr;
import com.shell.navalbattle.utils.ResourceMgr;

public class DoubleWeaponModel implements WeaponModel {

    @Override
    public void shoot(Submarine s) {
        Weapon weapon1 = new Weapon(s.getPosX() + s.getWidth() / 2, s.getPosY() - s.getHeight() / 10, s.getGroup(), s.getDirection());
        weapon1.setSpeed(Integer.parseInt(PropertyMgr.getConfig("doubleSpeed")));
        weapon1.setImage(ResourceMgr.bubbleRainbowBig);
        GameModel.getInstance().addGameObject(weapon1);
    }
}
