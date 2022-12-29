package com.fourbit.pc_invader.utils;

import com.badlogic.gdx.math.Vector2;

import static com.fourbit.pc_invader.utils.Globals.GAME_WIDTH;
import static com.fourbit.pc_invader.utils.Globals.GAME_HEIGHT;


public class Anchor {
    private final float x;  // 0.0 <= x <= 1.0, where 0.0 is the left of the screen and 1.0 is the right of the screen
    private final float y;  // 0.0 <= y <= 1.0, where 0.0 is the bottom of the screen and 1.0 is the top of the screen
    private final float homingSpeed;


    public Anchor(float x, float y, float homingSpeed) {
        this.x = x;
        this.y = y;
        this.homingSpeed = homingSpeed;
    }


    public Vector2 getScreenPos() {
        return new Vector2(x * GAME_WIDTH, y * GAME_HEIGHT);
    }

    public Vector2 getPhysicsPos() {
        return Utils.toMeters(getScreenPos());
    }

    public float getHomingSpeed() {
        return homingSpeed;
    }
}
