package com.fourbit.pc_invader.levels;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

import com.badlogic.gdx.utils.Disposable;
import com.fourbit.pc_invader.entities.Entity;
import com.fourbit.pc_invader.entities.player.Player;
import com.fourbit.pc_invader.utils.CollisionListener;
import com.fourbit.pc_invader.utils.GameComponent;
import com.fourbit.pc_invader.utils.Utils;

import static com.fourbit.pc_invader.utils.Globals.GAME_WIDTH;
import static com.fourbit.pc_invader.utils.Globals.GAME_HEIGHT;
import static com.fourbit.pc_invader.utils.Globals.PPM;


public class Level implements GameComponent, Disposable {
    protected final boolean debug;

    protected World physicsWorld;
    protected Box2DDebugRenderer physicsDebugRenderer;
    protected Array<Body> physicsBodies;
    protected OrthographicCamera viewportCamera, debugCamera;
    protected Texture background;


    public Level(String backgroundPath, boolean debug) {
        this.debug = debug;

        this.background = new Texture(backgroundPath);

        this.physicsWorld = new World(new Vector2(0, 0), true);
        this.physicsWorld.setContactListener(new CollisionListener());
        this.physicsBodies = new Array<>();

        this.viewportCamera = new OrthographicCamera(GAME_WIDTH, GAME_HEIGHT);
        this.viewportCamera.position.set(0.5f * viewportCamera.viewportWidth, 0.5f * viewportCamera.viewportHeight, 0.0f);
        this.viewportCamera.update();

        if (this.debug) {
            this.physicsDebugRenderer = new Box2DDebugRenderer();
            this.debugCamera = new OrthographicCamera(GAME_WIDTH / PPM, GAME_HEIGHT / PPM);
            this.debugCamera.position.set(0.5f * debugCamera.viewportWidth, 0.5f * debugCamera.viewportHeight, 0.0f);
            this.debugCamera.update();
        }
    }


    public OrthographicCamera getViewportCamera() {
        return viewportCamera;
    }

    public boolean isDebug() {
        return debug;
    }

    @Override
    public void update() {
        this.physicsWorld.step(1 / 60f, 6, 2);
    }

    @Override
    public void draw(Batch batch) {
        float backgroundAspectRatio = (float) this.background.getWidth() / this.background.getHeight();
        float screenAspectRatio = (float) GAME_WIDTH / GAME_HEIGHT;
        batch.draw(
                this.background,
                0,
                0,
                0,
                0,
                this.background.getWidth(),
                this.background.getHeight(),
                (float) (screenAspectRatio <= backgroundAspectRatio ? GAME_HEIGHT / this.background.getHeight() : GAME_WIDTH / this.background.getWidth()),
                (float) (screenAspectRatio <= backgroundAspectRatio ? GAME_HEIGHT / this.background.getHeight() : GAME_WIDTH / this.background.getWidth()),
                0,
                0,
                0,
                this.background.getWidth(),
                this.background.getHeight(),
                false,
                false
        );

        this.physicsWorld.getBodies(this.physicsBodies);
        // Do not remove the following comment line, it is there to suppress IntelliJ warning about LibGDX Array
        // noinspection GDXJavaUnsafeIterator
        for (Body body : this.physicsBodies) {
            Entity entity = (Entity) body.getUserData();
            if (entity != null) {
                if (!Utils.toMeters(entity.getPosition()).epsilonEquals(body.getPosition()))
                    entity.setPosition(Utils.toPixels(body.getPosition()));
                entity.setAngleRadian(body.getAngle());
                entity.draw(batch);
            }
        }

        if (this.debug) this.physicsDebugRenderer.render(this.physicsWorld, this.debugCamera.combined);
    }

    @Override
    public void dispose() {
        this.background.dispose();
        this.physicsWorld.dispose();

        if (this.debug) this.physicsDebugRenderer.dispose();
    }
}
