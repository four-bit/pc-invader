package com.fourbit.pc_invader.entities.player;

import com.badlogic.gdx.physics.box2d.World;

import static com.fourbit.pc_invader.utils.Globals.PLAYER_BULLET_SPEED;


public class Bullet extends com.fourbit.pc_invader.entities.Bullet {
    public Bullet(World world) {
        super(world, "entities/player/bullet.png", PLAYER_BULLET_SPEED);
    }
}
