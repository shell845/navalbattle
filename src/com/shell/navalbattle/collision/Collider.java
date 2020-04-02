/**
 * @author YC 04/02/2020
 */

package com.shell.navalbattle.collision;

import com.shell.navalbattle.gameobjects.AbstractGameObject;

public interface Collider {
    boolean Collision(AbstractGameObject item1, AbstractGameObject item2);
}
