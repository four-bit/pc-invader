package com.fourbit.pc_invader.boss;


import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.HashMap;


public class Head {

    private int x, y;
    private boolean flip;
    private Texture texture;
    private float speed, angle;

    public enum State {GOINGDOWN, GOINGDOWNLEFT, GOINGLEFT, GOINGUPLEFT, GOINGUP, GOINGUPRIGHT, GOINGRIGHT, GOINGDOWNRIGHT, STAND}

    public State state = State.GOINGUP;
    private FileHandle handle = Gdx.files.internal("boss/location.txt");
    private String[] text;
    private ArrayList<HashMap<String, Integer>> location = new ArrayList<>();
    private int locationNum;
    public static final int GAME_WIDTH = 1920;
    public static final int GAME_HEIGHT = 1080;


    public Head(int x, int y, int speed, float angle) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.angle = angle;
        this.initGraphics();
        text = handle.readString().split("\n");
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
            location.add(row);
        }
    }

    public void initGraphics() {
        this.texture = new Texture("boss/wormhead.png");
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public ArrayList<HashMap<String, Integer>> getLocation() {
        return location;
    }

    public void setLocation(ArrayList<HashMap<String, Integer>> location) {
        this.location = location;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public boolean getFlip() {
        return flip;
    }

    public void setFlip(boolean flip) {
        this.flip = flip;
    }

    public void checkDirection(int x, int y) {
        if (this.x == x && this.y > y) {
            this.state = State.GOINGDOWN;
        }
        if (this.x > x && this.y > y) {
            this.state = State.GOINGDOWNLEFT;
        }
        if (this.x > x && this.y == y) {
            this.state = State.GOINGLEFT;
        }
        if (this.x > x && this.y < y) {
            this.state = State.GOINGUPLEFT;
        }
        if (this.x == x && this.y < y) {
            this.state = State.GOINGUP;
        }
        if (this.x < x && this.y < y) {
            this.state = State.GOINGUPRIGHT;
        }
        if (this.x < x && this.y == y) {
            this.state = State.GOINGRIGHT;
        }
        if (this.x < x && this.y > y) {
            this.state = State.GOINGDOWNRIGHT;
        }
        if (this.x == x && this.y == y) {
            this.state = State.STAND;
        }
    }

    public void update() {


        switch (state) {
            case GOINGDOWN:
                this.y -= this.speed;
                checkDirection(location.get(locationNum).get("x"), location.get(locationNum).get("y"));
                break;
            case GOINGDOWNLEFT:
                this.x -= this.speed;
                this.y -= this.speed;
                checkDirection(location.get(locationNum).get("x"), location.get(locationNum).get("y"));
                break;
            case GOINGLEFT:
                this.x -= this.speed;
                checkDirection(location.get(locationNum).get("x"), location.get(locationNum).get("y"));
                break;
            case GOINGUPLEFT:
                this.x -= this.speed;
                this.y += this.speed;
                checkDirection(location.get(locationNum).get("x"), location.get(locationNum).get("y"));
                break;
            case GOINGUP:
                this.y += this.speed;
                checkDirection(location.get(locationNum).get("x"), location.get(locationNum).get("y"));
                break;
            case GOINGUPRIGHT:
                this.x += this.speed;
                this.y += this.speed;
                checkDirection(location.get(locationNum).get("x"), location.get(locationNum).get("y"));
                break;
            case GOINGRIGHT:
                this.x += this.speed;
                checkDirection(location.get(locationNum).get("x"), location.get(locationNum).get("y"));
                break;
            case GOINGDOWNRIGHT:
                this.x += this.speed;
                this.y -= this.speed;
                checkDirection(location.get(locationNum).get("x"), location.get(locationNum).get("y"));
                break;
            case STAND:
                if (locationNum < this.location.size() - 1) {
                    this.locationNum++;
                } else {
                    locationNum = 0;
                }
                checkDirection(location.get(locationNum).get("x"), location.get(locationNum).get("y"));
                break;
        }

        this.angle =  (float) Math.toDegrees( Math.atan2(
                (float) (this.y-this.location.get(locationNum).get("y")),
                (float)(this.x-this.location.get(locationNum).get("x"))));

        if (this.x < location.get(locationNum).get("x")){
            this.flip = true;
        }
        else {
            flip = false;
        }
    }

    public void dispose() {
        texture.dispose();
    }
}

