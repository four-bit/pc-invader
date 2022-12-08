package com.fourbit.pc_invader.Boss;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.sun.tools.javac.main.Option;
import com.badlogic.gdx.utils.Array;
import static com.fourbit.pc_invader.PcInvader.GAME_WIDTH;
import static com.fourbit.pc_invader.PcInvader.GAME_HEIGHT;


public class Boss {
    private int x, y, speed;
    private Head head;

    private float angle;
    private SpriteBatch batch;
    private Sprite sprite;
    public Boss(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public int getSpeed() {
        return speed;
    }


    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }


    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void update(){
        head.update();


    }
   }



