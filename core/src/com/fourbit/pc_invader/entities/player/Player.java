package com.fourbit.pc_invader.entities.player;


import com.badlogic.gdx.physics.box2d.BodyDef;
import com.sun.tools.javac.main.Option;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import com.fourbit.pc_invader.entities.PhysicsEntity;
import com.fourbit.pc_invader.utils.InputProcessor;
import com.fourbit.pc_invader.utils.BodyEditorLoader;
import com.fourbit.pc_invader.utils.Utils;

import static com.fourbit.pc_invader.utils.Globals.GAME_HEIGHT;
import static com.fourbit.pc_invader.utils.Globals.GAME_WIDTH;


public class Player extends PhysicsEntity {
    private final TextureAtlas exhaustTextureAtlas;
    private final ParticleEffect exhaustEffect;
    private final Array<Bullet> activeBullets;
    private final Pool<Bullet> bulletPool;
    private int hp;
    private int ammo;
    private float lastShot, lastAmmoRegen;

    private final PlayerConfig config;


    public Player(
            World world,
            float x, float y, float angle,
            int maxHealth
    ) {
        super(world, BodyDef.BodyType.KinematicBody, "entities/player/sprite.png", x, y, angle, 0.0f);

        new BodyEditorLoader(Gdx.files.internal("entities/player/body.json")).attachFixture(super.body, "body", super.fixtureDef, Utils.toMeters(super.getWidth()));
        super.body.createFixture(fixtureDef);
        super.fixtureDef.density = 1.0f;
        super.fixtureDef.friction = 0.0f;
        super.fixtureDef.restitution = 0.0f;

        this.exhaustTextureAtlas = new TextureAtlas();
        this.exhaustTextureAtlas.addRegion("exhaust_particle", new TextureRegion(new Texture("entities/player/exhaust_particle.png")));
        this.exhaustEffect = new ParticleEffect();
        this.exhaustEffect.load(Gdx.files.internal("entities/player/exhaust.p"), exhaustTextureAtlas);
        this.exhaustEffect.start();

        this.config = new PlayerConfig();
        super.speed = this.config.getSpeed();
        this.hp = maxHealth;
        this.ammo = this.config.getAmmo();

        this.activeBullets = new Array<>();
        final float bulletSpeed = this.config.getBulletSpeed();
        this.bulletPool = new Pool<Bullet>() {
            @Override
            protected Bullet newObject() {
                return new Bullet(body.getWorld(), bulletSpeed);
            }
        };
        this.lastShot = System.nanoTime();
        this.lastAmmoRegen = System.nanoTime();
    }


    public int getAmmo() {
        return this.ammo;
    }

    public int getHp() {
        return this.hp;
    }

    public void setHp(int val) throws Option.InvalidValueException {
        if (val >= 0) {
            this.hp = val;
        } else {
            throw new Option.InvalidValueException("val cannot be negative.");
        }
    }


    @Override
    public void update() {
        super.update();

        // Calculate movement vector based on user input and add that vector to player's position
        super.movement.setZero();
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            super.movement.add(new Vector2(-super.speed, 0));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            super.movement.add(new Vector2(super.speed, 0));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            super.movement.add(new Vector2(0, super.speed));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            super.movement.add(new Vector2(0, -super.speed));
        }
        super.body.setTransform(
                super.body.getPosition().add(super.movement),
                (float) Math.PI - Utils.getAngleToMouse(this).angleRad()  // Aiming
        );
        super.position.set(Utils.toPixels(super.body.getPosition()));


        // Level boundary checks
        if (Utils.isOutOfScreen(this, Utils.OrthographicDirection.UP)) {
            super.body.setTransform(
                    body.getPosition().x,
                    Utils.toMeters(GAME_HEIGHT - super.getHeight() * 0.5f),
                    super.body.getAngle()
            );
        }
        if (Utils.isOutOfScreen(this, Utils.OrthographicDirection.DOWN)) {
            super.body.setTransform(
                    body.getPosition().x,
                    Utils.toMeters(super.getHeight() * 0.5f),
                    super.body.getAngle()
            );
        }
        if (Utils.isOutOfScreen(this, Utils.OrthographicDirection.LEFT)) {
            super.body.setTransform(
                    Utils.toMeters(super.getWidth() * 0.5f),
                    body.getPosition().y,
                    super.body.getAngle());
        }
        if (Utils.isOutOfScreen(this, Utils.OrthographicDirection.RIGHT)) {
            super.body.setTransform(
                    Utils.toMeters(GAME_WIDTH - super.getWidth() * 0.5f),
                    body.getPosition().y,
                    super.body.getAngle());
        }


        // Exhaust effect positioning
        this.exhaustEffect.setPosition(super.position.x, super.position.y);
        ParticleEmitter emitter = this.exhaustEffect.getEmitters().first();
        emitter.getAngle().setHigh(super.angle - 180.0f);
        emitter.getAngle().setLow(super.angle - 180.0f);


        // Shooting
        long currentTime = System.nanoTime();
        if (InputProcessor.isShoot() && this.ammo > 0) {
            if (currentTime - this.lastShot > this.config.getAttackCooldownMs() * 1000000) {
                Bullet bullet = this.bulletPool.obtain();
                bullet.init(super.body.getPosition(), super.angle);
                this.activeBullets.add(bullet);
                this.lastShot = currentTime;
                this.ammo--;
            }
        } else {  // Ammo regeneration
            if (
                    currentTime - lastShot > this.config.getAmmoRegenCooldownMs() * 1000000 &&
                    currentTime - lastAmmoRegen > this.config.getAmmoRegenCooldownMs() * 1000000 &&
                    this.ammo < this.config.getAmmo()
            ) {
                this.ammo++;
                this.lastAmmoRegen = currentTime;
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
    public void draw(Batch batch) {
        this.exhaustEffect.draw(batch, Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }

    @Override
    public void dispose() {
        this.exhaustEffect.dispose();
        this.exhaustTextureAtlas.dispose();
        this.bulletPool.freeAll(this.activeBullets);
        super.dispose();
    }
}
