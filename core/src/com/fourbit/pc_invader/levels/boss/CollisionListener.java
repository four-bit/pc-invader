package com.fourbit.pc_invader.levels.boss;

import com.badlogic.gdx.physics.box2d.*;
import com.sun.tools.javac.main.Option;


public class CollisionListener extends com.fourbit.pc_invader.utils.CollisionListener {
    Level level;
    int bossTimer;
    int playerTimer;

    public CollisionListener(World world, Level level) {
        super(world);
        this.level = level;
    }


    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        if ((fb.getBody().getUserData() == this.level.player && fa.getBody().getUserData() == this.level.boss.getMain()) ||
                fa.getBody().getUserData() == this.level.player && fb.getBody().getUserData() == this.level.boss.getMain()) {
            this.playerTimer++;
            if (this.playerTimer == 10) {
                if (this.level.player.getHp() > 0) {
                    try {
                        this.level.player.setHp(this.level.player.getHp() - 1);
                    } catch (Option.InvalidValueException e) {
                        throw new RuntimeException(e);
                    }
                }
                this.playerTimer = 0;
            }
        }

        for (int i = 0; i < this.level.player.getActiveBullets().size; i++) {
            if ((fb.getBody().getUserData() == this.level.player.getActiveBullets().get(i) && fa.getBody().getUserData() == this.level.boss.getMain()) ||
                    fa.getBody().getUserData() == this.level.player.getActiveBullets().get(i) && fb.getBody().getUserData() == this.level.boss.getMain()) {
                this.bossTimer++;
                this.level.player.getBulletPool().free(this.level.player.getActiveBullets().get(i));
                this.level.player.getBulletPool().clear();
                if (this.bossTimer == 1) {
                    try {
                        if (this.level.boss.getHp() > 0) this.level.boss.setHp(this.level.boss.getHp() - 1);
                    } catch (Option.InvalidValueException e) {
                        e.printStackTrace();
                    }
                    this.bossTimer = 0;
                }
            }
        }
    }


    @Override
    public void endContact(Contact contact) {}

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
}
