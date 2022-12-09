package com.fourbit.pc_invader.boss;

import com.badlogic.gdx.graphics.Texture;

public class Body {
    private int x, y, speed;
    private Texture texture;
    private float angle;

    public enum State {GOINGUP, GOINGDOWN, UNDERGROUND, SNED}

    ;
    public State state = State.GOINGUP;
    public int currentHeight = 800;

    public Body(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.initGraphics();
    }

    public void initGraphics() {
        this.texture = new Texture("boss/wormbody.png");
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void update() {
////        if (this.x >x) {
//            this.x -= this.speed;
////        }
////        if (this.x < x){
//            this.x += this.speed;
        this.x -=speed;
        }
    }



