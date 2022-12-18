package com.fourbit.pc_invader.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Pool;
import com.fourbit.pc_invader.utils.GameComponent;
import com.fourbit.pc_invader.utils.Utils;


public class Bullet extends Entity implements Pool.Poolable, GameComponent {
    private boolean alive;
    private final float speed;
    private final Body body;
    private final PolygonShape collisionBox;


    public Bullet(World world, String texturePath, float speed) {
        super(texturePath);
        this.alive = false;
        this.speed = speed;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(Utils.toMeters(super.position));
        this.body = world.createBody(bodyDef);

        this.collisionBox = new PolygonShape();
        this.collisionBox.setAsBox(Utils.toMeters(super.texture.getWidth()), Utils.toMeters(super.texture.getHeight()));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = this.collisionBox;
        fixtureDef.isSensor = true;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.0f;

        this.body.createFixture(fixtureDef);
        this.body.setUserData(this);
    }


    public void init(Vector2 position) {
        super.position.set(position);
        this.body.getPosition().set(Utils.toMeters(position));
        this.alive = true;
    }

    public void init(float x, float y) {
        super.position.set(x, y);
        this.body.getPosition().set(Utils.toMeters(position));
        this.alive = true;
    }

    @Override
    public void update() {
        super.update();

        // TODO: Implement bullet physics here

        super.position.set(this.body.getPosition());
        if (Utils.isOutOfScreen(this)) this.alive = false;
    }

    @Override
    public void reset() {
        this.alive = false;
    }

    @Override
    public void dispose() {
        this.reset();
        this.collisionBox.dispose();
        super.dispose();
    }
}
