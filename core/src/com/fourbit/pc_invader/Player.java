package com.fourbit.pc_invader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.sun.tools.javac.main.Option;


public class Player extends Entity {
    private final int maxSpeed;
    private int healthPoints;
    private int shieldPoints;
    private boolean hasShield;
    private Body body;
    private CircleShape collisionBox;
    private TextureAtlas exhaustTextureAtlas;
    private ParticleEffect exhaustEffect;


    Player(
            World world,
            int x, int y,
            int maxSpeed,
            int maxHealth,
            int maxShield, boolean hasShield,
            float angle
    ) {
        super("player/sprite.png");
        super.setPosition(x, y);

        this.maxSpeed = maxSpeed;
        this.healthPoints = maxHealth;
        this.shieldPoints = maxShield;
        this.hasShield = hasShield;
        super.setAngle(angle);

        initGraphics();
        initPhysics(world);
    }


    // Player logic
    public void update() {
        Vector2 vel = this.body.getLinearVelocity();
        Vector2 pos = this.body.getPosition();

        // Calculate movement vector based on user input and add that vector to player's position
        if (Gdx.input.isKeyPressed(Input.Keys.A) && vel.x > -maxSpeed)
            this.body.applyLinearImpulse(-8f, 0, pos.x, pos.y, true);
        if (Gdx.input.isKeyPressed(Input.Keys.D) && vel.x > maxSpeed)
            this.body.applyLinearImpulse(8f, 0, pos.x, pos.y, true);
        if (Gdx.input.isKeyPressed(Input.Keys.W) && vel.y < maxSpeed)
            this.body.applyLinearImpulse(0, 8f, pos.x, pos.y, true);
        if (Gdx.input.isKeyPressed(Input.Keys.S) && vel.y > -maxSpeed)
            this.body.applyLinearImpulse(0, -8f, pos.x, pos.y, true);

//        // Level boundary check
//        if ((position.x - (float) texture.getWidth() / 2) < 0) position.x = (float) texture.getWidth() / 2;
//        if ((position.x + (float) texture.getWidth() / 2) > GAME_WIDTH) position.x = GAME_WIDTH - (float) texture.getWidth() / 2;
//        if ((position.y - (float) texture.getHeight() / 2) < 0) position.y = (float) texture.getHeight() / 2;
//        if ((position.y + (float) texture.getHeight() / 2) > GAME_HEIGHT) position.y = GAME_HEIGHT - (float) texture.getHeight() / 2;

        exhaustEffect.setPosition(super.getPosition().x, super.getPosition().y);
        ParticleEmitter emitter = exhaustEffect.getEmitters().first();
        emitter.getAngle().setHigh(super.getAngle() - 180.0f);
        emitter.getAngle().setLow(super.getAngle() - 180.0f);

        super.setAngle(180 - getAngleVector().angleDeg());
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        exhaustEffect.draw(batch, Gdx.graphics.getDeltaTime());
    }

    public void dispose() {
        exhaustEffect.dispose();
        exhaustTextureAtlas.dispose();
        collisionBox.dispose();
    }


    // Utilities
    public Vector2 getBearing() {
        Vector2 bearing2D = new Vector2();
        Vector3 bearing3D = new Vector3();
        OrthographicCamera cam = new OrthographicCamera();

        bearing3D.x = super.getPosition().x;
        bearing3D.y = super.getPosition().y;
        bearing3D.z = 0;
        cam.unproject(bearing3D);
        bearing2D.x = bearing3D.x;
        bearing2D.y = bearing3D.y;

        return bearing2D;
    }

    public Vector2 getAngleVector() {
        return getBearing().sub(PcInvader.getMouseCoords());
    }

    public void initGraphics() {
        exhaustTextureAtlas = new TextureAtlas();
        exhaustTextureAtlas.addRegion("exhaust_particle", new TextureRegion(new Texture("player/exhaust_particle.png")));
        exhaustEffect = new ParticleEffect();
        exhaustEffect.load(Gdx.files.internal("player/exhaust.p"), exhaustTextureAtlas);
        exhaustEffect.start();
    }

    public void initPhysics(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(super.getPosition());
        body = world.createBody(bodyDef);
        collisionBox = new CircleShape();
        collisionBox.setRadius((float) (super.getHeight() + super.getWidth()) / 4);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = collisionBox;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;
        fixtureDef.isSensor = true;

        body.createFixture(fixtureDef);
        body.setUserData(this);
    }


    // Getter and setters

    public Body getBody() { return body; }
    public int getHealthPoints() { return healthPoints; }
    public int getShieldPoints() { return hasShield ? shieldPoints : -1; }
    public boolean hasShield() { return hasShield; }

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
}
