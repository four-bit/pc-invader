package com.fourbit.pc_invader.entities.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;

import static com.fourbit.pc_invader.utils.Globals.PAS;


public class HealthBar implements Disposable {
    private final Player player;
    private final Texture labelTexture, leftBracketTexture, rightBracketTexture, inactiveSegmentTexture, activeSegmentTexture;
    private final ArrayList<Image> segments;

    public HealthBar(float x, float y, Player player, Stage stage, int padding) {
        this.player = player;


        this.labelTexture = new Texture("entities/player/healthBar/label.png");
        this.leftBracketTexture = new Texture("entities/player/healthBar/left.bracket.png");
        this.rightBracketTexture = new Texture("entities/player/healthBar/right.bracket.png");
        this.inactiveSegmentTexture = new Texture("entities/player/healthBar/inactive.segment.png");
        this.activeSegmentTexture = new Texture("entities/player/healthBar/active.segment.png");


        Image label = new Image(this.labelTexture);
        label.setPosition(
                x + (this.labelTexture.getWidth() * .5f + padding) * PAS,
                y - (this.leftBracketTexture.getHeight() + padding - 1) * PAS
        );
        label.setScale(PAS);
        stage.addActor(label);

        Image leftBracket = new Image(this.leftBracketTexture);
        leftBracket.setPosition(
                label.getX() + (this.labelTexture.getWidth() + padding + this.leftBracketTexture.getWidth()) * PAS,
                y - (this.leftBracketTexture.getHeight() + padding) * PAS
        );
        leftBracket.setScale(PAS);
        stage.addActor(leftBracket);

        Image rightBracket = new Image(this.rightBracketTexture);
        rightBracket.setPosition(
                leftBracket.getX() + (this.rightBracketTexture.getWidth() + this.player.getConfig().getHealth() * (this.inactiveSegmentTexture.getWidth() + padding)) * PAS,
                y - (this.rightBracketTexture.getHeight() + padding) * PAS
        );
        rightBracket.setScale(PAS);
        stage.addActor(rightBracket);


        for (int i = 0; i < this.player.getConfig().getHealth(); i++) {
            Image inactiveSegment = new Image(this.inactiveSegmentTexture);
            inactiveSegment.setPosition(
                    leftBracket.getX() + (padding + this.leftBracketTexture.getWidth() * .5f + (this.inactiveSegmentTexture.getWidth() + padding) * i) * PAS,
                    y - (this.inactiveSegmentTexture.getHeight() + padding + 2) * PAS
            );
            inactiveSegment.setScale(PAS);
            stage.addActor(inactiveSegment);
        }

        this.segments = new ArrayList<>();
        for (int i = 0; i < this.player.getConfig().getHealth(); i++) {
            Image activeSegments = new Image(this.activeSegmentTexture);
            activeSegments.setPosition(
                    leftBracket.getX() + (padding + this.leftBracketTexture.getWidth() * .5f + (this.activeSegmentTexture.getWidth() + padding) * i) * PAS,
                    y - (this.activeSegmentTexture.getHeight() + padding + 2) * PAS
            );
            activeSegments.setScale(PAS);
            stage.addActor(activeSegments);
            this.segments.add(activeSegments);
        }
    }


    public void update() {
        for (int i = 0; i < this.segments.size(); i++) {
            this.segments.get(i).setVisible(i < this.player.getHp());
        }
    }

    @Override
    public void dispose() {
        this.labelTexture.dispose();
        this.inactiveSegmentTexture.dispose();
        this.activeSegmentTexture.dispose();
        this.leftBracketTexture.dispose();
        this.rightBracketTexture.dispose();
    }
}
