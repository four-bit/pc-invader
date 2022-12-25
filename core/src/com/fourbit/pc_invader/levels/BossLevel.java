package com.fourbit.pc_invader.levels;

import com.badlogic.gdx.graphics.g2d.Batch;

import com.fourbit.pc_invader.entities.boss.Boss;
import com.fourbit.pc_invader.entities.player.Player;
import com.fourbit.pc_invader.ui.GameHUD;

import static com.fourbit.pc_invader.utils.Globals.GAME_HEIGHT;
import static com.fourbit.pc_invader.utils.Globals.GAME_WIDTH;


public class BossLevel extends Level {
    private final Player player;
    private final Boss boss;
    private final GameHUD gameHud;


    public BossLevel(boolean debug) {
        super("levels/level-boss.bg.png", debug);

        this.boss = new Boss(
                super.physicsWorld,
                GAME_WIDTH * 0.5f,
                GAME_HEIGHT * 0.5f,
                2.0f
        );
        this.player = new Player(
                super.physicsWorld,
                GAME_WIDTH * 0.25f, GAME_HEIGHT * 0.5f, 0.0f, 15,
                8, 3, true
        );

        this.gameHud = new GameHUD(this);
    }


    @Override
    public void update() {
        super.update();
        this.player.update();
        this.boss.update();
        this.gameHud.updateFrom(player);
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        this.gameHud.draw();
    }

    @Override
    public void dispose() {
        this.player.dispose();
        this.boss.dispose();
        this.gameHud.dispose();
        super.dispose();
    }
}
