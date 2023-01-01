package com.fourbit.pc_invader.utils;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.fourbit.pc_invader.entities.Bullet;
import com.fourbit.pc_invader.entities.boss.Boss;
import com.fourbit.pc_invader.entities.player.Player;
import com.sun.tools.javac.main.Option;



public class CollisionListener  implements ContactListener {
    Boss boss;
    Player player;
    int timer;
    World world;
    public Array<Body> toBeDeletedBullet;
    public CollisionListener(Player player, Boss boss, World world){
        this.player = player;
        this.boss = boss;
        this.world = world;
    }
    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        if (fb.getBody().getUserData() == player){
            timer++;
            if (timer == 10) {
                try {
                    player.setHp(player.getHp() - 1);
                } catch (Option.InvalidValueException e) {
                    e.printStackTrace();
                }
                timer = 0;
            }
        }
        for (int i = 0;i < player.getActiveBullets().size; i++){
            if (fb.getBody().getUserData() == player.getActiveBullets().get(i)&&fa.getBody().getUserData()!=player) {
                timer++;
                if (timer == 30) {
                    try {
                        boss.setHp(boss.getHp() - 1);
                    } catch (Option.InvalidValueException e) {
                        e.printStackTrace();
                    }
                    timer = 0;
                }
            }
        }
    }


    @Override
    public void endContact(Contact contact) {

    }


    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }



}
