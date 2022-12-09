package com.fourbit.pc_invader.Boss;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.sun.tools.javac.main.Option;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.Scanner;
import static com.fourbit.pc_invader.PcInvader.GAME_WIDTH;
import static com.fourbit.pc_invader.PcInvader.GAME_HEIGHT;
import static java.lang.Math.toRadians;

public class Head {

    private int x, y;
    private Texture texture;
    private float speed, angle;
    public enum State {GOINGDOWN,GOINGDOWNLEFT,GOINGLEFT,GOINGUPLEFT,GOINGUP,GOINGUPRIGHT,GOINGRIGHT,GOINGDOWNRIGHT,STAND};
    public State state=State.GOINGUP;
    private FileHandle handle = Gdx.files.internal("boss/location.txt");
    private String[] text;
    public int currentHeight = 800;
    private ArrayList<HashMap<String,Integer>> location = new ArrayList<>();
    private int delta;
    private int i;


    public Head(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.initGraphics();
        text = handle.readString().split("\n");
        for (String string : text) {
            System.out.println(string);
        }
        String[] header = text[0].split(",");

        for (int i = 1; i<text.length; i++) {
            String[] line = text[i].split(",");
            HashMap<String, Integer> row = new HashMap<>();
            for (int n = 0; n < 2; n++) {
                row.put(header[n], Integer.parseInt(line[n]));
            }
            location.add(row);
        }
        for (HashMap<String,Integer> row : location){
            System.out.println(row.get("x"));
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

    public void checkDirection(int x, int y){
        if (this.x == x && this.y > y){
            this.state = State.GOINGDOWN;
        }
        if (this.x > x && this.y > y) {
            this.state =  State.GOINGDOWNLEFT;
        }
        if (this.x > x && this.y == y){
            this.state = State.GOINGLEFT;
        }
        if (this.x > x && this.y < y){
            this.state = State.GOINGUPLEFT;
        }
        if (this.x == x && this.y < y){
            this.state = State.GOINGUP;
        }
        if (this.x < x && this.y < y){
            this.state = State.GOINGUPRIGHT;
        }
        if (this.x < x && this.y == y ){
            this.state = State.GOINGRIGHT;
        }
        if (this.x < x && this.y > y){
            this.state =  State.GOINGDOWNRIGHT;
        }
        if (this.x == x && this.y == y){
            this.state = State.STAND;
        }
    }
    public void update() {


        switch (state) {
            case GOINGDOWN:
                this.y -= this.speed;
                checkDirection(location.get(i).get("x"),location.get(i).get("y"));
                break;
            case GOINGDOWNLEFT:
                this.x -= this.speed;
                this.y -= this.speed;
                checkDirection(location.get(i).get("x"),location.get(i).get("y"));
                break;
            case GOINGLEFT:
                this.x -= this.speed;
                checkDirection(location.get(i).get("x"),location.get(i).get("y"));
                break;
            case GOINGUPLEFT:
                this.x -= this.speed;
                this.y += this.speed;
                checkDirection(location.get(i).get("x"),location.get(i).get("y"));
                break;
            case GOINGUP:
                this.y += this.speed;
                checkDirection(location.get(i).get("x"),location.get(i).get("y"));
                break;
            case GOINGUPRIGHT:
                this.x += this.speed;
                this.y += this.speed;
                checkDirection(location.get(i).get("x"),location.get(i).get("y"));
                break;
            case GOINGRIGHT:
                this.x += this.speed;
                checkDirection(location.get(i).get("x"),location.get(i).get("y"));
                break;
            case GOINGDOWNRIGHT:
                this.x += this.speed;
                this.y -= this.speed;
                checkDirection(location.get(i).get("x"),location.get(i).get("y"));
                break;
            case STAND:
                if (i < this.location.size()-1) {
                    this.i++;
                }else {
                    i=0;
                }
                checkDirection(location.get(i).get("x"),location.get(i).get("y"));

                System.out.println(i);

                System.out.println(location.get(i).get("x"));
                break;
        }

        // GO DOWN
//        if (this.x == x && this.y > y){
//            this.y -= this.speed;
//        }
//        //GO DOWN-LEFT
//        if (this.x > x && this.y > y){
//            this.x -= this.speed;
//            this.y -= this.speed;
//        }
//        //GO LEFT
//        if (this.x > x && this.y == y){
//            this.x -= this.speed;
//        }
//        //GO UP-LEFT
//        if (this.x > x && this.y < y){
//            this.x -= this.speed;
//            this.y += this.speed;
//        }
//        //GO UP
//        if (this.x == x && this.y < y){
//            this.y += this.speed;
//        }
//        //GO UP-RIGHT
//        if (this.x < x && this.y < y){
//            this.x += this.speed;
//            this.y += this.speed;
//        }
//        //GO RIGHT
//        if (this.x < x && this.y == y ){
//            this.x += this.speed;
//        }
//        //GO DOWN-RIGHT
//        if (this.x < x && this.y > y){
//            this.x += this.speed;
//            this.y -= this.speed;
//        }
    }
}

