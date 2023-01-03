package com.fourbit.pc_invader.entities.boss;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import com.fourbit.pc_invader.entities.PhysicsEntity;
import com.fourbit.pc_invader.utils.Anchor;
import com.fourbit.pc_invader.utils.BodyEditorLoader;
import com.fourbit.pc_invader.utils.Utils;


public class Main extends PhysicsEntity {
    private final BossConfig config;
    private int currentAnchorIndex;
    private long lastStop;
    private boolean stopped;

    public Main(World world, float x, float y, float speed, BossConfig bossConfig) {
        super(world, BodyDef.BodyType.KinematicBody, "entities/boss/main.png", x, y, 0.0f, speed);

        super.fixtureDef = new FixtureDef();
        super.fixtureDef.density = 1.0f;
        super.fixtureDef.friction = 0.0f;
        super.fixtureDef.restitution = 0.0f;
        super.fixtureDef.isSensor = true;
        new BodyEditorLoader(Gdx.files.internal("entities/boss/main.body.json")).attachFixture(super.body, "body", super.fixtureDef, Utils.toMeters(super.getWidth()));
        super.body.setAngularVelocity(0);
        this.config = bossConfig;
        this.currentAnchorIndex = 0;
        this.stopped = false;
    }


    public Anchor getCurrentAnchor() {
        return this.config.getAnchor(this.currentAnchorIndex);
    }


    @Override
    public void update() {
        if (!this.stopped) {
            Anchor currentAnchor = this.config.getAnchor(this.currentAnchorIndex);

            this.body.setLinearVelocity(
                    this.body.getPosition()
                            .sub(currentAnchor.getPhysicsPos())
                            .rotateDeg(180.0f)
            );

            if (currentAnchor.getHomingSpeed() < 0.0f || 0.0f < currentAnchor.getHomingSpeed()) {
                this.body.setLinearVelocity(this.body.getLinearVelocity().setLength(currentAnchor.getHomingSpeed()));
            }

            if (this.body.getPosition().sub(currentAnchor.getPhysicsPos()).len2() < 1.0f) {
                if (currentAnchor.getDelay() > 0) {
                    this.stopped = true;
                    this.lastStop = System.nanoTime();
                    this.body.setLinearVelocity(0, 0);
                } else {
                    this.currentAnchorIndex = (this.currentAnchorIndex + 1) % this.config.getAnchorsCount();
                }
            }
        } else {
            long currentTime = System.nanoTime();
            if (currentTime - this.lastStop > this.getCurrentAnchor().getDelay() * 1000000) {
                this.currentAnchorIndex = (this.currentAnchorIndex + 1) % this.config.getAnchorsCount();
                this.stopped = false;
            }
        }
    }
}
