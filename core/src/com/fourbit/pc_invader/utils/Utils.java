package com.fourbit.pc_invader.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.fourbit.pc_invader.entities.Entity;

import com.sun.tools.javac.main.Option;

import static com.fourbit.pc_invader.utils.Globals.GAME_HEIGHT;
import static com.fourbit.pc_invader.utils.Globals.GAME_WIDTH;


public class Utils {
    public enum OrthographicDirection {
        NONE,
        UP,
        DOWN,
        LEFT,
        RIGHT,
    }

    public enum Direction {
        NONE,
        UP,
        DOWN,
        LEFT,
        RIGHT,
        UP_LEFT,
        UP_RIGHT,
        DOWN_LEFT,
        DOWN_RIGHT
    }


    public static boolean isOutOfScreen(Entity entity) {
        return ((entity.getPosition().y + (float) entity.getHeight() / 2) > GAME_HEIGHT) ||  // TOP
                ((entity.getPosition().y - (float) entity.getHeight() / 2) < 0) ||  // BOTTOM
                ((entity.getPosition().x - (float) entity.getWidth() / 2) < 0) ||  // LEFT
                ((entity.getPosition().x + (float) entity.getWidth() / 2) > GAME_WIDTH);  // RIGHT
    }

    public static boolean isOutOfScreen(Entity entity, OrthographicDirection direction) {
        switch (direction) {
            case UP:
                return (entity.getPosition().y + (float) entity.getHeight() / 2) > GAME_HEIGHT;
            case DOWN:
                return (entity.getPosition().y - (float) entity.getHeight() / 2) < 0;
            case LEFT:
                return (entity.getPosition().x - (float) entity.getWidth() / 2) < 0;
            case RIGHT:
                return (entity.getPosition().x + (float) entity.getWidth() / 2) > GAME_WIDTH;
            default:
                return false;
        }
    }

    public static Direction whereOutOfScreen(Entity entity) {
        Direction direction = Direction.NONE;

        if ((entity.getPosition().y + (float) entity.getHeight() / 2) > GAME_HEIGHT) {
            direction = Direction.UP;
        }
        if ((entity.getPosition().y - (float) entity.getHeight() / 2) < 0) {
            direction = Direction.DOWN;
        }
        if ((entity.getPosition().x - (float) entity.getWidth() / 2) < 0) {
            direction = Direction.LEFT;
        }
        if ((entity.getPosition().x + (float) entity.getWidth() / 2) > GAME_WIDTH) {
            direction = Direction.RIGHT;
        }
        if (((entity.getPosition().y + (float) entity.getHeight() / 2) > GAME_HEIGHT) && ((entity.getPosition().x - (float) entity.getWidth() / 2) < 0)) {
            direction = Direction.UP_LEFT;
        }
        if (((entity.getPosition().y + (float) entity.getHeight() / 2) > GAME_HEIGHT) && ((entity.getPosition().x + (float) entity.getWidth() / 2) > GAME_WIDTH)) {
            direction = Direction.UP_RIGHT;
        }
        if (((entity.getPosition().y - (float) entity.getHeight() / 2) < 0) && ((entity.getPosition().x - (float) entity.getWidth() / 2) < 0)) {
            direction = Direction.DOWN_LEFT;
        }
        if (((entity.getPosition().y - (float) entity.getHeight() / 2) < 0) && ((entity.getPosition().x + (float) entity.getWidth() / 2) > GAME_WIDTH)) {
            direction = Direction.DOWN_RIGHT;
        }

        return direction;
    }

    public static float toMeters(float pixels) {
        return pixels / Globals.PPM;
    }

    public static Vector2 toMeters(Vector2 pixels) {
        return new Vector2(pixels.x / Globals.PPM, pixels.y / Globals.PPM);
    }

    public static float toPixels(float meters) {
        return (int) (meters * Globals.PPM);
    }

    public static Vector2 toPixels(Vector2 meters) {
        return new Vector2(meters.x * Globals.PPM, meters.y * Globals.PPM);
    }

    public static float toDegrees(float radians) {
        return (float) (radians * 180 / Math.PI);
    }

    public static float toRadians(float degrees) {
        return (float) (degrees * Math.PI / 180);
    }

    public static Vector2 getBearingFromCenter(Entity entity) {
        Vector2 bearing2D = new Vector2();
        Vector3 bearing3D = new Vector3();
        OrthographicCamera cam = new OrthographicCamera();

        bearing3D.x = entity.getPosition().x;
        bearing3D.y = entity.getPosition().y;
        bearing3D.z = 0;
        cam.unproject(bearing3D);
        bearing2D.x = bearing3D.x;
        bearing2D.y = bearing3D.y;

        return bearing2D;
    }

    public static Vector2 getAngleToMouse(Entity entity) {
        return getBearingFromCenter(entity).sub(InputProcessor.getMouseVector());
    }
}
