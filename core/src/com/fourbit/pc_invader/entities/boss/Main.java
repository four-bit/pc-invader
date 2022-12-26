package com.fourbit.pc_invader.entities.boss;


import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.fourbit.pc_invader.entities.PhysicsEntity;
import com.fourbit.pc_invader.utils.BodyEditorLoader;
import com.fourbit.pc_invader.utils.Utils;


public class Main extends PhysicsEntity {
    public Main(World world, float x, float y, float speed) {
        super(world, BodyDef.BodyType.KinematicBody, "entities/boss/main.png", x, y, 0.0f, speed);

        super.fixtureDef = new FixtureDef();
        super.fixtureDef.density = 1.0f;
        super.fixtureDef.friction = 0.0f;
        super.fixtureDef.restitution = 0.0f;
        new BodyEditorLoader(Gdx.files.internal("entities/boss/main.body.json")).attachFixture(super.body, "body", super.fixtureDef, Utils.toMeters(super.getWidth()));
        super.body.setAngularVelocity(0);
    }


    @Override
    public void update() {

    }
}
