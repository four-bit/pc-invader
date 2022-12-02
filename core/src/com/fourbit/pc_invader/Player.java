package com.fourbit.pc_invader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.sun.tools.javac.main.Option;
import static com.fourbit.pc_invader.PcInvader.GAME_WIDTH;
import static com.fourbit.pc_invader.PcInvader.GAME_HEIGHT;


public class Player {
    private int x, y, speed, healthPoints, shieldPoints;
    private boolean hasShield;
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
        this.exhaustTextureAtlas = new TextureAtlas();
        this.exhaustTextureAtlas.addRegion("exhaust_particle", new TextureRegion(new Texture("player/exhaust_particle.png")));
        this.exhaustEffect = new ParticleEffect();
        this.exhaustEffect.load(Gdx.files.internal("player/exhaust.p"), exhaustTextureAtlas);
        this.exhaustEffect.start();
    }

    Player(
            int x, int y,
            int speed,
            int maxHealth,
            int maxShield, boolean hasShield
    ) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.healthPoints = maxHealth;
        this.shieldPoints = maxShield;
        this.hasShield = hasShield;
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

    public void disableShield() {
        this.hasShield = false;
        this.shieldPoints = -1;
    }

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
    }

    public void dispose() {
        this.texture.dispose();
        this.exhaustEffect.dispose();
        this.exhaustTextureAtlas.dispose();
    }
}
