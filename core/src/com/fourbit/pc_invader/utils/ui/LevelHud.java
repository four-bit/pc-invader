package com.fourbit.pc_invader.utils.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.fourbit.pc_invader.levels.Level;

import java.util.LinkedHashMap;


public class LevelHud implements UIComponent, Disposable {
    protected final boolean debug;
    protected final Skin skin;
    protected Stage mainStage, debugStage;
    protected Table mainRoot, debugRoot, debugZone;
    protected LinkedHashMap<String, Label> debugInfoLabels;


    public LevelHud(Level level) {
        // Initialization
        this.debug = level.isDebug();


        // === HUD Skin configuration ===
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        this.skin.get(Label.LabelStyle.class).font.getData().markupEnabled = true;


        // === Main HUD ===
        this.mainStage = new Stage(new ScreenViewport());
        this.mainRoot = new Table();
        this.mainRoot.setFillParent(true);
        this.mainStage.addActor(mainRoot);
        // === End of Main HUD ===

        // Debug information
        if (this.debug) {
            this.debugStage = new Stage(new ScreenViewport());
            this.debugRoot = new Table();

            this.debugZone = new Table();
            this.debugZone.defaults().bottom().left();
            this.debugRoot.add(this.debugZone).expand().bottom().left();

            this.debugInfoLabels = new LinkedHashMap<>();

            this.debugRoot.setFillParent(true);
            this.debugStage.addActor(debugRoot);
        }
        // === End of Debug information ===
    }


    @Override
    public void update() {

    }

    @Override
    public void draw() {
        this.mainStage.act();
        this.mainStage.draw();
        if (this.debug) {
            this.debugStage.act();
            this.debugStage.draw();
        }
    }

    @Override
    public void dispose() {
        this.skin.dispose();
        this.mainStage.dispose();
        if (this.debug) this.debugStage.dispose();
    }
}
