package com.fourbit.pc_invader.utils;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface GameComponent {
    void update();
    void draw(Batch batch);
}
