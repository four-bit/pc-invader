package com.fourbit.pc_invader.entities.boss;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;
import com.fourbit.pc_invader.utils.GameComponent;


public class Boss implements GameComponent {
    private final Head head;

    public Boss(World world, float x, float y, float speed) {
        this.head = new Head(world, x, y, speed);
    }


    @Override
    public void update() {
        this.head.update();
    }

    @Override
    public void draw(Batch batch) {
        this.head.draw(batch);
    }

    @Override
    public void dispose() {
        this.head.dispose();
    }
}
