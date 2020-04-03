/**
 * @author YC 04/02/2020
 */

package com.shell.navalbattle.collision;

import com.shell.navalbattle.gameobjects.AbstractGameObject;
import com.shell.navalbattle.gameobjects.SeaGrass;
import com.shell.navalbattle.gameobjects.Submarine;
import com.shell.navalbattle.gameobjects.Weapon;

import java.awt.*;

public class SubmarineSeagrassHit implements Collider {

    @Override
    public boolean Collision(AbstractGameObject item1, AbstractGameObject item2) {
        if (item1 instanceof Submarine && item2 instanceof SeaGrass) {
            return collideWithSeagrass((Submarine) item1, (SeaGrass) item2);
        } else if (item1 instanceof SeaGrass && item2 instanceof Submarine) {
            return Collision(item2, item1);
        }
        return true;
    }

    public boolean collideWithSeagrass(Submarine s, SeaGrass g) {
        if (!s.isAlive() && !g.isAlive()) return false;
        if (!s.isAlive() || !g.isAlive()) return true;

        Rectangle rectSub = new Rectangle(s.getPosX(),
                s.getPosY() + s.getHeight() / 3, s.getWidth(), s.getHeight() * 2 / 3);
        Rectangle rectGrass = new Rectangle(s.getPosX(), s.getPosY(), s.getWidth(), s.getHeight());
        if (rectGrass.intersects(rectSub)) {
            s.moveBack();
        }
        return true;
    }
}
