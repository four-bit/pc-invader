package com.fourbit.pc_invader.entities.player;


import com.fourbit.pc_invader.entities.EntityConfig;


public class PlayerConfig extends EntityConfig {
    private final float speed;
    private final float bulletSpeed;
    private final int ammo;
    private final long attackCooldownMs;
    private final long AmmoRegenCooldownMs;


    PlayerConfig() {
        super("entities/player/player.json");

        this.speed = super.map.getFloat("speed");
        this.bulletSpeed = super.map.getFloat("bulletSpeed");
        this.ammo = super.map.getInt("ammo");
        this.attackCooldownMs = super.map.getLong("attackCooldownMs");
        this.AmmoRegenCooldownMs = super.map.getLong("ammoRegenCooldownMs");
    }


    public float getSpeed() {
        return speed;
    }

    public float getBulletSpeed() {
        return bulletSpeed;
    }

    public int getAmmo() {
        return ammo;
    }

    public long getAttackCooldownMs() {
        return attackCooldownMs;
    }

    public long getAmmoRegenCooldownMs() {
        return AmmoRegenCooldownMs;
    }
}
