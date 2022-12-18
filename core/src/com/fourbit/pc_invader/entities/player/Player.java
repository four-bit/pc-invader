package com.fourbit.pc_invader.entities.player;

import com.sun.tools.javac.main.Option;

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

import com.fourbit.pc_invader.entities.Sprite;
import com.fourbit.pc_invader.utils.BodyEditorLoader;
import com.fourbit.pc_invader.utils.Utils;


import static com.fourbit.pc_invader.utils.Globals.GAME_HEIGHT;
import static com.fourbit.pc_invader.utils.Globals.GAME_WIDTH;


public class Player extends Sprite {
    private final float speed;
    private final Vector2 movement;
    private final Body body;
    private final TextureAtlas exhaustTextureAtlas;
    private final ParticleEffect exhaustEffect;

    private int healthPoints;
    private int shieldPoints;
    private boolean hasShield;


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

        exhaustTextureAtlas = new TextureAtlas();
        exhaustTextureAtlas.addRegion("exhaust_particle", new TextureRegion(new Texture("entities/player/exhaust_particle.png")));
        exhaustEffect = new ParticleEffect();
        exhaustEffect.load(Gdx.files.internal("entities/player/exhaust.p"), exhaustTextureAtlas);
        exhaustEffect.start();
    }


    public float getSpeed() {
        return speed;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public int getShieldPoints() {
        return hasShield ? shieldPoints : -1;
    }

    public boolean hasShield() {
        return hasShield;
    }

    public void setHealthPoints(int val) throws Option.InvalidValueException {
        if (val >= 0) {
            healthPoints = val;
        } else {
            throw new Option.InvalidValueException("val cannot be negative.");
        }
    }

    public void setShieldPoints(int val) throws Option.InvalidValueException {
        if (val >= 0) {
            if (!hasShield) {
                hasShield = true;
            }
            shieldPoints = val;
        } else {
            throw new Option.InvalidValueException("val cannot be negative.");
        }
    }

    public void disableShield() {
        hasShield = false;
        shieldPoints = -1;
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
        this.body.setTransform(body.getPosition().add(Utils.toMeters(movement)), (float) Math.PI - Utils.getAngleToMouse(this).angleRad());

        // Level boundary checks
        if ((Utils.toPixels(this.body.getPosition().x) - (float) super.texture.getWidth() / 2) < 0) {  // LEFT
            this.body.setTransform(Utils.toMeters(super.texture.getWidth() / 2), body.getPosition().y, this.body.getAngle());
        }
        if ((Utils.toPixels(this.body.getPosition().x) + (float) super.texture.getWidth() / 2) > GAME_WIDTH) {  // RIGHT
            this.body.setTransform(Utils.toMeters(GAME_WIDTH - super.texture.getWidth() / 2), body.getPosition().y, this.body.getAngle());
        }
        if ((Utils.toPixels(this.body.getPosition().y) + (float) super.texture.getHeight() / 2) > GAME_HEIGHT) {  // TOP
            this.body.setTransform(body.getPosition().x, Utils.toMeters(GAME_HEIGHT - super.texture.getHeight() / 2), this.body.getAngle());
        }
        if ((Utils.toPixels(this.body.getPosition().y) - (float) super.texture.getHeight() / 2) < 0) {  // BOTTOM
            this.body.setTransform(body.getPosition().x, Utils.toMeters(super.texture.getHeight() / 2), this.body.getAngle());
        }

        // Exhaust effect positioning
        this.exhaustEffect.setPosition(super.position.x, super.position.y);
        ParticleEmitter emitter = this.exhaustEffect.getEmitters().first();
        emitter.getAngle().setHigh(super.angle - 180.0f);
        emitter.getAngle().setLow(super.angle - 180.0f);

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
        super.dispose();
    }
}
