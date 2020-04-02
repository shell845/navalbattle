/**
 * @author YC 04/02/2020
 */

package com.shell.navalbattle.collision;

import com.shell.navalbattle.gameobjects.AbstractGameObject;
import com.shell.navalbattle.utils.PropertyMgr;

import java.util.ArrayList;

public class CollisionChain implements Collider {
    private ArrayList<Collider> collides;

    public CollisionChain() {
        LoadeCollider();
    }

    public boolean Collision(AbstractGameObject item1, AbstractGameObject item2) {
        if (item1 == item2) return false;
        for (Collider c:collides) {
            if(!c.Collision(item1, item2)) break;
        }
        return true;
    }

    private void LoadeCollider() {
        collides = new ArrayList<>();
        String[] className = PropertyMgr.getConfig("colliders").split(",");
        try {
            for (String name : className) {
                Class klass = Class.forName("com.shell.navalbattle.collision." + name);
                collides.add((Collider) (klass.getConstructor().newInstance()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
