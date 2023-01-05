package com.fourbit.pc_invader.entities.boss;


import com.badlogic.gdx.utils.JsonValue;
import com.fourbit.pc_invader.entities.EntityConfig;
import com.fourbit.pc_invader.utils.Anchor;

import java.util.ArrayList;


public class BossConfig extends EntityConfig {
    private final int mainHp, segmentHp, attackBulletCountMin, attackBulletCountMax;
    private final float bulletSpeed;
    private final long attackCooldownMs;
    private final ArrayList<Anchor> anchors;


    public BossConfig() {
        super("entities/boss/boss.json");
        this.mainHp = super.map.getInt("mainHp");
        this.segmentHp = super.map.getInt("segmentHp");
        this.bulletSpeed = super.map.getFloat("bulletSpeed");
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
        return mainHp;
    }

    public int getSegmentHp() {
        return segmentHp;
    }

    public float getBulletSpeed() {
        return bulletSpeed;
    }

    public int getAttackBulletCountMin() {
        return attackBulletCountMin;
    }

    public int getAttackBulletCountMax() {
        return attackBulletCountMax;
    }

    public long getAttackCooldownMs() {
        return attackCooldownMs;
    }

    public Anchor getAnchor(int index) {
        return anchors.get(index);
    }

    public int getAnchorsCount() {
        return anchors.size();
    }
}
