/**
 * @author YC 04/02/2020
 */

package com.shell.navalbattle.collision;

import com.shell.navalbattle.*;

import java.awt.*;

public class WeaponSubmarineHit implements Collider {

    @Override
    public boolean Collision(AbstractGameObject item1, AbstractGameObject item2) {
        if (item1 instanceof Weapon && item2 instanceof Submarine) {
            return collideWithShip((Weapon) item1, (Submarine) item2);
        } else if (item1 instanceof Submarine && item2 instanceof Weapon) {
            return Collision(item2, item1);
        }
        return true;
    }

    public boolean collideWithShip(Weapon w, Submarine s) {
        if (!s.isAlive() && !w.isAlive()) return false;
        if (!s.isAlive() || !w.isAlive() || w.getGroup() == s.getGroup()) return true;

        Rectangle rectWeapon = new Rectangle(w.getPosX(), w.getPosY(), w.getWidth(), w.getHeight());
        Rectangle rectShip = new Rectangle(s.getPosX(),
                s.getPosY() + s.getHeight() / 3, s.getWidth(), s.getHeight() * 2 / 3);
        if (rectShip.intersects(rectWeapon)) {
            w.setAlive(false);
            if (s.getGroup() == Groups.Enemy) {
                s.setAlive(false);
                NavalFrame.MAIN_FRAME.hit++;
                NavalFrame.MAIN_FRAME.addGameObject(
                        new Explode(s.getPosX() + s.getWidth() / 2,
                                s.getPosY() + s.getHeight() / 2));
            } else if (s.getGroup() == Groups.Friend) {
                s.reduceLives();
            }
            return false;
        } else return true;
    }
}
