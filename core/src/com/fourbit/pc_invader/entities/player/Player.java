package com.fourbit.pc_invader.entities.player;


import com.badlogic.gdx.physics.box2d.BodyDef;
import com.fourbit.pc_invader.PcInvader;
import com.fourbit.pc_invader.utils.Resettable;

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
import static com.fourbit.pc_invader.utils.Globals.PAS;

import com.sun.tools.javac.main.Option;


public class Player extends PhysicsEntity implements Resettable {
    private final Vector2 initPos;
    private final TextureAtlas exhaustTextureAtlas;
    private final ParticleEffect exhaustEffect;
    private final Array<Bullet> activeBullets;
    private Pool<Bullet> bulletPool;
    private int hp;
    private int ammo;
    private float lastShot, lastAmmoRegen;

    private final PlayerConfig config;


    public Player(
            World world,
            float x, float y, float angle
    ) {
        super(world, BodyDef.BodyType.DynamicBody, "entities/player/sprite.png", x, y, angle, 0.0f);
        this.initPos = new Vector2(x, y);

        new BodyEditorLoader(Gdx.files.internal("entities/player/body.json")).attachFixture(super.body, "body", super.fixtureDef, Utils.toMeters(super.getWidth()));
        super.body.createFixture(fixtureDef);
        super.fixtureDef.density = 1.0f;
        super.fixtureDef.friction = 0.0f;
        super.fixtureDef.restitution = 0.0f;

        this.exhaustTextureAtlas = new TextureAtlas();
        this.exhaustTextureAtlas.addRegion("exhaust_particle", new TextureRegion(new Texture("entities/player/exhaust_particle.png")));
        this.exhaustEffect = new ParticleEffect();
        this.exhaustEffect.load(Gdx.files.internal("entities/player/exhaust.p"), exhaustTextureAtlas);
        ParticleEmitter emitter = this.exhaustEffect.getEmitters().first();
        emitter.getXScale().setHigh(PAS);
        emitter.getXScale().setLow(PAS);
        this.exhaustEffect.start();

        this.config = new PlayerConfig();
        super.speed = this.config.getSpeed();
        this.hp = this.config.getHealth();
        this.ammo = this.config.getAmmo();

        this.activeBullets = new Array<>();

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

    public Array<Bullet> getActiveBullets() {
        return this.activeBullets;
    }

    public Pool<Bullet> getBulletPool() {
        return this.bulletPool;
    }


    @Override
    public void reset() {
        this.hp = this.config.getHealth();
        this.ammo = this.config.getAmmo();
        this.lastShot = System.nanoTime();
        this.lastAmmoRegen = System.nanoTime();
        this.body.setTransform(Utils.toMeters(this.initPos), 0);
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
                (float) (Utils.getAngleToMouse(this).angleRad() + Math.PI) // Aiming
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
                PcInvader.shootSound.play(0.5f);

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
//        this.bulletPool.freeAll(this.activeBullets);
        super.dispose();
    }
}
