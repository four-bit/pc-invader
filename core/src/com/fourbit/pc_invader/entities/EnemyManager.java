package com.fourbit.pc_invader.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;
import com.fourbit.pc_invader.entities.Enemy;

public class EnemyManager {
    private Texture enemyTexture;
    private Random random;

    // An array to store the enemies currently on the screen
    private Enemy[] enemies;

    public EnemyManager(Texture enemyTexture) {
        this.enemyTexture = enemyTexture;
        this.random = new Random();

        // Initialize the array with a single enemy
        enemies = new Enemy[1];
        enemies[0] = new Enemy(random.nextInt(1920), random.nextInt(1080), enemyTexture);
    }

    public void update() {
        // Update the enemies
        for (Enemy enemy : enemies) {
            enemy.update();
        }

        // Remove any enemies that have been destroyed
        int numDestroyed = 0;
        for (int i = 0; i < enemies.length; i++) {
            if (enemies[i].isDestroyed()) {
                enemies[i] = null;
                numDestroyed++;
            }
        }

        // Shrink the array to remove the destroyed enemies
        if (numDestroyed > 0) {
            Enemy[] newEnemies = new Enemy[enemies.length - numDestroyed];
            int j = 0;
            for (int i = 0; i < enemies.length; i++) {
                if (enemies[i] != null) {
                    newEnemies[j++] = enemies[i];
                }
            }
            enemies = newEnemies;
        }

        // Add a new enemy if there are fewer than 3 on the screen
        if (enemies.length < 3) {
            addEnemy();
        }
    }

    public void draw(SpriteBatch batch) {
        // Draw all the enemies on the screen
        for (Enemy enemy : enemies) {
            enemy.draw(batch);
        }
    }

    private void addEnemy() {
        Enemy[] newEnemies = new Enemy[enemies.length + 1];
        System.arraycopy(enemies, 0, newEnemies, 0, enemies.length);
        newEnemies[enemies.length] = new Enemy(random.nextInt(800), random.nextInt(600), enemyTexture);
        enemies = newEnemies;
    }
}
