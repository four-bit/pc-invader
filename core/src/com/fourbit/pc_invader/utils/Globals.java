package com.fourbit.pc_invader.utils;


import com.badlogic.gdx.Gdx;


public class Globals {
    public static final int GAME_WIDTH = Gdx.graphics.getWidth();  // Measured in pixels
    public static final int GAME_HEIGHT = Gdx.graphics.getHeight();  // Measured in pixels
    public static int PAS = (float) GAME_WIDTH / (float) GAME_HEIGHT >= 16.0f / 10.0f ? GAME_WIDTH / 320 : GAME_WIDTH / 230;  // Pixel art scale factor
    public static float PPM = GAME_WIDTH / 60.0f;  // Pixel-per-meter ration
}
