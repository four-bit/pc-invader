package com.fourbit.pc_invader;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.fourbit.pc_invader.ui.GameHUD;
import com.fourbit.pc_invader.boss.Boss;


public class PcInvader extends ApplicationAdapter {
    private boolean debug;

    private World physicsWorld;
    private Box2DDebugRenderer physicsDebugRenderer;
    private SpriteBatch batch;
    private Texture background;
    private OrthographicCamera viewportCamera;
    private FrameBuffer sceneFrameBuffer;
    private Boss boss;
    private Player player;
    private GameHUD gameHud;
    public static final int GAME_WIDTH = 1920;
    public static final int GAME_HEIGHT = 1080;


    @Override
    public void create() {
        debug = true;  // TODO: Change this to false for production

        physicsWorld = new World(new Vector2(0, 0), false);
        physicsDebugRenderer = new Box2DDebugRenderer();

        batch = new SpriteBatch();
        background = new Texture("levels/glob.bg.png");
        player = new Player(
                physicsWorld,
                GAME_WIDTH / 4,
                GAME_HEIGHT / 2,
                15,
                8,
                3,
                true,
                0.0f
        );

        boss = new Boss(
                GAME_WIDTH / 2,
                GAME_HEIGHT / 2,
                2
        );

        sceneFrameBuffer = new FrameBuffer(Pixmap.Format.RGB888, GAME_WIDTH, GAME_HEIGHT, false);
        sceneFrameBuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        gameHud = new GameHUD(this);

        viewportCamera = new OrthographicCamera(GAME_WIDTH, GAME_HEIGHT);
        viewportCamera.position.set(0.5f * viewportCamera.viewportWidth, 0.5f * viewportCamera.viewportHeight, 0.0f);
        viewportCamera.update();

    }

    @Override
    public void render() {
        // Update game state
        physicsWorld.step(1 / 60f, 6, 2);
        player.update();
        boss.update();
        gameHud.updateFrom(player);

        // Graphics configuration
        batch.setProjectionMatrix(viewportCamera.combined);
        ScreenUtils.clear(0.0f, 0.0f, 0.0f, 0.0f);
        Gdx.gl20.glEnable(GL20.GL_SCISSOR_TEST); // re-enabled each frame because UI changes GL state
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update sceneFrameBuffer
        sceneFrameBuffer.begin();
        batch.begin();
        {
            batch.draw(background, 0, 0);
            boss.draw(batch);
            player.draw(batch);
        }
        batch.end();
        sceneFrameBuffer.end();


        // Render sceneFrameBuffer
        batch.begin();
        {
            batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            batch.draw(sceneFrameBuffer.getColorBufferTexture(), 0, GAME_HEIGHT, GAME_WIDTH, -GAME_HEIGHT);
        }
        batch.end();

        // Render HUD and debug information
        gameHud.draw();

        if (debug) physicsDebugRenderer.render(physicsWorld, viewportCamera.combined);
    }

    @Override
    public void dispose() {
        batch.dispose();
        sceneFrameBuffer.dispose();
        background.dispose();
        player.dispose();
        boss.dispose();
        gameHud.dispose();
        physicsDebugRenderer.dispose();
        physicsWorld.dispose();
    }

    public boolean isDebug() {
        return debug;
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
