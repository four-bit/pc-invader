package com.fourbit.pc_invader.entities.player;

import com.badlogic.gdx.physics.box2d.World;
import com.fourbit.pc_invader.utils.Globals;


public class Bullet extends com.fourbit.pc_invader.entities.Bullet {
    public Bullet(World world) {
        super(world, "entities/player/bullet.png", Globals.PLAYER_BULLET_SPEED);
    }
}
