package com.fourbit.pc_invader.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.fourbit.pc_invader.PcInvader;
import com.fourbit.pc_invader.utils.GameComponent;
import com.fourbit.pc_invader.utils.ui.UIComponent;


public class GameOverMenu implements GameComponent, UIComponent, Disposable {
    protected final Skin skin;
    protected Stage mainStage;
    protected Table mainRoot;
    protected TextButton replayBtn;
    protected TextButton quitBtn;

    public GameOverMenu(String msg) {

        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        this.skin.get(Label.LabelStyle.class).font.getData().markupEnabled = true;

        this.mainStage = new Stage(new ScreenViewport());
        this.mainRoot = new Table();
        this.mainRoot.setFillParent(true);
        this.mainRoot.defaults().center();

        this.mainRoot.add(new Label(msg, this.skin)).row();
        this.replayBtn = new TextButton("PLAY AGAIN", this.skin);
        this.mainRoot.add(this.replayBtn).row();
        this.quitBtn = new TextButton("MAIN MENU", this.skin);
        this.mainRoot.add(this.quitBtn).row();

        this.replayBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PcInvader.setState(PcInvader.GameState.BOSS_LEVEL);
            }
        });

        this.quitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PcInvader.setState(PcInvader.GameState.MAIN_MENU);
            }
        });

        this.mainStage.addActor(mainRoot);
    }


    public Stage getMainStage() {
        return mainStage;
    }


    @Override
    public void update() {
        this.mainStage.act();
    }

    @Override
    public void draw() {
        this.mainStage.draw();
    }

    @Override
    public void draw(Batch batch) {}

    @Override
    public void dispose() {
        this.skin.dispose();
        this.mainStage.dispose();
    }
}
