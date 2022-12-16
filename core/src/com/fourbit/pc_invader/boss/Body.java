package com.fourbit.pc_invader.boss;

import com.badlogic.gdx.graphics.Texture;

public class Body {
    private int x, y, speed;
    private Texture texture;
    private float angle;

    public enum State {GOINGDOWN, GOINGDOWNLEFT, GOINGLEFT, GOINGUPLEFT, GOINGUP, GOINGUPRIGHT, GOINGRIGHT, GOINGDOWNRIGHT}

    public State state = State.GOINGUP;
    public int currentHeight = 800;
    public static final int GAME_WIDTH = 1920;
    public static final int GAME_HEIGHT = 1080;

    public Body(int x, int y, int speed, float angle) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.angle = angle;
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

    public void checkDirection(int x, int y) {
        if (this.x == x && this.y > y) {
            this.state = State.GOINGDOWN;
        }
        if (this.x > x && this.y > y) {
            this.state = State.GOINGDOWNLEFT;
        }
        if (this.x > x && this.y == y) {
            this.state = State.GOINGLEFT;
        }
        if (this.x > x && this.y < y) {
            this.state = State.GOINGUPLEFT;
        }
        if (this.x == x && this.y < y) {
            this.state = State.GOINGUP;
        }
        if (this.x < x && this.y < y) {
            this.state = State.GOINGUPRIGHT;
        }
        if (this.x < x && this.y == y) {
            this.state = State.GOINGRIGHT;
        }
        if (this.x < x && this.y > y) {
            this.state = State.GOINGDOWNRIGHT;
        }
    }

    public void update(int x, int y) {
        switch (state) {
            case GOINGDOWN:
                this.y -= this.speed;
                checkDirection(x, y);
                break;
            case GOINGDOWNLEFT:
                this.x -= this.speed;
                this.y -= this.speed;
                checkDirection(x, y);
                break;
            case GOINGLEFT:
                this.x -= this.speed;
                checkDirection(x, y);
                break;
            case GOINGUPLEFT:
                this.x -= this.speed;
                this.y += this.speed;
                checkDirection(x, y);
                break;
            case GOINGUP:
                this.y += this.speed;
                checkDirection(x, y);
                break;
            case GOINGUPRIGHT:
                this.x += this.speed;
                this.y += this.speed;
                checkDirection(x, y);
                break;
            case GOINGRIGHT:
                this.x += this.speed;
                checkDirection(x, y);
                break;
            case GOINGDOWNRIGHT:
                this.x += this.speed;
                this.y -= this.speed;
                checkDirection(x, y);
                break;
        }
        this.angle = 180 + (float) Math.toDegrees( Math.atan2((float) (this.y-GAME_HEIGHT/2),(float)(this.x-GAME_WIDTH/2)));
    }

    public void dispose() {
        texture.dispose();
    }


}



