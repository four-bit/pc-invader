package com.fourbit.pc_invader.boss;



import com.badlogic.gdx.utils.Array;




public class Boss {
    private int x, y, speed;
    private Head head;
    private Array<Body> bodies = new Array<>();
    private float angle;

    public Boss(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;

        head = new Head(
                this.x,
                this.y,
                2
        );

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
    }

    public void dispose() {
        head.dispose();
        for (int i = 0; i < bodies.size; i++) {
            bodies.get(i).dispose();
        }
    }
}



