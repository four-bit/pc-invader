package com.fourbit.pc_invader.entities.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;

import static com.fourbit.pc_invader.utils.Globals.PAS;


public class BulletBar implements Disposable {
    private final Player player;
    private final Texture unloadedTexture, loadedTexture;
    private final ArrayList<Image> segments;

    public BulletBar(float x, float y, Player player, Stage stage, int padding) {
        this.player = player;

        this.unloadedTexture = new Texture("entities/player/bulletBar/unloaded.png");
        this.loadedTexture = new Texture("entities/player/bulletBar/loaded.png");


        for (int i = 0; i < this.player.getConfig().getAmmo(); i++) {
            Image unloadedSegment = new Image(this.unloadedTexture);
            unloadedSegment.setPosition(
                    x + (padding + this.unloadedTexture.getWidth()) * i * PAS,
                    y - (this.unloadedTexture.getHeight() + padding) * PAS
            );
            unloadedSegment.setScale(PAS);
            stage.addActor(unloadedSegment);
        }

        this.segments = new ArrayList<>();
        for (int i = 0; i < this.player.getConfig().getAmmo(); i++) {
            Image loadedSegment = new Image(this.loadedTexture);
            loadedSegment.setPosition(
                    x + (padding + this.loadedTexture.getWidth()) * i * PAS,
                    y - (this.loadedTexture.getHeight() + padding) * PAS
            );
            loadedSegment.setScale(PAS);
            stage.addActor(loadedSegment);
            this.segments.add(loadedSegment);
        }
    }


    public void update() {
        for (int i = 0; i < this.segments.size(); i++) {
            this.segments.get(i).setVisible(i < this.player.getAmmo());
        }
    }

    @Override
    public void dispose() {
        this.unloadedTexture.dispose();
        this.loadedTexture.dispose();
    }
}
