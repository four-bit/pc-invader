package com.fourbit.pc_invader;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public class Entity {
    private Vector2 position;
    private float angle;
    private final Texture texture;

    public Entity(String texturePath) {
        texture = new Texture(texturePath);
        position = new Vector2();
    }

    public Vector2 getPosition() { return position; }
    public float getX() { return position.x; }
    public float getY() { return position.y; }
    public int getWidth() { return texture.getWidth(); }
    public int getHeight() { return texture.getHeight(); }
    public float getAngle() { return angle; }

    public void setPosition(float x, float y) {
        this.position.x = x;
        this.position.y = y;
    }
    public void setAngle(float angle) { this.angle = angle; }

    public void draw(Batch batch) {
        batch.draw(
                texture,
                getX() - (float) getWidth() / 2,
                getY() - (float) getHeight() / 2,
                (float) getWidth() / 2,
                (float) getHeight() / 2,
                getWidth(),
                getHeight(),
                1.0f,
                1.0f,
                getAngle(),
                0,
                0,
                getWidth(),
                getHeight(),
                false,
                false
        );
    }

    public void dispose() {
        texture.dispose();
    }
}
