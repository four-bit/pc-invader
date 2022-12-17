package com.fourbit.pc_invader.utils;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface GameComponent {
    public void update();
    public void draw(Batch batch);
    public void dispose();
}
