package com.fourbit.pc_invader.levels.boss;

import com.badlogic.gdx.graphics.g2d.Batch;

import com.fourbit.pc_invader.PcInvader;
import com.fourbit.pc_invader.entities.boss.Boss;
import com.fourbit.pc_invader.entities.player.Player;

import static com.fourbit.pc_invader.utils.Globals.GAME_HEIGHT;
import static com.fourbit.pc_invader.utils.Globals.GAME_WIDTH;


public class Level extends com.fourbit.pc_invader.levels.Level {
    protected Player player;
    protected Boss boss;
    private final LevelHud levelHud;

    public Level(boolean debug) {
        super("levels/boss/background.png", debug);

        this.boss = new Boss(
                super.physicsWorld,
                GAME_WIDTH * .7f,
                GAME_HEIGHT * .5f
        );
        this.player = new Player(
                super.physicsWorld,
                GAME_WIDTH * .25f, GAME_HEIGHT * .5f, 0.0f
        );

        this.levelHud = new LevelHud(this);
        new CollisionListener(this.physicsWorld, this);
    }


    @Override
    public void reset() {
        super.reset();
        this.player.reset();
        this.boss.reset();
    }

    @Override
    public void update() {
        super.update();
        this.player.update();
        this.boss.update();
        this.levelHud.update();
        if (this.boss.getHp() == 0) super.state = State.PLAYER_WON;
        if (this.player.getHp() == 0) super.state = State.PLAYER_LOST;
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
