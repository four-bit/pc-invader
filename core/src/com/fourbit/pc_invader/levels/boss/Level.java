package com.fourbit.pc_invader.levels.boss;

import com.badlogic.gdx.graphics.g2d.Batch;

import com.fourbit.pc_invader.entities.Bullet;
import com.fourbit.pc_invader.entities.boss.Boss;
import com.fourbit.pc_invader.entities.player.Player;
import com.fourbit.pc_invader.utils.CollisionListener;

import static com.fourbit.pc_invader.utils.Globals.GAME_HEIGHT;
import static com.fourbit.pc_invader.utils.Globals.GAME_WIDTH;


public class Level extends com.fourbit.pc_invader.levels.Level {
    protected final Player player;
    protected final Boss boss;
    private final LevelHud levelHud;
    private final CollisionListener collisionListener;
    public Level(boolean debug) {
        super("levels/boss/background.png", debug);

        this.boss = new Boss(
                super.physicsWorld,
                GAME_WIDTH * .7f,
                GAME_HEIGHT * .5f,
                50
        );
        this.player = new Player(
                super.physicsWorld,
                GAME_WIDTH * .25f, GAME_HEIGHT * .5f, 0.0f, 8
        );
        this.collisionListener = new CollisionListener(this.player, this.boss, this.physicsWorld);
        super.physicsWorld.setContactListener(collisionListener);
        this.levelHud = new LevelHud(this);
    }


    @Override
    public void update() {
        super.update();
        this.player.update();
        this.boss.update();
        this.levelHud.update();

    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        this.levelHud.draw();
    }

    @Override
    public void dispose() {
        this.player.dispose();
        this.boss.dispose();
        this.levelHud.dispose();
        super.dispose();
    }
}
