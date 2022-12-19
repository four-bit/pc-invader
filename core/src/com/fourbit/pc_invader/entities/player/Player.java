package com.fourbit.pc_invader.entities.player;

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
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import com.fourbit.pc_invader.utils.InputProcessor;
import com.fourbit.pc_invader.entities.Entity;
import com.fourbit.pc_invader.utils.BodyEditorLoader;
import com.fourbit.pc_invader.utils.Utils;

import static com.fourbit.pc_invader.utils.Globals.GAME_HEIGHT;
import static com.fourbit.pc_invader.utils.Globals.GAME_WIDTH;
import static com.fourbit.pc_invader.utils.Globals.PLAYER_MAX_AMMO;
import static com.fourbit.pc_invader.utils.Globals.PLAYER_SHOOT_COOLDOWN_MS;
import static com.fourbit.pc_invader.utils.Globals.PLAYER_AMMO_REGEN_COOLDOWN_MS;


public class Player extends Entity {
    private final float speed;
    private final Vector2 movement;
    private final Body body;
    private final TextureAtlas exhaustTextureAtlas;
    private final ParticleEffect exhaustEffect;

    private int healthPoints;
    private int shieldPoints;
    private boolean hasShield;

    private final Array<Bullet> activeBullets = new Array<>();
    private final Pool<Bullet> bulletPool = new Pool<Bullet>() {
        @Override
        protected Bullet newObject() {
            return new Bullet(body.getWorld());
        }
    };
    private int ammo;
    private float lastShot, lastAmmoRegen;


    public Player(
            World world,
            float x, float y, float angle, float speed,
            int maxHealth, int maxShield, boolean hasShield
    ) {
        super("entities/player/sprite.png");
        super.position.x = x;
        super.position.y = y;

        this.speed = speed;
        this.movement = new Vector2();
        this.healthPoints = maxHealth;
        this.shieldPoints = maxShield;
        this.hasShield = hasShield;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(Utils.toMeters(super.position));
        this.body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        new BodyEditorLoader(Gdx.files.internal("entities/player/body.json")).attachFixture(this.body, "body", fixtureDef, Utils.toMeters(this.texture.getWidth()));

        this.body.createFixture(fixtureDef);
        this.body.setUserData(this);

        this.exhaustTextureAtlas = new TextureAtlas();
        this.exhaustTextureAtlas.addRegion("exhaust_particle", new TextureRegion(new Texture("entities/player/exhaust_particle.png")));
        this.exhaustEffect = new ParticleEffect();
        this.exhaustEffect.load(Gdx.files.internal("entities/player/exhaust.p"), exhaustTextureAtlas);
        this.exhaustEffect.start();

        this.lastShot = System.nanoTime();
        this.lastAmmoRegen = System.nanoTime();
        this.ammo = PLAYER_MAX_AMMO;
    }


    public float getSpeed() {
        return this.speed;
    }

    public int getAmmo() {
        return this.ammo;
    }

    public int getHealthPoints() {
        return this.healthPoints;
    }

    public int getShieldPoints() {
        return this.hasShield ? this.shieldPoints : -1;
    }

    public boolean hasShield() {
        return this.hasShield;
    }

    public void setHealthPoints(int val) throws Option.InvalidValueException {
        if (val >= 0) {
            this.healthPoints = val;
        } else {
            throw new Option.InvalidValueException("val cannot be negative.");
        }
    }

    public void setShieldPoints(int val) throws Option.InvalidValueException {
        if (val >= 0) {
            if (!this.hasShield) {
                this.hasShield = true;
            }
            this.shieldPoints = val;
        } else {
            throw new Option.InvalidValueException("val cannot be negative.");
        }
    }

    public void disableShield() {
        this.hasShield = false;
        this.shieldPoints = -1;
    }


    @Override
    public void update() {
        super.update();


        // Calculate movement vector based on user input and add that vector to player's position
        this.movement.setZero();
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.movement.add(new Vector2(-this.speed, 0));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.movement.add(new Vector2(this.speed, 0));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            this.movement.add(new Vector2(0, this.speed));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            this.movement.add(new Vector2(0, -this.speed));
        }
        this.body.setTransform(this.body.getPosition().add(Utils.toMeters(movement)), (float) Math.PI - Utils.getAngleToMouse(this).angleRad());
        this.position.set(Utils.toPixels(this.body.getPosition()));

        // Level boundary checks
        if (Utils.isOutOfScreen(this, Utils.OrthographicDirection.UP)) {
            this.body.setTransform(
                    body.getPosition().x,
                    Utils.toMeters(GAME_HEIGHT - (float) super.texture.getHeight() / 2),
                    this.body.getAngle()
            );
        }
        if (Utils.isOutOfScreen(this, Utils.OrthographicDirection.DOWN)) {
            this.body.setTransform(
                    body.getPosition().x,
                    Utils.toMeters((float) super.texture.getHeight() / 2),
                    this.body.getAngle()
            );
        }
        if (Utils.isOutOfScreen(this, Utils.OrthographicDirection.LEFT)) {
            this.body.setTransform(
                    Utils.toMeters((float) super.texture.getWidth() / 2),
                    body.getPosition().y,
                    this.body.getAngle());
        }
        if (Utils.isOutOfScreen(this, Utils.OrthographicDirection.RIGHT)) {
            this.body.setTransform(
                    Utils.toMeters(GAME_WIDTH - (float) super.texture.getWidth() / 2),
                    body.getPosition().y,
                    this.body.getAngle());
        }


        // Exhaust effect positioning
        this.exhaustEffect.setPosition(super.position.x, super.position.y);
        ParticleEmitter emitter = this.exhaustEffect.getEmitters().first();
        emitter.getAngle().setHigh(super.angle - 180.0f);
        emitter.getAngle().setLow(super.angle - 180.0f);


        // Shooting
        long currentTime = System.nanoTime();
        if (InputProcessor.isShoot() && this.ammo > 0) {
            if (currentTime - this.lastShot > PLAYER_SHOOT_COOLDOWN_MS * 1000000) {
                Bullet bullet = this.bulletPool.obtain();
                bullet.init(this.body.getPosition(), super.angle);
                this.activeBullets.add(bullet);
                this.lastShot = currentTime;
                this.ammo--;
            }
        } else {  // Ammo regeneration
            if (
                    currentTime - lastShot > PLAYER_AMMO_REGEN_COOLDOWN_MS * 1000000 &&
                    currentTime - lastAmmoRegen > PLAYER_AMMO_REGEN_COOLDOWN_MS * 1000000 &&
                    this.ammo < PLAYER_MAX_AMMO
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
