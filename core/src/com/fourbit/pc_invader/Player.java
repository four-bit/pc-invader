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
    private int x, y, speed, healthPoints, shieldPoints;
    private boolean hasShield;
    private float angle;
    private Texture texture;
    private TextureAtlas exhaustTextureAtlas;
    private ParticleEffect exhaustEffect;


    Player() {
        texture = null;
        x = 0;
        y = 0;
        speed = 0;
        healthPoints = 0;
        shieldPoints = -1;
        hasShield = false;
        angle = 0.0f;
        initGraphics();
    }

    Player(
            int x, int y,
            int speed,
            int maxHealth,
            int maxShield, boolean hasShield,
            float angle
    ) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        healthPoints = maxHealth;
        shieldPoints = maxShield;
        this.hasShield = hasShield;
        this.angle = angle;
        initGraphics();
    }


    // Player logic
    public void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.A))
            x -= speed;
        if (Gdx.input.isKeyPressed(Input.Keys.D))
            x += speed;
        if (Gdx.input.isKeyPressed(Input.Keys.W))
            y += speed;
        if (Gdx.input.isKeyPressed(Input.Keys.S))
            y -= speed;

        if ((x - texture.getWidth() / 2) < 0) x = texture.getWidth() / 2;
        if ((x + texture.getWidth() / 2) > GAME_WIDTH) x = GAME_WIDTH - texture.getWidth() / 2;
        if ((y - texture.getHeight() / 2) < 0) y = texture.getHeight() / 2;
        if ((y + texture.getHeight() / 2) > GAME_HEIGHT) y = GAME_HEIGHT - texture.getHeight() / 2;

        exhaustEffect.setPosition(x, y);
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

        bearing3D.x = x;
        bearing3D.y = y;
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
    public int getX() { return x; }
    public int getY() { return y; }
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
