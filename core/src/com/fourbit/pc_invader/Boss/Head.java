package com.fourbit.pc_invader.Boss;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.sun.tools.javac.main.Option;
import com.badlogic.gdx.utils.Array;
import static com.fourbit.pc_invader.PcInvader.GAME_WIDTH;
import static com.fourbit.pc_invader.PcInvader.GAME_HEIGHT;
import static java.lang.Math.toRadians;

public class Head {

    private int x, y;
    private Texture texture;
    private float speed, angle;
    public enum State {GOINGUP,GOINGDOWN,UNDERGROUND,SNED};
    public State state=State.GOINGUP;

    public int currentHeight = 800;



    public Head(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.initGraphics();
    }

    public void initGraphics() {
        this.texture = new Texture("boss/Boss-head3.png");
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

    public float getSpeed() {
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


    public void update() {
//        switch (state) {
//            case GOINGUP:
//                this.x += this.speed*2;
////                this.y += this.speed;
//                if (this.x > currentHeight) {
//                    state = State.GOINGDOWN;
//                    currentHeight = 800;
//                }
//            break;
//            case GOINGDOWN:
//                this.x -= this.speed*2;
////                this.y -= this.speed;
//                    if(this.x < currentHeight){
//                    currentHeight  = 1000;
//                    state = State.GOINGUP;
//                }
//                break;
//        }
    this.x -= this.speed;
    }
}

