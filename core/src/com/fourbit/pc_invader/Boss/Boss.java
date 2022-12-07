package com.fourbit.pc_invader.Boss;


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

    private Array<Body> bodies = new Array<>();
    private float angle;
    private SpriteBatch batch;

    public Boss(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.angle = angle;
        for (int i = 0; i < bodies.size; i++) {
            bodies.add(new Body(x,y, speed));
        }
        head = new Head(x,y,speed);
    }

    public float getAngle() {
        return angle;
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

    public void renderGame(){
        batch.draw(
                head.getTexture(),
                this.getX() -(float)head.getTexture().getWidth(),
                this.getY() - (float) head.getTexture().getHeight() ,
                head.getTexture().getWidth() * 5,
                head.getTexture().getHeight() * 5
        );

    }
}


