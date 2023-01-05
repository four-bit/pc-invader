package com.fourbit.pc_invader.levels.boss;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.fourbit.pc_invader.entities.player.BulletBar;
import com.fourbit.pc_invader.entities.player.HealthBar;
import com.fourbit.pc_invader.utils.Globals;
import com.fourbit.pc_invader.utils.InputProcessor;

import static com.fourbit.pc_invader.utils.Globals.GAME_HEIGHT;
import static com.fourbit.pc_invader.utils.Globals.GAME_WIDTH;


public class LevelHud extends com.fourbit.pc_invader.utils.ui.LevelHud {
    private final Level level;
    private final HealthBar playerHealthBar;
    private final BulletBar playerBulletBar;
    private final com.fourbit.pc_invader.entities.boss.HealthBar bossHealthBar;

    public LevelHud(Level level) {
        super(level);
        this.level = level;
        this.playerHealthBar = new HealthBar(0, GAME_HEIGHT, this.level.player, super.mainStage, 2);
        this.playerBulletBar = new BulletBar(32, GAME_HEIGHT - 54, this.level.player, super.mainStage, 1);
        this.bossHealthBar = new com.fourbit.pc_invader.entities.boss.HealthBar(0, 0, GAME_WIDTH, this.level.boss, this.mainStage);

        if (super.debug) {
            super.debugInfoLabels.put("gamePAS", new Label("gamePAS", skin));
            super.debugInfoLabels.put("gamePPM", new Label("gamePPM", skin));
            super.debugInfoLabels.put("levelState", new Label("levelState", skin));
            super.debugInfoLabels.put("playerHealth", new Label("playerHealth", skin));
            super.debugInfoLabels.put("playerSpeed", new Label("playerSpeed", skin));
            super.debugInfoLabels.put("playerAmmo", new Label("playerAmmo", skin));
            super.debugInfoLabels.put("playerPosition", new Label("playerPosition", skin));
            super.debugInfoLabels.put("playerAngle", new Label("playerAngle", skin));
            super.debugInfoLabels.put("mousePosition", new Label("mousePosition", skin));
            super.debugInfoLabels.put("mouseVector", new Label("mouseVector", skin));
            super.debugInfoLabels.put("bossPosition", new Label("bossPosition", skin));
            super.debugInfoLabels.put("bossInitPhase", new Label("bossInitPhase", skin));
            super.debugInfoLabels.put("bossAnchor", new Label("bossAnchor", skin));
            super.debugInfoLabels.put("bossHealth", new Label("bossHealth", skin));

            for (Label label : this.debugInfoLabels.values()) {
                debugZone.add(label);
                debugZone.row();
            }
        }
    }


    @Override
    public void update() {
        this.playerHealthBar.update();
        this.playerBulletBar.update();
        this.bossHealthBar.update();

        if (super.debug) {
            Vector2 mouseVector = InputProcessor.getMouseVector();

            this.debugInfoLabels.get("gamePAS").setText("[]Game PAS: [YELLOW]" + Globals.PAS);
            this.debugInfoLabels.get("gamePPM").setText("[]Game PPM: [YELLOW]" + Globals.PPM);
            this.debugInfoLabels.get("levelState").setText("[]Level state: [YELLOW]" + this.level.getState().toString());
            this.debugInfoLabels.get("playerHealth").setText("[]Player health: [YELLOW]" + this.level.player.getHp());
            this.debugInfoLabels.get("playerSpeed").setText("[]Player speed: [YELLOW]" + this.level.player.getSpeed());
            this.debugInfoLabels.get("playerAmmo").setText("[]Player ammo: [YELLOW]" + this.level.player.getAmmo());
            this.debugInfoLabels.get("playerPosition").setText("[]Player position: X:[YELLOW]" + Math.round(this.level.player.getPosition().x) + " []Y:[YELLOW]" + Math.round(this.level.player.getPosition().y));
            this.debugInfoLabels.get("playerAngle").setText("[]Player angle: [YELLOW]" + this.level.player.getAngleDegree());
            this.debugInfoLabels.get("mousePosition").setText("[]Mouse position: X:[YELLOW]" + Gdx.input.getX() + " []Y:[YELLOW]" + Gdx.input.getY());
            this.debugInfoLabels.get("mouseVector").setText("[]Mouse vector: X:[YELLOW]" + mouseVector.x + " []Y:[YELLOW]" + mouseVector.y);
            this.debugInfoLabels.get("bossPosition").setText("[]Boss position: X:[YELLOW]" + Math.round(this.level.boss.getPosition().x) + " []Y:[YELLOW]" + Math.round(this.level.boss.getPosition().y));
            this.debugInfoLabels.get("bossInitPhase").setText("[]Boss init phase:" + ((this.level.boss.isInitPhase()) ? "[RED] YES" : "[GREEN] NO"));
            this.debugInfoLabels.get("bossHealth").setText("[]Boss health: [YELLOW]" + this.level.boss.getHp());
            this.debugInfoLabels.get("bossAnchor")
                    .setText(
                            "[]Boss anchor: X:[YELLOW]" + Math.round(this.level.boss.getMain().getCurrentAnchor().getScreenPos().x) +
                                    " []Y:[YELLOW]" + Math.round(this.level.boss.getMain().getCurrentAnchor().getScreenPos().y) +
                                    " []Homing speed:[YELLOW]" + this.level.boss.getMain().getCurrentAnchor().getHomingSpeed() +
                                    " []Delay:[YELLOW]" + this.level.boss.getMain().getCurrentAnchor().getDelay()
                    );
        }
    }

    @Override
    public void dispose() {
        this.playerHealthBar.dispose();
        this.playerBulletBar.dispose();
        this.bossHealthBar.dispose();
        super.dispose();
    }
}
