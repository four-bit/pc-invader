package com.fourbit.pc_invader.entities.boss;


import com.badlogic.gdx.graphics.g2d.Batch;
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
                2,
                0.0f
        );

        for (int i = 0; i < 7; i++) {
            bodies.add(new Body(
                    this.x,
                    this.y,
                    2,
                    0.0f
            ));
        }
    }


    public Head getHead() {
        return head;
    }

    public Array<Body> getBodies() {
        return bodies;
    }

    public void update() {
        bodies.get(0).update(head.getX(), head.getY());
        head.update();
        for (int i = 1; i < bodies.size; i++) {
            if (
                    bodies.get(i).getX() - bodies.get(i - 1).getX() > 50 ||
                            bodies.get(i).getY() - bodies.get(i).getY() < -75 ||
                            bodies.get(i - 1).getX() - bodies.get(i).getX() > 50 ||
                            bodies.get(i).getY() - bodies.get(i - 1).getY() > 20
            ) {
                bodies.get(i).update(bodies.get(i - 1).getX(), bodies.get(i - 1).getY());
            }
        }
    }

    public void draw(Batch batch) {
        for (int i = getBodies().size - 1; i > 2; i--) {
            batch.draw(
                    getBodies().get(i).getTexture(),
                    getBodies().get(i).getX() + 200 + (int) Math.pow(2 * i, 2),
                    getBodies().get(i).getY() - 50 - 75 * i,
                    (float) getBodies().get(i).getTexture().getWidth() / 2,
                    (float) getBodies().get(i).getTexture().getHeight() / 2,
                    getBodies().get(i).getTexture().getWidth(),
                    getBodies().get(i).getTexture().getHeight(),
                    1.0f,
                    1.0f,
                    getBodies().get(i).getAngle(),
                    0,
                    0,
                    getHead().getTexture().getWidth(),
                    getHead().getTexture().getHeight(),
                    false,
                    false
            );
        }
        for (int i = 2; i > -1; i--) {
            batch.draw(
                    getBodies().get(i).getTexture(),
                    getBodies().get(i).getX() + 212 - (int) Math.pow(2 * i, 2),
                    getBodies().get(i).getY() - 50 - 75 * i,
                    (float) getBodies().get(i).getTexture().getWidth() / 2,
                    (float) getBodies().get(i).getTexture().getHeight() / 2,
                    getBodies().get(i).getTexture().getWidth(),
                    getBodies().get(i).getTexture().getHeight(),
                    1.0f,
                    1.0f,
                    getBodies().get(i).getAngle(),
                    0,
                    0,
                    getHead().getTexture().getWidth(),
                    getHead().getTexture().getHeight(),
                    false,
                    false
            );
        }


        batch.draw(
                getHead().getTexture(),
                getHead().getX(),
                getHead().getY(),
                (float) getHead().getTexture().getWidth() / 2,
                (float) getHead().getTexture().getHeight() / 2,
                getHead().getTexture().getWidth(),
                getHead().getTexture().getHeight(),
                1.0f,
                1.0f,
                getHead().getAngle(),
                0,
                0,
                getHead().getTexture().getWidth(),
                getHead().getTexture().getHeight(),
                false,
                getHead().getFlip()
        );
    }

    public void dispose() {
        head.dispose();
        for (int i = 0; i < bodies.size; i++) {
            bodies.get(i).dispose();
        }
    }
}



