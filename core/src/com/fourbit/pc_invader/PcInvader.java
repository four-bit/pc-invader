package com.fourbit.pc_invader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.ScreenUtils;

import com.fourbit.pc_invader.levels.boss.Level;
import com.fourbit.pc_invader.menu.GameOverMenu;
import com.fourbit.pc_invader.menu.MainMenu;
import com.fourbit.pc_invader.menu.SplashScreen;


import static com.fourbit.pc_invader.utils.Globals.GAME_HEIGHT;
import static com.fourbit.pc_invader.utils.Globals.GAME_WIDTH;


public class PcInvader extends ApplicationAdapter {
    public enum GameState {SPLASH_SCREEN, MAIN_MENU, LEVEL, BOSS_LEVEL, GAME_LOST, GAME_WON}

    protected boolean debug;
    private static GameState state;

    private SpriteBatch batch;
    private FrameBuffer sceneFrameBuffer;
    private SplashScreen splashScreen;
    private MainMenu mainMenu;
    private GameOverMenu gameOverMenuLost, gameOverMenuWon;
    private com.fourbit.pc_invader.levels.Level bossLevel;

    public static Music bgMusic;
    public static Music bossMusic;
    public static Music diedMusic;
    public static Music winMusic;

    public static Sound shootSound;
    public static Sound hitSound;
    public static Sound crashSound;
    public static long startTime;

    public static void setState(GameState state) {
        PcInvader.state = state;
    }


    @Override
    public void create() {
        startTime = System.nanoTime();
        bgMusic = Gdx.audio.newMusic(Gdx.files.internal("sfx/bgmusic/menu.ogg"));
        bossMusic = Gdx.audio.newMusic(Gdx.files.internal("sfx/bgmusic/boss.ogg"));
        diedMusic = Gdx.audio.newMusic(Gdx.files.internal("sfx/bgmusic/died.ogg"));
        winMusic = Gdx.audio.newMusic(Gdx.files.internal("sfx/bgmusic/win.ogg"));
        shootSound = Gdx.audio.newSound(Gdx.files.internal("sfx/shooting.wav"));
        hitSound = Gdx.audio.newSound(Gdx.files.internal("sfx/hit.wav"));
        crashSound = Gdx.audio.newSound(Gdx.files.internal("sfx/crash.wav"));

        PcInvader.bgMusic.setLooping(true);
        PcInvader.bossMusic.setLooping(true);



        this.debug = true;  // TODO: Change this to false for production
        setState(GameState.SPLASH_SCREEN);

        this.batch = new SpriteBatch();
        this.sceneFrameBuffer = new FrameBuffer(Pixmap.Format.RGB888, GAME_WIDTH, GAME_HEIGHT, false);
        this.sceneFrameBuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        try {
            this.splashScreen = new SplashScreen();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.mainMenu = new MainMenu();
        this.bossLevel = new Level(this.debug);
        this.gameOverMenuLost = new GameOverMenu("YOU DIED!");
        this.gameOverMenuWon = new GameOverMenu("YOU SURVIVED!");
    }

    public void update() {
        switch (state) {
            case SPLASH_SCREEN:
                this.splashScreen.update();
            case MAIN_MENU:
                this.mainMenu.update();
                Gdx.input.setInputProcessor(this.mainMenu.getMainStage());
                break;
            case LEVEL:
                break;
            case BOSS_LEVEL:
                this.bossLevel.update();
                if (this.bossLevel.getState() == com.fourbit.pc_invader.levels.Level.State.PLAYER_LOST)
                    state = GameState.GAME_LOST;
                if (this.bossLevel.getState() == com.fourbit.pc_invader.levels.Level.State.PLAYER_WON)
                    state = GameState.GAME_WON;
                break;
            case GAME_LOST:
                this.bossLevel.reset();
                this.gameOverMenuLost.update();
                Gdx.input.setInputProcessor(this.gameOverMenuLost.getMainStage());
                break;
            case GAME_WON:
                this.bossLevel.reset();
                this.gameOverMenuWon.update();
                Gdx.input.setInputProcessor(this.gameOverMenuWon.getMainStage());
                break;
        }
    }

    @Override
    public void render() {
        this.update();

        // Graphics configuration
        ScreenUtils.clear(0.0f, 0.0f, 0.0f, 0.0f);
        Gdx.gl20.glEnable(GL20.GL_SCISSOR_TEST); // re-enabled each frame because UI changes GL state
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update sceneFrameBuffer
        this.sceneFrameBuffer.begin();
        this.batch.begin();
        {
            switch (state) {
                case SPLASH_SCREEN:
                    this.splashScreen.draw(this.batch);
                    break;
                case MAIN_MENU:
                    this.mainMenu.draw(this.batch);
                    break;
                case LEVEL:
                    break;
                case BOSS_LEVEL:
                    this.bossLevel.draw(this.batch);
                    break;
                case GAME_LOST:
                    PcInvader.bossMusic.stop();
                    PcInvader.bossMusic.dispose();
                    PcInvader.diedMusic.play();
                    this.gameOverMenuLost.draw(this.batch);
                    break;
                case GAME_WON:
                    PcInvader.bossMusic.stop();
                    PcInvader.bossMusic.dispose();
                    PcInvader.winMusic.play();
                    this.gameOverMenuWon.draw(this.batch);
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

        switch (state) {
            case SPLASH_SCREEN:
                this.splashScreen.draw();
                break;
            case MAIN_MENU:
                this.mainMenu.draw();
                break;
            case GAME_LOST:
                this.gameOverMenuLost.draw();
                break;
            case GAME_WON:
                this.gameOverMenuWon.draw();
                break;
        }
    }

    @Override
    public void dispose() {
        this.splashScreen.dispose();
        this.mainMenu.dispose();
        this.bossLevel.dispose();
        this.batch.dispose();
        this.sceneFrameBuffer.dispose();
        this.gameOverMenuLost.dispose();
        this.gameOverMenuWon.dispose();
    }
}
