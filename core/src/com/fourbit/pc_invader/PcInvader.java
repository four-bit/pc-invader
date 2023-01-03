package com.fourbit.pc_invader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.ScreenUtils;

import com.fourbit.pc_invader.levels.boss.Level;
import com.fourbit.pc_invader.menu.MainMenu;

import static com.fourbit.pc_invader.utils.Globals.GAME_HEIGHT;
import static com.fourbit.pc_invader.utils.Globals.GAME_WIDTH;


public class PcInvader extends ApplicationAdapter {
    public enum GameState { MAIN_MENU, LEVEL, BOSS_LEVEL, GAME_LOST, GAME_WON }

    protected boolean debug;
    private static GameState state;

    private SpriteBatch batch;
    private FrameBuffer sceneFrameBuffer;
    private MainMenu mainMenu;
    private com.fourbit.pc_invader.levels.Level bossLevel;


    public static void setState(GameState state) {
        PcInvader.state = state;
    }


    @Override
    public void create() {
        this.debug = true;  // TODO: Change this to false for production
        state = GameState.MAIN_MENU;

        this.batch = new SpriteBatch();
        this.sceneFrameBuffer = new FrameBuffer(Pixmap.Format.RGB888, GAME_WIDTH, GAME_HEIGHT, false);
        this.sceneFrameBuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        this.mainMenu = new MainMenu("menu/MainMenu.background.png");
        this.bossLevel = new Level(this.debug);
    }

    public void update() {
        switch (state) {
            case MAIN_MENU:
                this.mainMenu.update();
                break;
            case LEVEL:
                break;
            case BOSS_LEVEL:
                this.bossLevel.update();
                if (this.bossLevel.getState() == com.fourbit.pc_invader.levels.Level.State.PLAYER_LOST) state = GameState.GAME_LOST;
                if (this.bossLevel.getState() == com.fourbit.pc_invader.levels.Level.State.PLAYER_WON) state = GameState.GAME_WON;
                break;
            case GAME_LOST:
                this.bossLevel.reset();
                break;
            case GAME_WON:
                this.bossLevel.reset();
                break;
        }
    }

    @Override
    public void render() {
        this.update();

        // Graphics configuration
        this.batch.setProjectionMatrix(bossLevel.getViewportCamera().combined);
        ScreenUtils.clear(0.0f, 0.0f, 0.0f, 0.0f);
        Gdx.gl20.glEnable(GL20.GL_SCISSOR_TEST); // re-enabled each frame because UI changes GL state
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update sceneFrameBuffer
        this.sceneFrameBuffer.begin();
        this.batch.begin();
        {
            switch (state) {
                case MAIN_MENU:
                    this.mainMenu.draw(this.batch);
                    break;
                case LEVEL:
                    break;
                case BOSS_LEVEL:
                    this.bossLevel.draw(this.batch);
                    break;
                case GAME_LOST:
                    break;
                case GAME_WON:
                    break;
            }
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

        if (state == GameState.MAIN_MENU) this.mainMenu.draw();
    }

    @Override
    public void dispose() {
        this.mainMenu.dispose();
        this.bossLevel.dispose();
        this.batch.dispose();
        this.sceneFrameBuffer.dispose();
    }
}
