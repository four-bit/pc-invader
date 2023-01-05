package com.fourbit.pc_invader.entities.player;

import com.badlogic.gdx.physics.box2d.World;

public class Bullet extends com.fourbit.pc_invader.entities.Bullet {
    public Bullet(World world, float speed) {
        super(world, "entities/player/bullet.png", speed);
    }
}
