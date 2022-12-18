package com.fourbit.pc_invader.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class Bullet extends Sprite implements Pool.Poolable {
    private boolean alive;
    private final float speed;


    public Bullet(String texturePath, float speed) {
        super(texturePath);
        this.alive = false;
        this.speed = speed;
    }

    public void init(Vector2 position) {
        super.position = position;
        alive = true;
    }

    public void init(float x, float y) {
        super.position.x = x;
        super.position.y = y;
        alive = true;
    }

    public void update() {

    }

    @Override
    public void reset() {
        this.alive = false;
    }
}
