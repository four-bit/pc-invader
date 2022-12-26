package com.fourbit.pc_invader.utils;

public class Globals {
    public static final int GAME_WIDTH = 1920;  // Measured in pixels
    public static final int GAME_HEIGHT = 1080;  // Measured in pixels
    public static final int PAS = 6;  // Pixel art scale factor
    public static final float PPM = 32.0f;  // Pixel-per-meter ration

    public static final float PLAYER_BULLET_SPEED = .6f;
    public static final int PLAYER_MAX_AMMO = 20;
    public static final long PLAYER_SHOOT_COOLDOWN_MS = 100;
    public static final long PLAYER_AMMO_REGEN_COOLDOWN_MS = 200;
}
