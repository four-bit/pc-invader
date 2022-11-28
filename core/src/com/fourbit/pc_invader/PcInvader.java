package com.fourbit.pc_invader;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.ScreenUtils;


public class PcInvader extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture background;
    private OrthographicCamera viewportCamera;
    private FrameBuffer sceneFrameBuffer;

    public static final int FRAMEBUFFER_WIDTH = 1920;
    public static final int FRAMEBUFFER_HEIGHT = 1080;
    private static final int UPSCALE = 6;
    public static final int SCREEN_WIDTH = FRAMEBUFFER_WIDTH * UPSCALE;
    public static final int SCREEN_HEIGHT = FRAMEBUFFER_HEIGHT * UPSCALE;


    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("background.png");

        sceneFrameBuffer = new FrameBuffer(Pixmap.Format.RGB888, FRAMEBUFFER_WIDTH, FRAMEBUFFER_HEIGHT, false);
        sceneFrameBuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        viewportCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewportCamera.position.set(0.5f * viewportCamera.viewportWidth, 0.5f * viewportCamera.viewportHeight, 0.0f);
        viewportCamera.update();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.0f, 0.0f, 0.0f, 1.0f);

        Gdx.gl20.glEnable(GL20.GL_SCISSOR_TEST); // re-enabled each frame because UI changes GL state
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sceneFrameBuffer.begin();
        batch.begin();
        batch.draw(background, 0, 0);
        batch.end();
        sceneFrameBuffer.end();

        batch.begin();
        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        batch.draw(sceneFrameBuffer.getColorBufferTexture(), 0, SCREEN_HEIGHT, SCREEN_WIDTH, -SCREEN_HEIGHT);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        sceneFrameBuffer.dispose();
        background.dispose();
    }
}
