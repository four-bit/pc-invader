package com.fourbit.pc_invader.entities.boss;


import com.badlogic.gdx.utils.JsonValue;
import com.fourbit.pc_invader.entities.EntityConfig;
import com.fourbit.pc_invader.utils.Anchor;

import java.util.ArrayList;


public class BossConfig extends EntityConfig {
    private final int mainHp, segmentHp, contactDmg, bulletDmg, attackBulletCountMin, attackBulletCountMax;
    private final float bulletSpeed;
    private final long attackCooldownMs;
    private final ArrayList<Anchor> anchors;


    public BossConfig() {
        super("entities/boss/boss.json");
        this.mainHp = super.map.getInt("mainHp");
        this.segmentHp = super.map.getInt("segmentHp");
        this.contactDmg = super.map.getInt("contactDmg");
        this.bulletSpeed = super.map.getFloat("bulletSpeed");
        this.bulletDmg = super.map.getInt("bulletDmg");
        this.attackBulletCountMin = super.map.getInt("attackBulletCountMin");
        this.attackBulletCountMax = super.map.getInt("attackBulletCountMax");
        this.attackCooldownMs = super.map.getLong("attackCooldownMs");

        this.anchors = new ArrayList<>();
        JsonValue anchor = super.map.getChild("anchors");
        for (; anchor != null; anchor = anchor.next()) {
            this.anchors.add(new Anchor(
                    anchor.getFloat("x"),
                    anchor.getFloat("y"),
                    anchor.getFloat("homingSpeed"),
                    anchor.getLong("delayMs")
            ));
        }
    }


    public int getMainHp() {
        return this.mainHp;
    }

    public int getSegmentHp() {
        return this.segmentHp;
    }

    public int getContactDmg() {
        return this.contactDmg;
    }

    public float getBulletSpeed() {
        return this.bulletSpeed;
    }

    public int getBulletDmg() {
        return this.bulletDmg;
    }

    public int getAttackBulletCountMin() {
        return this.attackBulletCountMin;
    }

    public int getAttackBulletCountMax() {
        return this.attackBulletCountMax;
    }

    public long getAttackCooldownMs() {
        return this.attackCooldownMs;
    }

    public Anchor getAnchor(int index) {
        return this.anchors.get(index);
    }

    public int getAnchorsCount() {
        return this.anchors.size();
    }
}
