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

import java.util.LinkedHashMap;


public class GameHUD {
    private boolean debugMode;
    private Skin skin;
    private Stage main, debug;
    private Table mainRoot, debugRoot;
    private LinkedHashMap<String, Label> debugInfoLabels;


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

            Table debugZone = new Table();
            debugZone.defaults().bottom().left();
            debugRoot.add(debugZone).expand().bottom().left();

            debugInfoLabels = new LinkedHashMap<>();
            debugInfoLabels.put("playerHealth", new Label("playerHealth", skin));
            debugInfoLabels.put("playerShield", new Label("playerShield", skin));
            debugInfoLabels.put("playerVelocity", new Label("playerVelocity", skin));
            debugInfoLabels.put("playerPosition", new Label("playerPosition", skin));
            debugInfoLabels.put("playerBearing", new Label("playerBearing", skin));
            debugInfoLabels.put("playerAngle", new Label("playerAngle", skin));
            debugInfoLabels.put("playerAngleVector", new Label("playerAngleVector", skin));
            debugInfoLabels.put("mousePosition", new Label("mousePosition", skin));
            debugInfoLabels.put("mouseVector", new Label("mouseVector", skin));

            for (Label label : debugInfoLabels.values()) {
                debugZone.add(label);
                debugZone.row();
            }

            debugRoot.setFillParent(true);
            debug.addActor(debugRoot);
        }
        // === End of Debug information ===
    }


    public void updateFrom(Player player) {
        Vector2 mouseVector = PcInvader.getMouseCoords();
        Vector2 playerBearing = player.getBearing();
        Vector2 playerAngleVector = player.getAngleVector();

        if (debugMode) {
            debugInfoLabels.get("playerHealth").setText("[]Player health: [YELLOW]" + player.getHealthPoints());
            debugInfoLabels.get("playerShield").setText("[#ffffff]Player shield: [YELLOW]" + player.getShieldPoints() + (player.hasShield() ? "[GREEN] ENABLED" : "[RED] DISABLED"));
            debugInfoLabels.get("playerVelocity").setText("[]Player velocity: X:[YELLOW]" + player.getBody().getLinearVelocity().x + " []Y:[YELLOW]" + player.getBody().getLinearVelocity().y);
            debugInfoLabels.get("playerPosition").setText("[]Player position: X:[YELLOW]" + player.getX() + " []Y:[YELLOW]" + player.getY());
            debugInfoLabels.get("playerBearing").setText("[]Player bearing: X:[YELLOW]" + playerBearing.x + " []Y:[YELLOW]" + playerBearing.y);
            debugInfoLabels.get("playerAngle").setText("[]Player angle: [YELLOW]" + player.getAngle());
            debugInfoLabels.get("playerAngleVector").setText("[]Player angle vector: X:[YELLOW]" + playerAngleVector.x + " []Y:[YELLOW]" + playerAngleVector.y);
            debugInfoLabels.get("mousePosition").setText("[]Mouse position: X:[YELLOW]" + Gdx.input.getX() + " []Y:[YELLOW]" + Gdx.input.getY());
            debugInfoLabels.get("mouseVector").setText("[]Mouse vector: X:[YELLOW]" + mouseVector.x + " []Y:[YELLOW]" + mouseVector.y);
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
