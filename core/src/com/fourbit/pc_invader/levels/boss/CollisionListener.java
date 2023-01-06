package com.fourbit.pc_invader.levels.boss;

import com.badlogic.gdx.physics.box2d.*;

import com.fourbit.pc_invader.PcInvader;
import com.sun.tools.javac.main.Option;


public class CollisionListener extends com.fourbit.pc_invader.utils.CollisionListener {
    Level level;
    int bossTimer;
    int playerTimer;
    int bulletTimer;
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
            this.level.player.setTexture("entities/player/sprite.hit.png");
            if (this.playerTimer == 10) {
                if (this.level.player.getHp() - this.level.boss.getConfig().getContactDmg() >= 0) {
                    try {
                        this.level.player.setHp(this.level.player.getHp() - this.level.boss.getConfig().getContactDmg());
                        PcInvader.crashSound.play(1f);
                    } catch (Option.InvalidValueException e) {
                        throw new RuntimeException(e);
                    }
                }
                this.playerTimer = 0;
            }
        }

        for (int i = 0; i < this.level.boss.getMain().getActiveBullets().size; i++) {
            if ((fb.getBody().getUserData() == this.level.player && fa.getBody().getUserData() == this.level.boss.getMain().getActiveBullets().get(i)) ||
                    fa.getBody().getUserData() == this.level.player && fb.getBody().getUserData() == this.level.boss.getMain().getActiveBullets().get(i)) {
                this.bulletTimer++;
                this.level.player.setTexture("entities/player/sprite.hit.png");
                if (this.bulletTimer == 10) {
                    if (this.level.player.getHp() - this.level.boss.getConfig().getBulletDmg() >= 0) {
                        try {
                            this.level.player.setHp(this.level.player.getHp() - this.level.boss.getConfig().getBulletDmg());
                            PcInvader.crashSound.play(1f);
                        } catch (Option.InvalidValueException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    this.bulletTimer = 0;
                }
                this.level.boss.getMain().getBulletPool().free(this.level.boss.getMain().getActiveBullets().get(i));
                this.level.boss.getMain().getBulletPool().clear();
            }
        }

        for (int i = 0; i < this.level.player.getActiveBullets().size; i++) {
            if ((fb.getBody().getUserData() == this.level.player.getActiveBullets().get(i) && fa.getBody().getUserData() == this.level.boss.getMain()) ||
                    fa.getBody().getUserData() == this.level.player.getActiveBullets().get(i) && fb.getBody().getUserData() == this.level.boss.getMain()) {
                this.bossTimer++;
                this.level.boss.getMain().setTexture("entities/boss/main.hit.png");
                if (this.level.boss.getHp() - this.level.player.getConfig().getBulletDmg() >= 0) {
                    try {
                        this.level.boss.setHp(this.level.boss.getHp() - this.level.player.getConfig().getBulletDmg());
                        PcInvader.hitSound.play(1f);
                    } catch (Option.InvalidValueException e) {
                        e.printStackTrace();
                    }
                }
                this.level.player.getBulletPool().free(this.level.player.getActiveBullets().get(i));
                this.level.player.getBulletPool().clear();
            }
        }
    }


    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        this.bossTimer++;
        if (fa.getBody().getUserData() == this.level.boss.getMain() || fb.getBody().getUserData() == this.level.boss.getMain()) {
            this.level.boss.getMain().setTexture("entities/boss/main.png");
            bossTimer = 0;
        }
        if (fa.getBody().getUserData() == this.level.player || fb.getBody().getUserData() == this.level.player){
           this.level.player.setTexture("entities/player/sprite.png");
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
}
