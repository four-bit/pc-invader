package com.fourbit.pc_invader.entities.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import static com.fourbit.pc_invader.utils.Globals.GAME_HEIGHT;
import static com.fourbit.pc_invader.utils.Globals.GAME_WIDTH;
import static com.fourbit.pc_invader.utils.Globals.PAS;


public class HealthBar implements Disposable {
    private final int padding;
    private final Player player;
    private final Texture inactiveSegmentTexture, activeSegmentTexture, leftBracketTexture, rightBracketTexture;
    private Image leftBracket, rightBracket;
    private Array<Image> inactiveSegments, activeSegments;

    public HealthBar(Player player, Stage stage, int padding) {
        this.padding = padding;
        this.player = player;

        this.leftBracketTexture = new Texture("entities/player/healthBar/left.bracket.png");
        this.rightBracketTexture = new Texture("entities/player/healthBar/right.bracket.png");
        this.inactiveSegmentTexture = new Texture("entities/player/healthBar/inactive.segment.png");
        this.activeSegmentTexture = new Texture("entities/player/healthBar/active.segment.png");

        this.leftBracket = new Image(this.leftBracketTexture);
        this.leftBracket.setPosition(
                (leftBracketTexture.getWidth() + this.padding) * PAS,
                GAME_HEIGHT - (leftBracketTexture.getHeight() + padding) * PAS
        );
        this.leftBracket.setScale(PAS);
        this.rightBracket = new Image(this.rightBracketTexture);
        this.rightBracket.setPosition(
                (rightBracket.getWidth() + this.padding + (this.player.getConfig().getHealth() + this.padding) * this.inactiveSegmentTexture.getWidth()) * PAS,
                GAME_HEIGHT - (rightBracketTexture.getHeight() + padding) * PAS
        );
        this.rightBracket.setScale(PAS);

        this.inactiveSegments = new Array<>();
        for (int i = 0; i < this.player.getConfig().getHealth(); i++) {
            Image inactiveSegment = new Image(this.inactiveSegmentTexture);
            inactiveSegment.setPosition(
                    (this.inactiveSegmentTexture.getWidth() * i + padding) * PAS,
                    GAME_HEIGHT - (inactiveSegmentTexture.getHeight() + padding) * PAS
            );
            inactiveSegment.setScale(PAS);
        }

        this.activeSegments = new Array<>();
        for (int i = 0; i < this.player.getConfig().getHealth(); i++) {
            Image activeSegments = new Image(this.activeSegmentTexture);
            activeSegments.setPosition(
                    (this.activeSegmentTexture.getWidth() * i + padding) * PAS,
                    GAME_HEIGHT - (activeSegmentTexture.getHeight() + padding) * PAS
            );
            activeSegments.setScale(PAS);
        }

        stage.addActor(this.leftBracket);
        stage.addActor(this.rightBracket);
        for (Image inactiveSegment : inactiveSegments) stage.addActor(inactiveSegment);
        for (Image activeSegment : activeSegments) stage.addActor(activeSegment);
    }


    public void update() {
        // TODO: Implement this
    }

    @Override
    public void dispose() {
        this.inactiveSegmentTexture.dispose();
        this.activeSegmentTexture.dispose();
        this.leftBracketTexture.dispose();
        this.rightBracketTexture.dispose();
    }
}
