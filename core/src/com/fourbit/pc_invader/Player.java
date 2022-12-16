package com.fourbit.pc_invader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.sun.tools.javac.main.Option;
import static com.fourbit.pc_invader.PcInvader.GAME_WIDTH;
import static com.fourbit.pc_invader.PcInvader.GAME_HEIGHT;


public class Player extends BodyDef {
    private final int speed;
    private int ammo;
    private int healthPoints;
    private int shieldPoints;
    private boolean hasShield;
    private float angle;
    private final Vector2 movement;
    private Body body;
    private Texture texture;
    private TextureAtlas exhaustTextureAtlas;
    private ParticleEffect exhaustEffect;


    Player(
            World world,
            int x, int y,
            int speed,
            int max_ammo,
            int maxHealth,
            int maxShield, boolean hasShield,
            float angle
    ) {
        super();
        body = world.createBody(this);
        super.type = BodyType.DynamicBody;

        position.set(x, y);
        movement = new Vector2(0, 0);

        this.speed = speed;
        ammo = max_ammo;
        healthPoints = maxHealth;
        shieldPoints = maxShield;
        this.hasShield = hasShield;
        this.angle = angle;

        initGraphics();
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
        position.add(movement);

        // Level boundary check
        if ((position.x - (float) texture.getWidth() / 2) < 0) position.x = (float) texture.getWidth() / 2;
        if ((position.x + (float) texture.getWidth() / 2) > GAME_WIDTH) position.x = GAME_WIDTH - (float) texture.getWidth() / 2;
        if ((position.y - (float) texture.getHeight() / 2) < 0) position.y = (float) texture.getHeight() / 2;
        if ((position.y + (float) texture.getHeight() / 2) > GAME_HEIGHT) position.y = GAME_HEIGHT - (float) texture.getHeight() / 2;

        movement.setZero();

        exhaustEffect.setPosition(position.x, position.y);
        ParticleEmitter emitter = exhaustEffect.getEmitters().first();
        emitter.getAngle().setHigh(angle - 180.0f);
        emitter.getAngle().setLow(angle - 180.0f);

        angle = 180 - getAngleVector().angleDeg();

        // Shooting
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            ammo--;
        }
    }

    public void draw(Batch batch) {
        exhaustEffect.draw(batch, Gdx.graphics.getDeltaTime());
        batch.draw(
                texture,
                getX() - (float) getWidth() / 2,
                getY() - (float) getHeight() / 2,
                (float) getWidth() / 2,
                (float) getHeight() / 2,
                getWidth(),
                getHeight(),
                1.0f,
                1.0f,
                getAngle(),
                0,
                0,
                getWidth(),
                getHeight(),
                false,
                false
        );
    }

    public void dispose() {
        texture.dispose();
        exhaustEffect.dispose();
        exhaustTextureAtlas.dispose();
    }


    // Utilities
    public Vector2 getBearing() {
        Vector2 bearing2D = new Vector2();
        Vector3 bearing3D = new Vector3();
        OrthographicCamera cam = new OrthographicCamera();

        bearing3D.x = position.x;
        bearing3D.y = position.y;
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
        texture = new Texture("player/sprite.png");

        exhaustTextureAtlas = new TextureAtlas();
        exhaustTextureAtlas.addRegion("exhaust_particle", new TextureRegion(new Texture("player/exhaust_particle.png")));
        exhaustEffect = new ParticleEffect();
        exhaustEffect.load(Gdx.files.internal("player/exhaust.p"), exhaustTextureAtlas);
        exhaustEffect.start();
    }


    // Getter and setters
    public int getX() { return (int) position.x; }
    public int getY() { return (int) position.y; }
    public int getSpeed() { return speed; }
    public int getAmmo() { return ammo; }
    public int getHealthPoints() { return healthPoints; }
    public int getShieldPoints() { return hasShield ? shieldPoints : -1; }
    public boolean hasShield() { return hasShield; }
    public float getAngle() { return angle; }
    public ParticleEffect getExhaustEffect() { return exhaustEffect; }
    public int getWidth() { return texture.getWidth(); }
    public int getHeight() { return texture.getHeight(); }

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
