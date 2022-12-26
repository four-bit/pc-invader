package com.fourbit.pc_invader.levels.boss;

import com.badlogic.gdx.graphics.g2d.Batch;

import com.fourbit.pc_invader.entities.boss.Boss;
import com.fourbit.pc_invader.entities.player.Player;

import static com.fourbit.pc_invader.utils.Globals.GAME_HEIGHT;
import static com.fourbit.pc_invader.utils.Globals.GAME_WIDTH;


public class Level extends com.fourbit.pc_invader.levels.Level {
    protected final Player player;
    protected final Boss boss;
    private final LevelHud levelHud;


    public Level(boolean debug) {
        super("levels/boss/background.png", debug);

        this.boss = new Boss(
                super.physicsWorld,
                GAME_WIDTH * .75f,
                GAME_HEIGHT * .5f
        );
        this.player = new Player(
                super.physicsWorld,
                GAME_WIDTH * .25f, GAME_HEIGHT * .5f, 0.0f, .35f,
                8, 3, true
        );

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
