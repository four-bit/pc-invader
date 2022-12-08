package com.fourbit.pc_invader.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameHUD {
    private Skin skin;
    private Stage stage;

    public GameHUD() {
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        stage = new Stage(new ScreenViewport());
    }


    public void update() {

    }

    public void draw() {

    }

    public void dispose() {
        skin.dispose();
        stage.dispose();
    }
}
