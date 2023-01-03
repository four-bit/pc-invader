package com.fourbit.pc_invader.utils;

import com.badlogic.gdx.physics.box2d.*;
import com.fourbit.pc_invader.levels.Level;


public abstract class CollisionListener implements ContactListener {
    protected World world;

    public CollisionListener(World world) {
        this.world = world;
        this.world.setContactListener(this);
    }
}

