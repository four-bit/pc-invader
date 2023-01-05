package com.fourbit.pc_invader.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import static com.fourbit.pc_invader.utils.Globals.GAME_HEIGHT;
import static com.fourbit.pc_invader.utils.Globals.GAME_WIDTH;


public class InputProcessor {
    public static Vector2 getMouseVector() {
        Vector2 mouseCoords2D = new Vector2();
        Vector3 mouseCoords3D = new Vector3();
        OrthographicCamera cam = new OrthographicCamera(GAME_WIDTH, GAME_HEIGHT);

        mouseCoords3D.x = Gdx.input.getX();
        mouseCoords3D.y = GAME_HEIGHT - Gdx.input.getY();
        mouseCoords3D.z = 0;
        cam.project(mouseCoords3D);
        mouseCoords2D.x = mouseCoords3D.x;
        mouseCoords2D.y = mouseCoords3D.y;

        return mouseCoords2D;
    }

    public static boolean isShoot() {
        return Gdx.input.isButtonPressed(Input.Buttons.LEFT);
    }
}
