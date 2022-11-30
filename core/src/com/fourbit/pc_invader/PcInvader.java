package com.fourbit.pc_invader;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.ScreenUtils;


public class PcInvader extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture background;
    private OrthographicCamera viewportCamera;
    private FrameBuffer sceneFrameBuffer;
    private Player player;


    public static final int GAME_WIDTH = 1920;
    public static final int GAME_HEIGHT = 1080;


    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("background.png");
        player = new Player(GAME_WIDTH / 2, GAME_HEIGHT / 2, 200, "player/lvl3-default.png");

        sceneFrameBuffer = new FrameBuffer(Pixmap.Format.RGB888, GAME_WIDTH, GAME_HEIGHT, false);
        sceneFrameBuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        viewportCamera = new OrthographicCamera(GAME_WIDTH, GAME_HEIGHT);
        viewportCamera.position.set(0.5f * viewportCamera.viewportWidth, 0.5f * viewportCamera.viewportHeight, 0.0f);
        viewportCamera.update();
    }

    @Override
    public void render() {
        int calcPlayerSpeed = (int) (player.getSpeed() * Gdx.graphics.getDeltaTime());
        if (Gdx.input.isKeyPressed(Input.Keys.A))
            player.setX(player.getX() - calcPlayerSpeed);
        if (Gdx.input.isKeyPressed(Input.Keys.D))
            player.setX(player.getX() + calcPlayerSpeed);
        if (Gdx.input.isKeyPressed(Input.Keys.W))
            player.setY(player.getY() + calcPlayerSpeed);
        if (Gdx.input.isKeyPressed(Input.Keys.S))
            player.setY(player.getY() - calcPlayerSpeed);

        if (player.getX() - player.getWidth() / 2 < 0) player.setX(player.getWidth() / 2);
        if (player.getX() + player.getWidth() / 2 > GAME_WIDTH) player.setX(GAME_WIDTH - player.getWidth() / 2);
        if (player.getX() - player.getHeight() / 2 < 0) player.setY(player.getHeight() / 2);
        if (player.getX() + player.getHeight() / 2 > GAME_HEIGHT) player.setY(GAME_HEIGHT - player.getHeight() / 2);

        ScreenUtils.clear(0.0f, 0.0f, 0.0f, 0.0f);

        Gdx.gl20.glEnable(GL20.GL_SCISSOR_TEST); // re-enabled each frame because UI changes GL state
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(viewportCamera.combined);

        sceneFrameBuffer.begin();
        batch.begin();
        batch.draw(background, 0, 0);
        batch.draw(player.getTexture(), player.getX() - player.getWidth() / 2, player.getY() - player.getHeight() / 2);
        batch.end();
        sceneFrameBuffer.end();

        batch.begin();
        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        batch.draw(sceneFrameBuffer.getColorBufferTexture(), 0, GAME_HEIGHT, GAME_WIDTH, -GAME_HEIGHT);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        sceneFrameBuffer.dispose();
        background.dispose();
        player.dispose();
    }
}
