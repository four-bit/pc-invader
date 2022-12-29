package com.fourbit.pc_invader.entities.boss;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

import com.fourbit.pc_invader.utils.GameComponent;

import static com.fourbit.pc_invader.utils.Globals.GAME_WIDTH;


public class Boss implements GameComponent, Disposable {
    private boolean initPhase;
    private final Vector2 initPosition;
    private final Main main;
    private final BossConfig config;

    public Boss(World world, float x, float y) {
        this.main = new Main(world, 0, 0, 20.0f);

        this.main.setPosition(GAME_WIDTH + this.main.getWidth(), y);
        this.initPhase = true;
        this.initPosition = new Vector2(x, y);
        this.main.getBody().setLinearVelocity(-Math.abs(this.main.getPosition().x - initPosition.x) * 0.25f,0);

        this.config = new BossConfig();
    }


    public Vector2 getPosition() {
        return this.main.getPosition();
    }

    public boolean isInitPhase() {
        return initPhase;
    }

    @Override
    public void update() {
        if (this.initPhase) {
            this.main.getBody().setLinearVelocity(-Math.abs(this.main.getPosition().x - this.initPosition.x) * 0.2f,0);
            if (Math.abs(this.main.getPosition().x - this.initPosition.x) < 0.1) {
                this.main.setPosition(this.initPosition);
                this.main.getBody().setLinearVelocity(Vector2.Zero);
                this.initPhase = false;
            }
        } else {
            this.main.update();
        }
    }

    @Override
    public void draw(Batch batch) {
        this.main.draw(batch);
    }

    @Override
    public void dispose() {
        this.main.dispose();
    }
}
