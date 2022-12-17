package com.fourbit.pc_invader.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.fourbit.pc_invader.entities.player.Player;
import com.fourbit.pc_invader.levels.Level;
import com.fourbit.pc_invader.utils.InputProcessor;

import java.util.LinkedHashMap;


public class GameHUD {
    private final boolean debug;
    private final Skin skin;
    protected Stage mainStage, debugStage;
    protected Table mainRoot, debugRoot;
    private LinkedHashMap<String, Label> debugInfoLabels;


    public GameHUD(Level level) {
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

            Table debugZone = new Table();
            debugZone.defaults().bottom().left();
            this.debugRoot.add(debugZone).expand().bottom().left();

            this.debugInfoLabels = new LinkedHashMap<>();
            this.debugInfoLabels.put("playerHealth", new Label("playerHealth", skin));
            this.debugInfoLabels.put("playerShield", new Label("playerShield", skin));
            this.debugInfoLabels.put("playerSpeed", new Label("playerSpeed", skin));
            this.debugInfoLabels.put("playerPosition", new Label("playerPosition", skin));
            this.debugInfoLabels.put("playerBearing", new Label("playerBearing", skin));
            this.debugInfoLabels.put("playerAngle", new Label("playerAngle", skin));
            this.debugInfoLabels.put("playerAngleVector", new Label("playerAngleVector", skin));
            this.debugInfoLabels.put("mousePosition", new Label("mousePosition", skin));
            this.debugInfoLabels.put("mouseVector", new Label("mouseVector", skin));

            for (Label label : this.debugInfoLabels.values()) {
                debugZone.add(label);
                debugZone.row();
            }

            this.debugRoot.setFillParent(true);
            this.debugStage.addActor(debugRoot);
        }
        // === End of Debug information ===
    }


    public void updateFrom(Player player) {
        Vector2 mouseVector = InputProcessor.getMouseCoords();
        Vector2 playerBearing = player.getBearing();
        Vector2 playerAngleVector = player.getAngleVector();

        if (this.debug) {
            this.debugInfoLabels.get("playerHealth").setText("[]Player health: [YELLOW]" + player.getHealthPoints());
            this.debugInfoLabels.get("playerShield").setText("[#ffffff]Player shield: [YELLOW]" + player.getShieldPoints() + (player.hasShield() ? "[GREEN] ENABLED" : "[RED] DISABLED"));
            this.debugInfoLabels.get("playerSpeed").setText("[]Player speed: [YELLOW]" + player.getSpeed());
            this.debugInfoLabels.get("playerPosition").setText("[]Player position: X:[YELLOW]" + player.getPosition().x + " []Y:[YELLOW]" + player.getPosition().y);
            this.debugInfoLabels.get("playerBearing").setText("[]Player bearing: X:[YELLOW]" + playerBearing.x + " []Y:[YELLOW]" + playerBearing.y);
            this.debugInfoLabels.get("playerAngle").setText("[]Player angle: [YELLOW]" + player.getAngle());
            this.debugInfoLabels.get("playerAngleVector").setText("[]Player angle vector: X:[YELLOW]" + playerAngleVector.x + " []Y:[YELLOW]" + playerAngleVector.y);
            this.debugInfoLabels.get("mousePosition").setText("[]Mouse position: X:[YELLOW]" + Gdx.input.getX() + " []Y:[YELLOW]" + Gdx.input.getY());
            this.debugInfoLabels.get("mouseVector").setText("[]Mouse vector: X:[YELLOW]" + mouseVector.x + " []Y:[YELLOW]" + mouseVector.y);
        }
    }

    public void draw() {
        this.mainStage.act();
        this.mainStage.draw();
        if (this.debug) {
            this.debugStage.act();
            this.debugStage.draw();
        }
    }

    public void dispose() {
        this.skin.dispose();
        this.mainStage.dispose();
        if (this.debug) this.debugStage.dispose();
    }
}
