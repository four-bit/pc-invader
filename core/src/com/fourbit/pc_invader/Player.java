package com.fourbit.pc_invader;

import com.badlogic.gdx.graphics.Texture;

public class Player {
    private Texture texture;
    private int x, y, speed;

    Player() {
        texture = null;
        this.x = 0;
        this.y = 0;
        speed = 0;
    }

    Player(int x, int y, int speed, String spriteInternalPath) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.texture = new Texture(spriteInternalPath);
    }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    public int getSpeed() { return this.speed; }
    public int getWidth() { return this.texture.getWidth(); }
    public int getHeight() { return this.texture.getHeight(); }
    public Texture getTexture() { return this.texture; }


    public void dispose() {
        this.texture.dispose();
    }
}
