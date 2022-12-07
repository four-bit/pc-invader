package com.fourbit.pc_invader;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.fourbit.pc_invader.Boss.Body;
import com.fourbit.pc_invader.Boss.Boss;
import com.fourbit.pc_invader.Boss.Head;


public class PcInvader extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture background;
    private OrthographicCamera viewportCamera;
    private FrameBuffer sceneFrameBuffer;
    private Player player;
    private Head head;
    private Body body;
    private Array<Body> bodies = new Array<>();
    public static final int GAME_WIDTH = 1920;
    public static final int GAME_HEIGHT = 1080;

    public static float rot = 0.0f;


    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("levels/level-boss.bg.png");
        player = new Player(
                GAME_WIDTH / 2,
                GAME_HEIGHT / 2,
                20,
                8,
                3,
                true,
                0.0f
        );
        head = new Head(
                GAME_WIDTH / 2,
                GAME_HEIGHT / 2,
                2
        );
//        body = new Body(
//                GAME_WIDTH / 2,
//                GAME_HEIGHT / 2,
//                50
//        );

        for (int i = 0; i < 7; i++) {
            bodies.add(new Body(
                    head.getX() -head.getTexture().getWidth() +head.getTexture().getWidth()/2,
                    head.getY() -head.getTexture().getHeight()- 75 *i,
                    2
            ));
        }
        sceneFrameBuffer = new FrameBuffer(Pixmap.Format.RGB888, GAME_WIDTH, GAME_HEIGHT, false);
        sceneFrameBuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        viewportCamera = new OrthographicCamera(GAME_WIDTH, GAME_HEIGHT);
        viewportCamera.position.set(0.5f * viewportCamera.viewportWidth, 0.5f * viewportCamera.viewportHeight, 0.0f);
        viewportCamera.update();

    }

    @Override
    public void render() {
        player.update();
        head.update();
        player.setAngle(rot);
        PcInvader.rot += player.getSpeed() * 50 * Gdx.graphics.getDeltaTime() % 360.0f;

        ScreenUtils.clear(0.0f, 0.0f, 0.0f, 0.0f);

        Gdx.gl20.glEnable(GL20.GL_SCISSOR_TEST); // re-enabled each frame because UI changes GL state
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(viewportCamera.combined);

        sceneFrameBuffer.begin();
        batch.begin();
        batch.draw(background, 0, 0);
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

        batch.draw(
                head.getTexture(),
                head.getX() -(float)head.getTexture().getWidth(),
                head.getY() - (float) head.getTexture().getHeight() ,
                head.getTexture().getWidth() * 5,
                head.getTexture().getHeight() * 5
        );
        batch.draw(
                bodies.get(0).getTexture(),
                bodies.get(0).getX(),
                bodies.get(0).getY(),
                bodies.get(0).getTexture().getWidth() * 5,
                bodies.get(0).getTexture().getHeight() * 5
        );
        bodies.get(0).update(head.getX(), head.getY());
        for (int i = 1; i < 7; i++) {
            batch.draw(
                    bodies.get(i).getTexture(),
                    bodies.get(i).getX(),
                    bodies.get(i).getY(),
                    bodies.get(i).getTexture().getWidth() * 5,
                    bodies.get(i).getTexture().getHeight() * 5
            );
            bodies.get(i).update(bodies.get(i-1).getX(),bodies.get(i-1).getY());
        }


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

