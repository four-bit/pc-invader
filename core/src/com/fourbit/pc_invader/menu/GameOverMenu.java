package com.fourbit.pc_invader.menu;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import com.fourbit.pc_invader.PcInvader;


public class GameOverMenu extends Menu {
    protected TextButton replayBtn;
    protected TextButton quitBtn;

    public GameOverMenu(String msg) {
        super("menu/GameOver.background.png");

        super.mainRoot.add(new Label(msg, this.skin)).row();
        this.replayBtn = new TextButton("PLAY AGAIN", this.skin);
        super.mainRoot.add(this.replayBtn).row();
        this.quitBtn = new TextButton("MAIN MENU", this.skin);
        super.mainRoot.add(this.quitBtn).row();

        this.replayBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PcInvader.diedMusic.stop();
                PcInvader.diedMusic.dispose();
                PcInvader.bossMusic.play();
                PcInvader.setState(PcInvader.GameState.BOSS_LEVEL);
            }
        });

        this.quitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PcInvader.bossMusic.stop();
                PcInvader.bossMusic.dispose();
                PcInvader.diedMusic.stop();
                PcInvader.diedMusic.dispose();
                PcInvader.bgMusic.play();
                PcInvader.setState(PcInvader.GameState.MAIN_MENU);
            }
        });
    }
}
