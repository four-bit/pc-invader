package com.fourbit.pc_invader.entities.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.FixtureDef;
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
    private final Body body;
    private final CircleShape collisionBox;
    private final TextureAtlas exhaustTextureAtlas;
    private final ParticleEffect exhaustEffect;

    private int healthPoints;
    private int shieldPoints;
    private boolean hasShield;


    public Player(
            World world,
            int x, int y, float angle, float speed,
            int maxHealth, int maxShield, boolean hasShield
    ) {
        super("player/sprite.png");
        super.position.x = x;
        super.position.y = y;
        super.angle = angle;

        this.speed = speed;
        this.movement = new Vector2();
        this.healthPoints = maxHealth;
        this.shieldPoints = maxShield;
        this.hasShield = hasShield;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(Utils.toMeters(super.position));
        this.body = world.createBody(bodyDef);

        this.collisionBox = new CircleShape();
        this.collisionBox.setRadius((float) (super.texture.getHeight() + super.texture.getWidth()) / 4 / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = collisionBox;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;
        fixtureDef.isSensor = true;

        this.body.createFixture(fixtureDef);
        this.body.setUserData(this);

        exhaustTextureAtlas = new TextureAtlas();
        exhaustTextureAtlas.addRegion("exhaust_particle", new TextureRegion(new Texture("player/exhaust_particle.png")));
        exhaustEffect = new ParticleEffect();
        exhaustEffect.load(Gdx.files.internal("player/exhaust.p"), exhaustTextureAtlas);
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
        if ((Utils.toPixels(this.body.getPosition().x) - (float) super.texture.getWidth() / 2) < 0) this.body.setLinearVelocity(speed, 0);
        if ((Utils.toPixels(this.body.getPosition().x) + (float) super.texture.getWidth() / 2) > GAME_WIDTH) this.body.setLinearVelocity(-speed, 0);;
        if ((Utils.toPixels(this.body.getPosition().y) - (float) super.texture.getHeight() / 2) < 0) this.body.setLinearVelocity(0, speed);;
        if ((Utils.toPixels(this.body.getPosition().y) + (float) super.texture.getHeight() / 2) > GAME_HEIGHT) this.body.setLinearVelocity(0, -speed);;

        movement.setZero();

        exhaustEffect.setPosition(super.position.x, super.position.y);
        ParticleEmitter emitter = exhaustEffect.getEmitters().first();
        emitter.getAngle().setHigh(super.angle - 180.0f);
        emitter.getAngle().setLow(super.angle - 180.0f);

        super.angle = 180 - getAngleVector().angleDeg();
    }

    @Override
    public void draw(Batch batch) {
        exhaustEffect.draw(batch, Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }

    @Override
    public void dispose() {
        exhaustEffect.dispose();
        exhaustTextureAtlas.dispose();
        collisionBox.dispose();
        super.dispose();
    }


    // Utilities
    public Vector2 getBearing() {
        Vector2 bearing2D = new Vector2();
        Vector3 bearing3D = new Vector3();
        OrthographicCamera cam = new OrthographicCamera();

        bearing3D.x = super.position.x;
        bearing3D.y = super.position.y;
        bearing3D.z = 0;
        cam.unproject(bearing3D);
        bearing2D.x = bearing3D.x;
        bearing2D.y = bearing3D.y;

        return bearing2D;
    }

    public Vector2 getAngleVector() {
        return getBearing().sub(PcInvader.getMouseCoords());
    }
}
