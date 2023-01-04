package com.fourbit.pc_invader.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.fourbit.pc_invader.PcInvader;


public class MainMenu extends Menu {
    protected TextButton startBtn;
    protected TextButton quitBtn;

    public MainMenu() {
        super("menu/MainMenu.background.png");

        super.mainRoot.add(new Label("PC INVADER", this.skin)).row();
        this.startBtn = new TextButton("START", this.skin);
        super.mainRoot.add(this.startBtn).row();
        this.quitBtn = new TextButton("QUIT", this.skin);
        super.mainRoot.add(this.quitBtn).row();

        this.startBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PcInvader.bgMusic.stop();
                PcInvader.bgMusic.dispose();
                PcInvader.bossMusic.play();
                PcInvader.setState(PcInvader.GameState.BOSS_LEVEL);
            }
        });

        this.quitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        PcInvader.bgMusic.play();
    }
}
