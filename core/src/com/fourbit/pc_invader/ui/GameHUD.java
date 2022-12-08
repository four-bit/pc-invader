package com.fourbit.pc_invader.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.fourbit.pc_invader.PcInvader;
import com.fourbit.pc_invader.Player;


public class GameHUD {
    private boolean debugMode;
    private Skin skin;
    private Stage main, debug;
    private Table mainRoot, debugRoot;

    public GameHUD(PcInvader game, Player player) {
        // === HUD Skin configuration ===
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        skin.get(Label.LabelStyle.class).font.getData().markupEnabled = true;


        // === Main HUD ===
        main = new Stage(new ScreenViewport());
        mainRoot = new Table();
        mainRoot.setFillParent(true);
        main.addActor(mainRoot);
        // === End of Main HUD ===

        // Debug information
        debug = new Stage(new ScreenViewport());
        debugRoot = new Table();

        Label dbInfoPlayerHealth = new Label("[#ffffff]Player health: [YELLOW]" + player.getHealthPoints(), skin);
        debugRoot.add(dbInfoPlayerHealth).expandX().bottom().left();
        debugRoot.row();

        Label dbInfoPlayerShield = new Label(
                "[#ffffff]Player shield: [YELLOW]" + player.getShieldPoints() + (player.hasShield() ? "[GREEN] ENABLED" : "[RED] DISABLED"),
                skin
        );
        debugRoot.add(dbInfoPlayerShield).expandX().bottom().left();
        debugRoot.row();

        Label dbInfoPlayerSpeed = new Label("[#ffffff]Player speed: [YELLOW]" + player.getSpeed(), skin);
        debugRoot.add(dbInfoPlayerSpeed).expandX().bottom().left();
        debugRoot.row();

        debugRoot.setFillParent(true);
        debug.addActor(debugRoot);
        // === End of Debug information ===


        // Other
        debugMode = game.isDebug();
    }


    public void update() {

    }

    public void draw() {
        main.act();
        main.draw();
        if (debugMode) {
            debug.act();
            debug.draw();
        }
    }

    public void dispose() {
        skin.dispose();
        main.dispose();
        debug.dispose();
    }
}
