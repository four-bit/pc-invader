package com.fourbit.pc_invader.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import com.fourbit.pc_invader.utils.GameComponent;
import com.fourbit.pc_invader.utils.Utils;


public class Entity implements GameComponent {
    protected Vector2 position;
    protected float angle;
    protected Texture texture;

    public Entity(String texturePath) {
        this.texture = new Texture(texturePath);
        this.position = new Vector2();
    }

    public Entity(String texturePath, Vector2 position, float angle) {
        this.texture = new Texture(texturePath);
        this.position = position;
        this.angle = angle;
    }

    public Entity(String texturePath, float x, float y, float angle) {
        this.texture = new Texture(texturePath);
        this.position = new Vector2(x, y);
        this.angle = angle;
    }


    public Vector2 getPosition() {
        return position;
    }

    public float getAngleDegree() {
        return angle;
    }

    public int getWidth() {
        return this.texture.getWidth();
    }

    public int getHeight() {
        return this.texture.getHeight();
    }

    public float getAngleRadian() {
        return Utils.toRadians(angle);
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setPosition(float x, float y) {
        this.position.x = x;
        this.position.y = y;
    }

    public void setAngleRadian(float angle) {
        this.angle = Utils.toDegrees(angle);
    }

    public void setAngleDegree(float angle) {
        this.angle = angle;
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
