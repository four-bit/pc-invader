package com.fourbit.pc_invader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.sun.tools.javac.main.Option;
import static com.fourbit.pc_invader.PcInvader.GAME_WIDTH;
import static com.fourbit.pc_invader.PcInvader.GAME_HEIGHT;


public class Player {
    private final int speed;
    private int healthPoints;
    private int shieldPoints;
    private boolean hasShield;
    private float angle;
    private final Vector2 position, movement;
    private Texture texture;
    private TextureAtlas exhaustTextureAtlas;
    private ParticleEffect exhaustEffect;


    Player(
            int x, int y,
            int speed,
            int maxHealth,
            int maxShield, boolean hasShield,
            float angle
    ) {
        position = new Vector2(x, y);
        movement = new Vector2(0, 0);
        this.speed = speed;
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
        texture = new Texture("player/level-3.png");

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
    public int getHealthPoints() { return healthPoints; }
    public int getShieldPoints() { return hasShield ? shieldPoints : -1; }
    public boolean hasShield() { return hasShield; }
    public float getAngle() { return angle; }
    public Texture getTexture() { return texture; }
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
