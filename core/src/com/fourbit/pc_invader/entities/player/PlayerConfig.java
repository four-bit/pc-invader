package com.fourbit.pc_invader.entities.player;


import com.fourbit.pc_invader.entities.EntityConfig;


public class PlayerConfig extends EntityConfig {
    private final int health;
    private final float speed;
    private final float bulletSpeed;
    private final int bulletDmg, ammo;
    private final long attackCooldownMs;
    private final long ammoRegenCooldownMs;


    PlayerConfig() {
        super("entities/player/player.json");

        this.health = super.map.getInt("health");
        this.speed = super.map.getFloat("speed");
        this.bulletSpeed = super.map.getFloat("bulletSpeed");
        this.bulletDmg = super.map.getInt("bulletDmg");
        this.ammo = super.map.getInt("ammo");
        this.attackCooldownMs = super.map.getLong("attackCooldownMs");
        this.ammoRegenCooldownMs = super.map.getLong("ammoRegenCooldownMs");
    }


    public int getHealth() {
        return this.health;
    }

    public float getSpeed() {
        return this.speed;
    }

    public float getBulletSpeed() {
        return this.bulletSpeed;
    }

    public int getBulletDmg() {
        return this.bulletDmg;
    }

    public int getAmmo() {
        return this.ammo;
    }

    public long getAttackCooldownMs() {
        return this.attackCooldownMs;
    }

    public long getAmmoRegenCooldownMs() {
        return this.ammoRegenCooldownMs;
    }
}
