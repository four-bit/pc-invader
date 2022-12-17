package com.fourbit.pc_invader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.fourbit.pc_invader.levels.BossLevel;
import com.fourbit.pc_invader.levels.Level;


import static com.fourbit.pc_invader.utils.Globals.GAME_HEIGHT;
import static com.fourbit.pc_invader.utils.Globals.GAME_WIDTH;


public class PcInvader extends ApplicationAdapter {
    private boolean debug;

    private SpriteBatch batch;
    private FrameBuffer sceneFrameBuffer;
    private Level level;


    @Override
    public void create() {
        this.debug = true;  // TODO: Change this to false for production

        this.batch = new SpriteBatch();
        this.sceneFrameBuffer = new FrameBuffer(Pixmap.Format.RGB888, GAME_WIDTH, GAME_HEIGHT, false);
        this.sceneFrameBuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        this.level = new BossLevel(this.debug);
    }

    public void update() {
        this.level.update();
    }

    @Override
    public void render() {
        this.update();

        // Graphics configuration
        this.batch.setProjectionMatrix(level.getViewportCamera().combined);
        ScreenUtils.clear(0.0f, 0.0f, 0.0f, 0.0f);
        Gdx.gl20.glEnable(GL20.GL_SCISSOR_TEST); // re-enabled each frame because UI changes GL state
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update sceneFrameBuffer
        this.sceneFrameBuffer.begin();
        this.batch.begin();
        {
            level.draw(batch);
        }
        this.batch.end();
        this.sceneFrameBuffer.end();


        // Render sceneFrameBuffer
        this.batch.begin();
        {
            this.batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            this.batch.draw(sceneFrameBuffer.getColorBufferTexture(), 0, GAME_HEIGHT, GAME_WIDTH, -GAME_HEIGHT);
        }
        this.batch.end();
    }

    @Override
    public void dispose() {
        this.level.dispose();
        this.batch.dispose();
        this.sceneFrameBuffer.dispose();
    }

    public static Vector2 getMouseCoords() {
        Vector2 mouseCoords2D = new Vector2();
        Vector3 mouseCoords3D = new Vector3();
        OrthographicCamera cam = new OrthographicCamera();

        mouseCoords3D.x = Gdx.input.getX();
        mouseCoords3D.y = GAME_HEIGHT - Gdx.input.getY();
        mouseCoords3D.z = 0;
        cam.unproject(mouseCoords3D);
        mouseCoords2D.x = mouseCoords3D.x;
        mouseCoords2D.y = mouseCoords3D.y;

        return mouseCoords2D;
    }
}
