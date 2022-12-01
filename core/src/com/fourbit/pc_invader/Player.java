package com.fourbit.pc_invader;

import com.badlogic.gdx.graphics.Texture;
import com.sun.tools.javac.main.Option;


public class Player {
    private Texture texture;
    private int x, y, speed, healthPoints, shieldPoints;
    private boolean hasShield;

    Player() {
        this.texture = null;
        this.x = 0;
        this.y = 0;
        this.speed = 0;
        this.healthPoints = 0;
        this.shieldPoints = -1;
        this.hasShield = false;
    }

    Player(
            int x, int y,
            int speed,
            int maxHealth,
            int maxShield, boolean hasShield,
            String spriteInternalPath) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.healthPoints = maxHealth;
        this.shieldPoints = maxShield;
        this.hasShield = hasShield;
        this.texture = new Texture(spriteInternalPath);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return this.speed;
    }

    public int getHealthPoints() {
        return this.healthPoints;
    }

    public int getShieldPoints() {
        return this.hasShield ? this.shieldPoints : -1;
    }

    public Texture getTexture() {
        return this.texture;
    }

    public int getWidth() {
        return this.texture.getWidth();
    }

    public int getHeight() {
        return this.texture.getHeight();
    }

    public void setX(int val) {
        this.x = val;
    }

    public void setY(int val) {
        this.y = val;
    }

    public void setSpeed(int val) {
        this.speed = val;
    }

    public void setHealthPoints(int val) throws Option.InvalidValueException {
        if (val >= 0) {
            this.healthPoints = val;
        } else {
            throw new Option.InvalidValueException("val cannot be negative.");
        }
    }

    public void setShieldPoints(int val) throws Option.InvalidValueException {
        if (val >= 0) {
            if (!this.hasShield) {
                this.hasShield = true;
            }
            this.shieldPoints = val;
        } else {
            throw new Option.InvalidValueException("val cannot be negative.");
        }
    }

    public void disableShield() {
        this.hasShield = false;
        this.shieldPoints = -1;
    }


    public void dispose() {
        this.texture.dispose();
    }
}
