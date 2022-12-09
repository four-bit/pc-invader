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
        this.texture = null;
        this.x = 0;
        this.y = 0;
        this.speed = 0;
        this.healthPoints = 0;
        this.shieldPoints = -1;
        this.hasShield = false;
        this.angle = 0.0f;
        this.initGraphics();
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
        this.healthPoints = maxHealth;
        this.shieldPoints = maxShield;
        this.hasShield = hasShield;
        this.angle = angle;
        this.initGraphics();
    }


    // Player logic
    public void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.A))
            this.x -= this.speed;
        if (Gdx.input.isKeyPressed(Input.Keys.D))
            this.x += this.speed;
        if (Gdx.input.isKeyPressed(Input.Keys.W))
            this.y += this.speed;
        if (Gdx.input.isKeyPressed(Input.Keys.S))
            this.y -= this.speed;

        if ((this.x - texture.getWidth() / 2) < 0) this.x = texture.getWidth() / 2;
        if ((this.x + texture.getWidth() / 2) > GAME_WIDTH) this.x = GAME_WIDTH - texture.getWidth() / 2;
        if ((this.y - texture.getHeight() / 2) < 0) this.y = texture.getHeight() / 2;
        if ((this.y + texture.getHeight() / 2) > GAME_HEIGHT) this.y = GAME_HEIGHT - texture.getHeight() / 2;

        exhaustEffect.setPosition(this.x, this.y);
        ParticleEmitter emitter = exhaustEffect.getEmitters().first();
        emitter.getAngle().setHigh(this.angle - 180.0f);
        emitter.getAngle().setLow(this.angle - 180.0f);

        this.angle = 180 - this.getAngleVector().angleDeg();
    }

    public void dispose() {
        this.texture.dispose();
        this.exhaustEffect.dispose();
        this.exhaustTextureAtlas.dispose();
    }


    // Utilities
    public Vector2 getBearing() {
        Vector2 bearing2D = new Vector2();
        Vector3 bearing3D = new Vector3();
        OrthographicCamera cam = new OrthographicCamera();

        bearing3D.x = this.x;
        bearing3D.y = this.y;
        bearing3D.z = 0;
        cam.unproject(bearing3D);
        bearing2D.x = bearing3D.x;
        bearing2D.y = bearing3D.y;

        return bearing2D;
    }

    public Vector2 getAngleVector() {
        return this.getBearing().sub(PcInvader.getMouseCoords());
    }

    public void initGraphics() {
        this.texture = new Texture("player/lvl3-default.png");
        this.exhaustTextureAtlas = new TextureAtlas();
        this.exhaustTextureAtlas.addRegion("exhaust_particle", new TextureRegion(new Texture("player/exhaust_particle.png")));
        this.exhaustEffect = new ParticleEffect();
        this.exhaustEffect.load(Gdx.files.internal("player/exhaust.p"), exhaustTextureAtlas);
        this.exhaustEffect.start();
    }


    // Getter and setters
    public int getX() { return x; }
    public int getY() { return y; }
    public int getSpeed() { return this.speed; }
    public int getHealthPoints() { return this.healthPoints; }
    public int getShieldPoints() { return this.hasShield ? this.shieldPoints : -1; }
    public boolean hasShield() { return this.hasShield; }
    public float getAngle() { return this.angle; }
    public Texture getTexture() { return this.texture; }
    public ParticleEffect getExhaustEffect() { return exhaustEffect; }
    public int getWidth() { return this.texture.getWidth(); }
    public int getHeight() { return this.texture.getHeight(); }

    public void setX(int val) { this.x = val; }
    public void setY(int val) { this.y = val; }
    public void setSpeed(int val) { this.speed = val; }
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
    public void setAngle(float val) { this.angle = val; }
    public void disableShield() {
        this.hasShield = false;
        this.shieldPoints = -1;
    }
}
