package com.fourbit.pc_invader.entities.boss;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.fourbit.pc_invader.entities.PhysicsEntity;
import com.fourbit.pc_invader.utils.Utils;

public class Bullet extends com.fourbit.pc_invader.entities.Bullet {
    public Bullet(World world, float speed) {
        super(world, "entities/player/bullet.png", (float) 0.3);
    }
}

