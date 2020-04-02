/**
 * @author YC 04/02/2020
 */

package com.shell.navalbattle.collision;

import com.shell.navalbattle.*;

import java.awt.*;

public class WeaponSeagrassHit implements Collider {

    @Override
    public boolean Collision(AbstractGameObject item1, AbstractGameObject item2) {
        if (item1 instanceof Weapon && item2 instanceof SeaGrass) {
            return collideWithSeagrass((Weapon) item1, (SeaGrass) item2);
        } else if (item1 instanceof SeaGrass && item2 instanceof Weapon) {
            return Collision(item2, item1);
        }
        return true;
    }

    public boolean collideWithSeagrass(Weapon w, SeaGrass s) {
        if (!s.isAlive() && !w.isAlive()) return false;
        if (!s.isAlive() || !w.isAlive()) return true;

        Rectangle rectWeapon = new Rectangle(w.getPosX(), w.getPosY(), w.getWidth(), w.getHeight());
        Rectangle rectGrass = new Rectangle(s.getPosX(), s.getPosY(), s.getWidth(), s.getHeight());
        if (rectGrass.intersects(rectWeapon)) {
            w.setAlive(false);
        }
        return true;
    }
}
