package com.fourbit.pc_invader.Boss;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.sun.tools.javac.main.Option;
import com.badlogic.gdx.utils.Array;
import  com.badlogic.gdx.utils.Timer;

import java.util.TimerTask;

import static com.fourbit.pc_invader.PcInvader.GAME_WIDTH;
import static com.fourbit.pc_invader.PcInvader.GAME_HEIGHT;


public class Boss {
    private int x, y, speed;
    private Head head;
    private Timer timer;
    private TimerTask timerTask;
    public int delta;

    private Array<Body> bodies = new Array<>();
    private float angle;
    private SpriteBatch batch;
    private Sprite sprite;
    private int i;
    public Boss(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;

        head = new Head(
                this.x,
                this.y,
                2
        );

//        for (int i = 0; i < 7; i++) {
//            bodies.add(new Body(
//                    head.getX() -head.getTexture().getWidth() +head.getTexture().getWidth()/2,
//                    head.getY() -head.getTexture().getHeight()- 75 *i,
//                    2
//            ));
//        }
        for (int i = 0; i < 7; i++) {
            bodies.add(new Body(
                    this.x,
                    this.y,
                    2
            ));
        }
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
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

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public Array<Body> getBodies() {
        return bodies;
    }

    public void setBodies(Array<Body> bodies) {
        this.bodies = bodies;
    }

    public void update() {
        head.update();

//        head.update(head.getLocation().get(i).get("x"), head.getLocation().get(i).get("y"));
//
//        delta += Gdx.graphics.getDeltaTime()*100;
//        System.out.println(delta);
//        if (delta ==60){
//            if (i < this.location.size()-1) {
//                i++;
//                delta = 0;
//                System.out.println(i);
//            }else {
//                i=0;
//                delta = 0;
//                System.out.println(i);
//            }
//        }//
    }
}



