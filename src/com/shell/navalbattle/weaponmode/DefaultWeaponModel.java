/**
 * @author YC 03/30/2020
 */

package com.shell.navalbattle.weaponmode;

import com.shell.navalbattle.*;
import com.shell.navalbattle.gameobjects.Submarine;
import com.shell.navalbattle.gameobjects.Weapon;

public class DefaultWeaponModel implements WeaponModel {

    @Override
    public void shoot(Submarine s) {
        Weapon weapon = new Weapon(s.getPosX() + s.getWidth() / 2, s.getPosY(), s.getGroup(), s.getDirection());
        GameModel.getInstance().addGameObject(weapon);
    }
}
