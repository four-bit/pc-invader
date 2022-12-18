package com.fourbit.pc_invader.entities.boss;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.fourbit.pc_invader.utils.BodyEditorLoader;
import com.fourbit.pc_invader.entities.Sprite;


public class Boss extends Sprite {
    private float x, y, speed;
    private Head head;
    private Array<Body> bodies = new Array<>();
    private float angle;
    private com.badlogic.gdx.physics.box2d.Body body;
    private BodyEditorLoader loader ;


    public Boss(World world, float x, float y, int speed) {
        super("entities/boss/wormhead.png");
        super.angle = angle;
        super.setPosition(x, y);
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



        initPhysics(world);
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


    public void initPhysics(World world){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(super.getPosition());
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;
        fixtureDef.isSensor = true;

        float scale = this.texture.getWidth();
        loader = new BodyEditorLoader(Gdx.files.internal("entities/boss/head.json"));
        loader.attachFixture(body, "wormhead.png", fixtureDef, scale);

        body.setAngularVelocity(0);
        body.setUserData(this);
    }

    public void dispose() {
        head.dispose();
        for (int i = 0; i < bodies.size; i++) {
            bodies.get(i).dispose();
        }
    }
}



