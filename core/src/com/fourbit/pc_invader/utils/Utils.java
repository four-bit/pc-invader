package com.fourbit.pc_invader.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.fourbit.pc_invader.entities.Sprite;

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

    public static float toDegrees(float radians) {
        return (float) (radians * 180 / Math.PI);
    }

    public static Vector2 getBearingFromCenter(Sprite sprite) {
        Vector2 bearing2D = new Vector2();
        Vector3 bearing3D = new Vector3();
        OrthographicCamera cam = new OrthographicCamera();

        bearing3D.x = sprite.getPosition().x;
        bearing3D.y = sprite.getPosition().y;
        bearing3D.z = 0;
        cam.unproject(bearing3D);
        bearing2D.x = bearing3D.x;
        bearing2D.y = bearing3D.y;

        return bearing2D;
    }

    public static Vector2 getAngleToMouse(Sprite sprite) {
        return getBearingFromCenter(sprite).sub(InputProcessor.getMouseVector());
    }
}
