package com.fourbit.pc_invader.levels;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.fourbit.pc_invader.entities.boss.Boss;
import com.fourbit.pc_invader.entities.player.Player;
import com.fourbit.pc_invader.ui.GameHUD;
import com.fourbit.pc_invader.utils.Utils;

import static com.fourbit.pc_invader.utils.Globals.GAME_HEIGHT;
import static com.fourbit.pc_invader.utils.Globals.GAME_WIDTH;


public class BossLevel extends Level {
    private final Player player;
    private final Boss boss;
    private final GameHUD gameHud;
    private final PolygonShape levelBoundTop, levelBoundBottom, levelBoundLeft, levelBoundRight;


    public BossLevel(boolean debug) {
        super("levels/level-boss.bg.png", debug);

        // Create our body definition
        BodyDef levellevelBoundBodyDef = new BodyDef();
        levellevelBoundBodyDef.type = BodyDef.BodyType.StaticBody;
        levellevelBoundBodyDef.position.set(
                new Vector2(
                        Utils.toMeters((int) super.viewportCamera.viewportWidth / 2),
                        Utils.toMeters((int) super.viewportCamera.viewportHeight / 2))
        );

        Body groundBody = super.physicsWorld.createBody(levellevelBoundBodyDef);

        this.levelBoundTop = new PolygonShape();
        this.levelBoundBottom = new PolygonShape();
        this.levelBoundLeft = new PolygonShape();
        this.levelBoundRight = new PolygonShape();

        this.levelBoundTop.setAsBox(
                Utils.toMeters((int) super.viewportCamera.viewportWidth / 2),
                Utils.toMeters(1),
                Utils.toMeters(new Vector2(0, viewportCamera.viewportHeight / 2)),
                0
        );
        this.levelBoundBottom.setAsBox(
                Utils.toMeters((int) super.viewportCamera.viewportWidth / 2),
                Utils.toMeters(1),
                Utils.toMeters(new Vector2(0, -viewportCamera.viewportHeight / 2)),
                0
        );
        this.levelBoundLeft.setAsBox(
                Utils.toMeters(1),
                Utils.toMeters((int) super.viewportCamera.viewportHeight / 2),
                Utils.toMeters(new Vector2(-viewportCamera.viewportWidth / 2, 0)),
                0
        );
        this.levelBoundRight.setAsBox(
                Utils.toMeters(1),
                Utils.toMeters((int) super.viewportCamera.viewportHeight / 2),
                Utils.toMeters(new Vector2(viewportCamera.viewportWidth / 2, 0)),
                0
        );

        groundBody.createFixture(this.levelBoundTop, 0.0f);
        groundBody.createFixture(this.levelBoundBottom, 0.0f);
        groundBody.createFixture(this.levelBoundLeft, 0.0f);
        groundBody.createFixture(this.levelBoundRight, 0.0f);

        this.player = new Player(
                super.physicsWorld,
                GAME_WIDTH / 4, GAME_HEIGHT / 2, 0.0f, 15,
                8, 3, true
        );
        this.boss = new Boss(
                super.physicsWorld,
                GAME_WIDTH / 2,
                GAME_HEIGHT / 2,
                2
        );

        this.gameHud = new GameHUD(this);
    }


    @Override
    public void update() {
        super.update();
        this.player.update();
        this.boss.update();
        this.gameHud.updateFrom(player);
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        this.boss.draw(batch);
        this.player.draw(batch);
        this.gameHud.draw();
    }

    @Override
    public void dispose() {
        this.player.dispose();
        this.boss.dispose();
        this.gameHud.dispose();
        this.levelBoundTop.dispose();
        this.levelBoundBottom.dispose();
        this.levelBoundLeft.dispose();
        this.levelBoundRight.dispose();
        super.dispose();
    }
}
