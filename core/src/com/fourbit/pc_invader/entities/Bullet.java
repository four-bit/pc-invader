package com.fourbit.pc_invader.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
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
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(Utils.toMeters(super.position));
        this.body = world.createBody(bodyDef);

        this.collisionBox = new PolygonShape();
        this.collisionBox.setAsBox(
                Utils.toMeters((float) super.texture.getWidth() / 2),
                Utils.toMeters((float) super.texture.getHeight() / 2)
        );

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = this.collisionBox;
        fixtureDef.isSensor = true;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.0f;

        this.body.createFixture(fixtureDef);
        this.body.setBullet(true);
        this.body.setUserData(this);

        this.body.setLinearVelocity(Utils.toMeters(new Vector2().setLength(this.speed).setAngleDeg(super.angle)));
    }


    public boolean isAlive() {
        return alive;
    }


    public void init(Vector2 position, float angle) {
        super.position.set(Utils.toPixels(position));
        super.angle = angle;
        this.body.setTransform(position, super.getAngleRadian());
        this.alive = true;
    }

    public void init(float x, float y, float angle) {
        super.position.set(Utils.toPixels(x), Utils.toPixels(y));
        super.angle = angle;
        this.body.setTransform(x, y, super.getAngleRadian());
        this.alive = true;
    }

    @Override
    public void update() {
        super.update();
        this.body.setTransform(
                this.body.getPosition().add(
                        new Vector2(Utils.toMeters(speed), 0).setAngleRad(this.body.getAngle())
                ),
                this.body.getAngle());
        super.position = Utils.toPixels(this.body.getPosition());
        if (Utils.isOutOfScreen(this, super.getWidth())) this.alive = false;
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
