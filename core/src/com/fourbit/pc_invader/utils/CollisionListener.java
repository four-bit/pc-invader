package com.fourbit.pc_invader.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.fourbit.pc_invader.entities.Bullet;
import com.fourbit.pc_invader.entities.boss.Boss;
import com.fourbit.pc_invader.entities.player.Player;
import com.sun.tools.javac.main.Option;


public class CollisionListener  implements ContactListener {
    Bullet bullet;
    Boss boss;
    Player player;

    public CollisionListener(Player player, Boss boss){
        this.player = player;
        this.boss = boss;
    }
    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        Object classA = contact.getFixtureA().getBody().getUserData().getClass();
        Object classB = contact.getFixtureB().getBody().getUserData().getClass();
        if (fa.getUserData() == player && fb.getUserData() == boss){
            try {
                player.setHp(player.getHp() - 1);
            } catch (Option.InvalidValueException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        System.out.println("A collision happen");

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}
