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

    private Vector2 Heading = new Vector2();
    private final Vector2 mouseInWorld2D = new Vector2();
    private final Vector3 mouseInWorld3D = new Vector3();
    private final OrthographicCamera cam  = new OrthographicCamera();

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

    public void initGraphics() {
        this.texture = new Texture("player/lvl3-default.png");
        this.exhaustTextureAtlas = new TextureAtlas();
        this.exhaustTextureAtlas.addRegion("exhaust_particle", new TextureRegion(new Texture("player/exhaust_particle.png")));
        this.exhaustEffect = new ParticleEffect();
        this.exhaustEffect.load(Gdx.files.internal("player/exhaust.p"), exhaustTextureAtlas);
        this.exhaustEffect.start();
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getSpeed() { return this.speed; }
    public int getHealthPoints() { return this.healthPoints; }
    public int getShieldPoints() { return this.hasShield ? this.shieldPoints : -1; }
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

    public void update() {
        // TODO: implement aiming base on cursor position

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

        mouseInWorld3D.x = Gdx.input.getX(); // Get mouse location
        mouseInWorld3D.y = Gdx.input.getY();
        mouseInWorld3D.z = 0;
        cam.unproject(mouseInWorld3D); // return x,y coordinate on the screen (read method documentation)
        mouseInWorld2D.x = mouseInWorld3D.x;
        mouseInWorld2D.y = mouseInWorld3D.y;
        this.angle = mouseInWorld2D.angleDeg(); // Set player angle to angle of vector
    }

    public void dispose() {
        this.texture.dispose();
        this.exhaustEffect.dispose();
        this.exhaustTextureAtlas.dispose();
    }
}
