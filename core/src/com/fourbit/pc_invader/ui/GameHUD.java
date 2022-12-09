package com.fourbit.pc_invader.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.fourbit.pc_invader.PcInvader;
import com.fourbit.pc_invader.Player;

import java.util.HashMap;


public class GameHUD {
    private boolean debugMode;
    private Skin skin;
    private Stage main, debug;
    private Table mainRoot, debugRoot;
    private HashMap<String, Label> debugInfoLabels;


    public GameHUD(PcInvader game) {
        // Initialization
        debugMode = game.isDebug();


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
        if (debugMode) {
            debug = new Stage(new ScreenViewport());
            debugRoot = new Table();
            debugRoot.defaults().expandX().bottom().left();

            debugInfoLabels = new HashMap<>();
            debugInfoLabels.put("playerHealth", new Label("playerHealth", skin));
            debugInfoLabels.put("playerShield", new Label("playerShield", skin));
            debugInfoLabels.put("playerSpeed", new Label("playerSpeed", skin));
            debugInfoLabels.put("playerPosition", new Label("playerPosition", skin));
            debugInfoLabels.put("playerAngle", new Label("playerAngle", skin));
            debugInfoLabels.put("mousePosition", new Label("mousePosition", skin));

            for (Label label : debugInfoLabels.values()) {
                debugRoot.add(label);
                debugRoot.row();
            }

            debugRoot.setFillParent(true);
            debug.addActor(debugRoot);
        }
        // === End of Debug information ===
    }


    public void updateFrom(Player player) {
        Vector2 mousePosition =  PcInvader.getMouseCoords();

        if (debugMode) {
            debugInfoLabels.get("playerHealth").setText("[]Player health: [YELLOW]" + player.getHealthPoints());
            debugInfoLabels.get("playerShield").setText("[#ffffff]Player shield: [YELLOW]" + player.getShieldPoints() + (player.hasShield() ? "[GREEN] ENABLED" : "[RED] DISABLED"));
            debugInfoLabels.get("playerSpeed").setText("[]Player speed: [YELLOW]" + player.getSpeed());
            debugInfoLabels.get("playerPosition").setText("[]Player position: X:[YELLOW]" + player.getX() + " []Y:[YELLOW]" + player.getY());
            debugInfoLabels.get("playerAngle").setText("[]Player angle: [YELLOW]" + player.getAngle());
            debugInfoLabels.get("mousePosition").setText("[]Mouse position: X:[YELLOW]" + mousePosition.x + " []Y:[YELLOW]" + mousePosition.y);
        }
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
        if (debugMode) debug.dispose();
    }
}
