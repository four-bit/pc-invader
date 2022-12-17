package com.fourbit.pc_invader.entities;

import com.badlogic.gdx.utils.Pool;

public class Bullet extends Entity implements Pool.Poolable {
    private boolean alive;


    public Bullet(String texturePath) {
        super(texturePath);
    }

    @Override
    public void reset() {
        this.alive = false;
    }
}
