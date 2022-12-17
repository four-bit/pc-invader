package com.fourbit.pc_invader.utils;

import com.badlogic.gdx.math.Vector2;

public class Utils {
    public static float toMeters(int pixels) {
        return pixels / Globals.PPM;
    }

    public static Vector2 toMeters(Vector2 pixels) {
        return new Vector2(pixels.x / Globals.PPM, pixels.y / Globals.PPM);
    }

    public static int toPixels(float meters) {
        return (int) (meters * Globals.PPM);
    }

    public static Vector2 toPixels(Vector2 meters) {
        return new Vector2(meters.x * Globals.PPM, meters.y * Globals.PPM);
    }
}
