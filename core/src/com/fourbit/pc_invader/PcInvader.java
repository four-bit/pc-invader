package com.fourbit.pc_invader;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.fourbit.pc_invader.ui.GameHUD;
import com.fourbit.pc_invader.boss.Boss;



public class PcInvader extends ApplicationAdapter {
    private boolean debug;
    private SpriteBatch batch;
    private Texture background;
    private OrthographicCamera viewportCamera;
    private FrameBuffer sceneFrameBuffer;
    private Player player;
    private GameHUD gameHud;
    public static final int GAME_WIDTH = 1920;
    public static final int GAME_HEIGHT = 1080;
    private Boss boss;


    @Override
    public void create() {
        debug = true;  // TODO: Change this to false for production

        batch = new SpriteBatch();
        background = new Texture("levels/level-boss.bg.png");
        player = new Player(
                GAME_WIDTH / 4,
                GAME_HEIGHT / 2,
                20,
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
        player.update();
        boss.update();
        
        gameHud.updateFrom(player);


        // Render graphics
        ScreenUtils.clear(0.0f, 0.0f, 0.0f, 0.0f);

        Gdx.gl20.glEnable(GL20.GL_SCISSOR_TEST); // re-enabled each frame because UI changes GL state
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(viewportCamera.combined);

        sceneFrameBuffer.begin();
        batch.begin();

        batch.draw(background, 0, 0);

        for (int i = boss.getBodies().size - 1; i > -1; i--){
            batch.draw(
                    boss.getBodies().get(i).getTexture(),
                    boss.getBodies().get(i).getX() + 200,
                    boss.getBodies().get(i).getY() - 50 - 75*i,
                    (float) boss.getBodies().get(i).getTexture().getWidth()/2,
                    (float) boss.getBodies().get(i).getTexture().getHeight()/2,
                    boss.getBodies().get(i).getTexture().getWidth() ,
                    boss.getBodies().get(i).getTexture().getHeight(),
                    1.0f,
                    1.0f,
                    boss.getBodies().get(i).getAngle(),
                    0,
                    0,
                    boss.getHead().getTexture().getWidth(),
                    boss.getHead().getTexture().getHeight(),
                    false,
                    false
            );
        }

        batch.draw(
                boss.getHead().getTexture(),
                boss.getHead().getX(),
                boss.getHead().getY(),
                (float) boss.getHead().getTexture().getWidth()/2,
                (float) boss.getHead().getTexture().getHeight()/2,
                boss.getHead().getTexture().getWidth() ,
                boss.getHead().getTexture().getHeight(),
                1.0f,
                1.0f,
                boss.getHead().getAngle(),
                0,
                0,
                boss.getHead().getTexture().getWidth(),
                boss.getHead().getTexture().getHeight(),
                false,
                boss.getHead().getFlip()
        );



        player.getExhaustEffect().draw(batch, Gdx.graphics.getDeltaTime());
        batch.draw(
                player.getTexture(),
                player.getX() - (float) player.getWidth() / 2,
                player.getY() - (float) player.getHeight() / 2,
                (float) player.getWidth() / 2,
                (float) player.getHeight() / 2,
                player.getWidth(),
                player.getHeight(),
                1.0f,
                1.0f,
                player.getAngle(),
                0,
                0,
                player.getWidth(),
                player.getHeight(),
                false,
                false
        );

        batch.end();
        sceneFrameBuffer.end();

        batch.begin();
        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        batch.draw(sceneFrameBuffer.getColorBufferTexture(), 0, GAME_HEIGHT, GAME_WIDTH, -GAME_HEIGHT);
        batch.end();

        gameHud.draw();
    }

    @Override
    public void dispose() {
        batch.dispose();
        sceneFrameBuffer.dispose();
        background.dispose();
        player.dispose();
        boss.dispose();
        gameHud.dispose();
    }

    public boolean isDebug() { return debug; }

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