package com.fourbit.pc_invader.entities.boss;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Disposable;

import static com.fourbit.pc_invader.utils.Globals.PAS;


public class HealthBar implements Disposable {
    private final float x, y, width;
    private final Boss boss;
    private final Texture barTexture;
    private final Image bar;

    public HealthBar(float x, float y, float width, Boss boss, Stage stage) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.boss = boss;

        this.barTexture = new Texture("entities/boss/healthBar.png");
        this.bar = new Image(barTexture);
        this.bar.setPosition(this.x, this.y);
        this.bar.setScaleX(this.width);
        this.bar.setScaleY(PAS);
        stage.addActor(this.bar);
    }


    public void update() {
        float newScale = this.width * (float) this.boss.getHp() / (this.boss.getConfig().getMainHp() + this.boss.getConfig().getSegmentHp());
        this.bar.setScaleX(newScale);
        this.bar.setPosition(this.x + (this.width - newScale) * .5f, this.y);
    }

    @Override
    public void dispose() {
        this.barTexture.dispose();
    }
}
