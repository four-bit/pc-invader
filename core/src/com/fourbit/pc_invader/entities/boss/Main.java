package com.fourbit.pc_invader.entities.boss;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.fourbit.pc_invader.entities.PhysicsEntity;
import com.fourbit.pc_invader.utils.Anchor;
import com.fourbit.pc_invader.utils.BodyEditorLoader;
import com.fourbit.pc_invader.utils.Resettable;
import com.fourbit.pc_invader.utils.Utils;

import java.util.Random;


public class Main extends PhysicsEntity implements Resettable {
    private final BossConfig config;
    private final Array<Bullet> activeBullets;
    private Pool<Bullet> bulletPool;
    private final Random random = new Random();

    private int currentAnchorIndex, bulletCount;
    private long lastStop;
    private boolean stopped;
    private long lastShot;

    public Main(World world, float x, float y, float speed, BossConfig bossConfig) {
        super(world, BodyDef.BodyType.KinematicBody, "entities/boss/main.png", x, y, 0.0f, speed);

        super.fixtureDef = new FixtureDef();
        super.fixtureDef.density = 1.0f;
        super.fixtureDef.friction = 0.0f;
        super.fixtureDef.restitution = 0.0f;
        super.fixtureDef.isSensor = true;
        new BodyEditorLoader(Gdx.files.internal("entities/boss/main.body.json")).attachFixture(super.body, "body", super.fixtureDef, Utils.toMeters(super.getWidth()));
        super.body.setAngularVelocity(0);

        this.config = bossConfig;
        this.currentAnchorIndex = 0;
        this.stopped = false;

        this.activeBullets = new Array<>();

        this.lastShot = System.nanoTime();
    }


    public Anchor getCurrentAnchor() {
        return this.config.getAnchor(this.currentAnchorIndex);
    }

    public Array<Bullet> getActiveBullets() {
        return this.activeBullets;
    }

    public Pool<Bullet> getBulletPool() {
        return bulletPool;
    }

    @Override
    public void reset() {
        this.currentAnchorIndex = 0;
        this.stopped = false;
        this.bulletPool.freeAll(this.activeBullets);
        this.bulletPool.clear();
        this.activeBullets.clear();
    }

    @Override
    public void update() {
        final float bulletSpeed = this.config.getBulletSpeed();
        this.bulletPool = new Pool<Bullet>() {
            @Override
            protected Bullet newObject() {
                return new Bullet(body.getWorld(), bulletSpeed);
            }
        };

        // Movement
        if (!this.stopped) {
            Anchor currentAnchor = this.config.getAnchor(this.currentAnchorIndex);

            this.body.setLinearVelocity(
                    this.body.getPosition()
                            .sub(currentAnchor.getPhysicsPos())
                            .rotateDeg(180.0f)
            );

            if (currentAnchor.getHomingSpeed() < 0.0f || 0.0f < currentAnchor.getHomingSpeed()) {
                this.body.setLinearVelocity(this.body.getLinearVelocity().setLength(currentAnchor.getHomingSpeed()));
            }

            if (this.body.getPosition().sub(currentAnchor.getPhysicsPos()).len2() < 1.0f) {
                if (currentAnchor.getDelay() > 0) {
                    this.stopped = true;
                    this.lastStop = System.nanoTime();
                    this.body.setLinearVelocity(0, 0);
                } else {
                    this.currentAnchorIndex = (this.currentAnchorIndex + 1) % this.config.getAnchorsCount();
                }
            }
        } else {
            long currentTime = System.nanoTime();
            if (currentTime - this.lastStop > this.getCurrentAnchor().getDelay() * 1000000) {
                this.currentAnchorIndex = (this.currentAnchorIndex + 1) % this.config.getAnchorsCount();
                this.stopped = false;
            }
        }


        // Shooting
        long currentTime = System.nanoTime();
        if (this.stopped && currentTime - this.lastShot > this.config.getAttackCooldownMs() * 1000000) {
//            this.bulletCount =
//                    Math.max(this.config.getAttackBulletCountMin(), this.bulletCount - 2) +
//                    random.nextInt(
//                            Math.max(this.config.getAttackBulletCountMin(), this.bulletCount - 2) +
//                                    Math.min(this.config.getAttackBulletCountMax(), this.bulletCount + 2)
//                    );
            this.bulletCount =
                    this.config.getAttackBulletCountMin() +
                    random.nextInt(this.config.getAttackBulletCountMin() + this.config.getAttackBulletCountMax());

            for (int j = 0; j < this.bulletCount; j++) {
                Bullet bullet = this.bulletPool.obtain();
                bullet.init(super.body.getPosition(), 360f / this.bulletCount * j);
                this.activeBullets.add(bullet);
                this.lastShot = currentTime;
            }
        }

        Bullet bullet;
        for (int i = this.activeBullets.size; --i >= 0;) {
            bullet = this.activeBullets.get(i);
            if (bullet.isAlive()) {
                bullet.update();
            } else {
                this.activeBullets.removeIndex(i);
                this.bulletPool.free(bullet);
            }
        }
    }

    @Override
    public void dispose() {
        this.bulletPool.clear();
        this.activeBullets.clear();
        super.dispose();
    }
}
