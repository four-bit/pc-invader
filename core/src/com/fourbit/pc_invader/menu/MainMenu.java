package com.fourbit.pc_invader.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.fourbit.pc_invader.PcInvader;
import com.fourbit.pc_invader.utils.GameComponent;
import com.fourbit.pc_invader.utils.ui.UIComponent;

import static com.fourbit.pc_invader.utils.Globals.GAME_HEIGHT;
import static com.fourbit.pc_invader.utils.Globals.GAME_WIDTH;

public class MainMenu implements GameComponent, UIComponent, Disposable {
    protected Texture background;
    protected final Skin skin;
    protected Stage mainStage;
    protected Table mainRoot;
    protected TextButton startBtn;
    protected TextButton quitBtn;

    public MainMenu(String backgroundPath) {
        this.background = new Texture(backgroundPath);

        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        this.skin.get(Label.LabelStyle.class).font.getData().markupEnabled = true;

        this.mainStage = new Stage(new ScreenViewport());
        this.mainRoot = new Table();
        this.mainRoot.setFillParent(true);
        this.mainRoot.defaults().center();

        this.mainRoot.add(new Label("PC INVADER", this.skin)).row();
        this.startBtn = new TextButton("START", this.skin);
        this.mainRoot.add(this.startBtn).row();
        this.quitBtn = new TextButton("QUIT", this.skin);
        this.mainRoot.add(this.quitBtn).row();

        this.startBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PcInvader.setState(PcInvader.GameState.BOSS_LEVEL);
            }
        });

        this.quitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        this.mainStage.addActor(mainRoot);
        Gdx.input.setInputProcessor(this.mainStage);
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
    public void draw(Batch batch) {
        float backgroundAspectRatio = (float) this.background.getWidth() / this.background.getHeight();
        float screenAspectRatio = (float) GAME_WIDTH / GAME_HEIGHT;
        batch.draw(
                this.background,
                0,
                0,
                0,
                0,
                this.background.getWidth(),
                this.background.getHeight(),
                (float) (screenAspectRatio <= backgroundAspectRatio ? GAME_HEIGHT / this.background.getHeight() : GAME_WIDTH / this.background.getWidth()),
                (float) (screenAspectRatio <= backgroundAspectRatio ? GAME_HEIGHT / this.background.getHeight() : GAME_WIDTH / this.background.getWidth()),
                0,
                0,
                0,
                this.background.getWidth(),
                this.background.getHeight(),
                false,
                false
        );
        this.draw();
    }

    @Override
    public void dispose() {
        this.background.dispose();
        this.skin.dispose();
        this.mainStage.dispose();
    }
}
