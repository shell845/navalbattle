/**
 * @author YC 03/30/2020
 */

package com.shell.navalbattle.model;

import com.shell.navalbattle.*;

public class DefaultWeaponModel implements WeaponModel {

    @Override
    public void shoot(Submarine s) {
        Weapon weapon = new Weapon(s.getPosX() + s.getWidth() / 2, s.getPosY(), s.getGroup());
        NavalFrame.MAIN_FRAME.addGameObject(weapon);
    }
}
