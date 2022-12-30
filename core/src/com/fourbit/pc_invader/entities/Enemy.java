package com.fourbit.pc_invader.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

class Enemy {
    // The enemy's hitbox
    Rectangle hitbox;

    // The enemy's texture
    Texture texture;

    // The enemy's health
    int health;

    public Enemy(int x, int y, Texture texture) {
        this.hitbox = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
        this.texture = texture;
        this.health = 100;
    }

    public void update() {
        // Update the enemy's hitbox position
        hitbox.x--;

        // Decrease the enemy's health over time
        health--;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, hitbox.x, hitbox.y);
    }

    public boolean isDestroyed() {
        return health <= 0;
    }
}

