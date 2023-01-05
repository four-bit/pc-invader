package com.fourbit.pc_invader.menu;

import com.fourbit.pc_invader.PcInvader;
import com.fourbit.pc_invader.utils.InputProcessor;


public class SplashScreen extends Menu{
    public SplashScreen() throws InterruptedException {
        super("splash.png");
    }


    @Override
    public void update() {
        super.update();
        if (InputProcessor.isShoot()) {
            PcInvader.shootSound.play();
            PcInvader.setState(PcInvader.GameState.MAIN_MENU);
        }
    }
}
