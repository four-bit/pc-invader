package com.fourbit.pc_invader.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.fourbit.pc_invader.PcInvader;
import com.fourbit.pc_invader.utils.InputProcessor;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

import static com.badlogic.gdx.Input.*;
import static com.badlogic.gdx.Input.Buttons.*;
import static com.fourbit.pc_invader.utils.Globals.GAME_HEIGHT;
import static com.fourbit.pc_invader.utils.Globals.GAME_WIDTH;

public class SplashScreen extends Menu{
    private long startTime;
    private long nanoTime;
    public SplashScreen() throws InterruptedException {
        super("splash.png");
    }

    @Override
    public void update() {
        super.update();
        if (InputProcessor.isShoot()) {
            System.out.println("a");
            PcInvader.shootSound.play();
            PcInvader.setState(PcInvader.GameState.MAIN_MENU);
        }
    }
    @Override
    public void draw(Batch batch) {
        float backgroundAspectRatio = (float) this.background.getWidth() / this.background.getHeight();
        float screenAspectRatio = (float) GAME_WIDTH / GAME_HEIGHT;
        batch.draw(
                this.background,
                0,
                0
        );
    }
}
