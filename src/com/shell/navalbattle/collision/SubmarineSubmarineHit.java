/**
 * @author YC 04/02/2020
 */

package com.shell.navalbattle.collision;

import com.shell.navalbattle.*;

import java.awt.*;

public class SubmarineSubmarineHit implements Collider {

    @Override
    public boolean Collision(AbstractGameObject item1, AbstractGameObject item2) {
        if (item1 instanceof Submarine && item2 instanceof Submarine) {
            return collideWithSubmarine((Submarine) item1, (Submarine) item2);
        }
        return true;
    }

    public boolean collideWithSubmarine(Submarine s1, Submarine s2) {
        if (!s1.isAlive() && !s2.isAlive()) return false;
        if (!s1.isAlive() || !s2.isAlive() || s1.getGroup() == s2.getGroup()) return true;

        Rectangle rectS1 = new Rectangle(s1.getPosX(),
                s1.getPosY() + s1.getHeight() / 3, s1.getWidth(), s1.getHeight() * 2 / 3);
        Rectangle rectS2 = new Rectangle(s2.getPosX(),
                s2.getPosY() + s2.getHeight() / 3, s2.getWidth(), s2.getHeight() * 2 / 3);
        if (rectS1.intersects(rectS2)) {
            if (s1.getGroup() == Groups.Friend) {
                s1.reduceLives();
                s2.setAlive(false);
                NavalFrame.MAIN_FRAME.addGameObject(
                        new Explode(s2.getPosX() + s2.getWidth() / 2,
                                s2.getPosY() + s2.getHeight() / 2));
            } else {
                s2.reduceLives();
                s1.setAlive(false);
                NavalFrame.MAIN_FRAME.addGameObject(
                        new Explode(s1.getPosX() + s1.getWidth() / 2,
                                s1.getPosY() + s1.getHeight() / 2));
            }
            return s1.isAlive();
        } else return true;
    }
}
