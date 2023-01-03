package com.fourbit.pc_invader.utils;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.fourbit.pc_invader.entities.Bullet;
import com.fourbit.pc_invader.entities.boss.Boss;
import com.fourbit.pc_invader.entities.player.Player;
import com.sun.tools.javac.main.Option;


public class CollisionListener implements ContactListener {
    Boss boss;
    Player player;
    int bossTimer;
    int playerTimer;
    World world;
    public Array<Bullet> toBeDeletedBullet = new Array<>();

    public CollisionListener(Player player, Boss boss, World world) {
        this.player = player;
        this.boss = boss;
        this.world = world;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        if (    (fb.getBody().getUserData() == player && fa.getBody().getUserData() == boss.getMain()) ||
                fa.getBody().getUserData() == player && fb.getBody().getUserData() == boss.getMain()) {
            playerTimer++;
            if (playerTimer == 10) {
                try {
                    player.setHp(player.getHp() - 1);
                } catch (Option.InvalidValueException e) {
                    e.printStackTrace();
                }
                playerTimer = 0;
            }
        }
        for (int i = 0; i < player.getActiveBullets().size; i++) {
                if ((fb.getBody().getUserData() == player.getActiveBullets().get(i) && fa.getBody().getUserData() == boss.getMain()) ||
                        fa.getBody().getUserData() == player.getActiveBullets().get(i) && fb.getBody().getUserData() == boss.getMain()) {
                    bossTimer++;
                    player.getBulletPool().free(player.getActiveBullets().get(i));
                    player.getBulletPool().clear();
                    toBeDeletedBullet.add(player.getActiveBullets().get(i));
                    if (bossTimer == 1) {
                        try {
                            boss.setHp(boss.getHp() - 1);
                        } catch (Option.InvalidValueException e) {
                            e.printStackTrace();
                        }
                        bossTimer = 0;
                }
            }
        }
    }


    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

    }


    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public void update() {
        for (int i=0; i<toBeDeletedBullet.size; i++){
            world.destroyBody(toBeDeletedBullet.get(i).getBody());
        }
        toBeDeletedBullet.clear();
    }

}

