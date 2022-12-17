package com.fourbit.pc_invader.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import com.fourbit.pc_invader.utils.GameComponent;


public class Entity implements GameComponent {
    protected Vector2 position;
    protected float angle;
    protected Texture texture;

    public Entity(String texturePath) {
        texture = new Texture(texturePath);
        position = new Vector2();
    }


    public Vector2 getPosition() {
        return position;
    }

    public float getAngle() {
        return angle;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }


    @Override
    public void update() {}

    @Override
    public void draw(Batch batch) {
        batch.draw(
                texture,
                position.x - (float) texture.getWidth() / 2,
                position.y - (float) texture.getHeight() / 2,
                (float) texture.getWidth() / 2,
                (float) texture.getHeight() / 2,
                texture.getWidth(),
                texture.getHeight(),
                1.0f,
                1.0f,
                angle,
                0,
                0,
                texture.getWidth(),
                texture.getHeight(),
                false,
                false
        );
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
