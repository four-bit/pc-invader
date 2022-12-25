package com.fourbit.pc_invader.entities.boss;


import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.fourbit.pc_invader.entities.Entity;
import com.fourbit.pc_invader.utils.BodyEditorLoader;
import com.fourbit.pc_invader.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;


public class Head extends Entity {
    private final float speed;
    private final ArrayList<HashMap<String, Integer>> locations = new ArrayList<>();
    private int locationIndex;
    private final Body body;

    public enum State {GOINGDOWN, GOINGDOWNLEFT, GOINGLEFT, GOINGUPLEFT, GOINGUP, GOINGUPRIGHT, GOINGRIGHT, GOINGDOWNRIGHT, STAND}
    public State state = State.GOINGUP;


    public Head(World world, float x, float y, float speed) {
        super("entities/boss/head.png", x, y, 0.0f);
        this.speed = speed;

        FileHandle handle = Gdx.files.internal("entities/boss/locations.txt");
        String[] text = handle.readString().split("\n");
        String[] header = text[0].split(",");
        header[1] = header[1].replaceAll("\\s", "");

        for (int i = 1; i < text.length; i++) {
            String[] line = text[i].split(",");
            for (int token_idx = 0; token_idx < line.length; token_idx++) {
                line[token_idx] = line[token_idx].replaceAll("\\s", "");
            }
            HashMap<String, Integer> row = new HashMap<>();
            for (int n = 0; n < 2; n++) {
                row.put(header[n], Integer.parseInt(line[n]));
            }
            this.locations.add(row);
        }

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(Utils.toMeters(super.getPosition()));
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;
        fixtureDef.isSensor = true;

        float scale = this.texture.getWidth();
        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("entities/boss/head.json"));
        loader.attachFixture(body, "wormhead.png", fixtureDef, scale);

        body.setAngularVelocity(0);
        body.setUserData(this);
    }


    private void checkDirection(float x, float y) {
        if (Utils.toMeters(this.body.getPosition().x) == x && Utils.toMeters(this.body.getPosition().y) > y) {
            this.state = State.GOINGDOWN;
        }
        if (Utils.toMeters(this.body.getPosition().x) > x && Utils.toMeters(this.body.getPosition().y) > y) {
            this.state = State.GOINGDOWNLEFT;
        }
        if (Utils.toMeters(this.body.getPosition().x) > x && Utils.toMeters(this.body.getPosition().y) == y) {
            this.state = State.GOINGLEFT;
        }
        if (Utils.toMeters(this.body.getPosition().x) > x && Utils.toMeters(this.body.getPosition().y) < y) {
            this.state = State.GOINGUPLEFT;
        }
        if (Utils.toMeters(this.body.getPosition().x) == x && Utils.toMeters(this.body.getPosition().y) < y) {
            this.state = State.GOINGUP;
        }
        if (Utils.toMeters(this.body.getPosition().x) < x && Utils.toMeters(this.body.getPosition().y) < y) {
            this.state = State.GOINGUPRIGHT;
        }
        if (Utils.toMeters(this.body.getPosition().x) < x && Utils.toMeters(this.body.getPosition().y) == y) {
            this.state = State.GOINGRIGHT;
        }
        if (Utils.toMeters(this.body.getPosition().x) < x && Utils.toMeters(this.body.getPosition().y) > y) {
            this.state = State.GOINGDOWNRIGHT;
        }
        if (Utils.toMeters(this.body.getPosition().x) == x && Utils.toMeters(this.body.getPosition().y) == y) {
            this.state = State.STAND;
        }
    }


    @Override
    public void update() {
        switch (state) {
            case GOINGDOWN:
                this.body.getPosition().add(Utils.toMeters(new Vector2(0, -this.speed)));
                checkDirection(locations.get(locationIndex).get("x"), locations.get(locationIndex).get("y"));
                break;
            case GOINGDOWNLEFT:
                this.body.getPosition().add(Utils.toMeters(new Vector2(-this.speed, -this.speed)));
                checkDirection(locations.get(locationIndex).get("x"), locations.get(locationIndex).get("y"));
                break;
            case GOINGLEFT:
                this.body.getPosition().add(Utils.toMeters(new Vector2(-this.speed, 0)));
                checkDirection(locations.get(locationIndex).get("x"), locations.get(locationIndex).get("y"));
                break;
            case GOINGUPLEFT:
                this.body.getPosition().add(Utils.toMeters(new Vector2(-this.speed, this.speed)));
                checkDirection(locations.get(locationIndex).get("x"), locations.get(locationIndex).get("y"));
                break;
            case GOINGUP:
                this.body.getPosition().add(Utils.toMeters(new Vector2(0, this.speed)));
                checkDirection(locations.get(locationIndex).get("x"), locations.get(locationIndex).get("y"));
                break;
            case GOINGUPRIGHT:
                this.body.getPosition().add(Utils.toMeters(new Vector2(this.speed, this.speed)));
                checkDirection(locations.get(locationIndex).get("x"), locations.get(locationIndex).get("y"));
                break;
            case GOINGRIGHT:
                this.body.getPosition().add(Utils.toMeters(new Vector2(this.speed, 0)));
                checkDirection(locations.get(locationIndex).get("x"), locations.get(locationIndex).get("y"));
                break;
            case GOINGDOWNRIGHT:
                this.body.getPosition().add(Utils.toMeters(new Vector2(this.speed, -this.speed)));
                checkDirection(locations.get(locationIndex).get("x"), locations.get(locationIndex).get("y"));
                break;
            case STAND:
                if (locationIndex < this.locations.size() - 1) {
                    this.locationIndex++;
                } else {
                    locationIndex = 0;
                }
                checkDirection(locations.get(locationIndex).get("x"), locations.get(locationIndex).get("y"));
                break;
        }

        this.angle = (float) Math.toDegrees(Math.atan2(
                (Utils.toMeters(this.body.getPosition().x) - this.locations.get(locationIndex).get("y")),
                (this.body.getPosition().y - this.locations.get(locationIndex).get("x"))));
    }
}
