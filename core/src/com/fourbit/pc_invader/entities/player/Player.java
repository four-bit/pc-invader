package com.fourbit.pc_invader.entities.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.fourbit.pc_invader.PcInvader;
import com.fourbit.pc_invader.entities.Entity;
import com.fourbit.pc_invader.utils.Utils;
import com.sun.tools.javac.main.Option;

import static com.fourbit.pc_invader.utils.Globals.GAME_HEIGHT;
import static com.fourbit.pc_invader.utils.Globals.GAME_WIDTH;
import static com.fourbit.pc_invader.utils.Globals.PPM;


public class Player extends Entity {
    private final float speed;
    private final Vector2 movement;

    private int healthPoints;
    private int shieldPoints;
    private boolean hasShield;

    private Body body;
    private CircleShape collisionBox;
    private TextureAtlas exhaustTextureAtlas;
    private ParticleEffect exhaustEffect;


    public Player(
            World world,
            int x, int y, float angle, float speed,
            int maxHealth, int maxShield, boolean hasShield
    ) {
        super("player/sprite.png");
        super.setPosition(x, y);
        super.setAngle(angle);

        this.speed = speed;
        this.movement = new Vector2();
        this.healthPoints = maxHealth;
        this.shieldPoints = maxShield;
        this.hasShield = hasShield;

        initGraphics();
        initPhysics(world);
    }


    // Player logic
    public void update() {
        // Calculate movement vector based on user input and add that vector to player's position
        if (Gdx.input.isKeyPressed(Input.Keys.A))
            movement.add(new Vector2(-speed, 0));
        if (Gdx.input.isKeyPressed(Input.Keys.D))
            movement.add(new Vector2(speed, 0));
        if (Gdx.input.isKeyPressed(Input.Keys.W))
            movement.add(new Vector2(0, speed));
        if (Gdx.input.isKeyPressed(Input.Keys.S))
            movement.add(new Vector2(0, -speed));
        this.body.setLinearVelocity(movement);

        // Level boundary check
        if ((this.body.getPosition().x - (float) super.getWidth() / 2) < 0) this.body.setLinearVelocity(speed, 0);
        if ((this.body.getPosition().x + (float) super.getWidth() / 2) > GAME_WIDTH) this.body.setLinearVelocity(-speed, 0);;
        if ((this.body.getPosition().y - (float) super.getHeight() / 2) < 0) this.body.setLinearVelocity(0, speed);;
        if ((this.body.getPosition().y + (float) super.getHeight() / 2) > GAME_HEIGHT) this.body.setLinearVelocity(0, -speed);;

        movement.setZero();

        exhaustEffect.setPosition(super.getPosition().x, super.getPosition().y);
        ParticleEmitter emitter = exhaustEffect.getEmitters().first();
        emitter.getAngle().setHigh(super.getAngle() - 180.0f);
        emitter.getAngle().setLow(super.getAngle() - 180.0f);

        super.setAngle(180 - getAngleVector().angleDeg());
    }

    @Override
    public void draw(Batch batch) {
        exhaustEffect.draw(batch, Gdx.graphics.getDeltaTime());
        super.draw(batch);
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
        bodyDef.position.set(Utils.toMeters(super.getPosition()));
        body = world.createBody(bodyDef);

        collisionBox = new CircleShape();
        collisionBox.setRadius((float) (super.getHeight() + super.getWidth()) / 4 / PPM);

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
    public float getSpeed() { return speed; }
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
