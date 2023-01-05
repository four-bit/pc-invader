package com.fourbit.pc_invader.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.fourbit.pc_invader.utils.GameComponent;
import com.fourbit.pc_invader.utils.ui.UIComponent;

import static com.fourbit.pc_invader.utils.Globals.GAME_HEIGHT;
import static com.fourbit.pc_invader.utils.Globals.GAME_WIDTH;

public class Menu implements GameComponent, UIComponent, Disposable {
    protected final Skin skin;
    protected Texture background;
    protected Stage mainStage;
    protected Table mainRoot;

    public Menu(String backgroundPath) {
        this.background = new Texture(backgroundPath);

        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        this.skin.get(Label.LabelStyle.class).font.getData().markupEnabled = true;

        this.mainStage = new Stage(new ScreenViewport());
        this.mainRoot = new Table();
        this.mainRoot.setFillParent(true);
        this.mainRoot.defaults().center();

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
    }

    @Override
    public void dispose() {
        this.background.dispose();
        this.skin.dispose();
        this.mainStage.dispose();
    }
}
