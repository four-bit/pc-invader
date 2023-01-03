package com.fourbit.pc_invader.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Pool;

import com.fourbit.pc_invader.utils.GameComponent;
import com.fourbit.pc_invader.utils.Utils;


public class Bullet extends PhysicsEntity implements Pool.Poolable, GameComponent {
    private boolean alive;
    private final PolygonShape collisionBox;


    public Bullet(World world, String texturePath, float speed) {
        super(world, BodyDef.BodyType.DynamicBody, texturePath, speed);
        this.alive = false;

        this.collisionBox = new PolygonShape();
        this.collisionBox.setAsBox(
                Utils.toMeters(super.getWidth() * 0.5f),
                Utils.toMeters(super.getHeight() * 0.5f)
        );
        super.fixtureDef.shape = this.collisionBox;
        super.fixtureDef.isSensor = true;
        super.fixtureDef.density = 0.5f;
        super.fixtureDef.friction = 0.0f;
        super.fixtureDef.restitution = 1.0f;
        super.body.createFixture(fixtureDef);
        super.body.setBullet(true);
        super.body.setLinearVelocity(new Vector2().setLength(super.speed).setAngleDeg(super.angle));
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

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    @Override
    public void update() {
        super.update();
        this.body.setTransform(
                this.body.getPosition().add(
                        new Vector2(speed, 0).setAngleRad(this.body.getAngle())
                ),
                this.body.getAngle());
        super.position = Utils.toPixels(this.body.getPosition());
        if (Utils.isOutOfScreen(this, super.getWidth())) this.alive = false;
    }

    @Override
    public void draw(Batch batch) {
        if (this.alive) {
            super.draw(batch);
        }
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
