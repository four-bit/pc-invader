package com.fourbit.pc_invader.music;

import com.badlogic.gdx.Gdx;

public class music implements com.badlogic.gdx.audio.Music {
    protected com.badlogic.gdx.audio.Music music;
    public music(String path){
        com.badlogic.gdx.audio.Music music = Gdx.audio.newMusic(Gdx.files.internal(path));
    }

    @Override
    public void play() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public void setLooping(boolean isLooping) {

    }

    @Override
    public boolean isLooping() {
        return false;
    }

    @Override
    public void setVolume(float volume) {

    }

    @Override
    public float getVolume() {
        return 0;
    }

    @Override
    public void setPan(float pan, float volume) {

    }

    @Override
    public void setPosition(float position) {

    }

    @Override
    public float getPosition() {
        return 0;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void setOnCompletionListener(OnCompletionListener listener) {

    }
}
