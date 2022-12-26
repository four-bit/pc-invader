package com.fourbit.pc_invader.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.fourbit.pc_invader.utils.Utils;


public class PhysicsEntity extends Entity {
    protected final float speed;  // In meter
    protected final Vector2 movement;   // In meter
    protected Body body;
    protected FixtureDef fixtureDef;


    public PhysicsEntity(World world, BodyType bodyType, String texturePath, float speed) {
        super(texturePath);
        this.speed = speed;
        this.movement = new Vector2();
        this.create(world, bodyType);
    }

    public PhysicsEntity(World world, BodyType bodyType, String texturePath, Vector2 position, float angle, float speed) {
        super(texturePath, position, angle);
        this.speed = speed;
        this.movement = new Vector2();
        this.create(world, bodyType);
    }

    public PhysicsEntity(World world, BodyType bodyType, String texturePath, float x, float y, float angle, float speed) {
        super(texturePath, x, y, angle);
        this.speed = speed;
        this.movement = new Vector2();
        this.create(world, bodyType);
    }


    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        this.body.setTransform(Utils.toMeters(super.position), 0.0f);
    }

    public float getSpeed() {
        return speed;
    }

    public Vector2 getMovement() {
        return movement;
    }

    public Body getBody() {
        return body;
    }


    public void create(World world, BodyType bodyType) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(Utils.toMeters(super.position));

        this.body = world.createBody(bodyDef);
        this.fixtureDef = new FixtureDef();
        this.body.setUserData(this);
    }
}
