package com.fourbit.pc_invader.entities.boss;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;
import com.fourbit.pc_invader.utils.GameComponent;


public class Boss implements GameComponent {
    private final Main main;

    public Boss(World world, float x, float y, float speed) {
        this.main = new Main(world, x, y, speed);
    }


    @Override
    public void update() {
        this.main.update();
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
