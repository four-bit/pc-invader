package com.fourbit.pc_invader.entities;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;


public class EntityConfig {
    protected final JsonValue map;


    public EntityConfig(String configPath) {
        JsonReader jsonReader = new JsonReader();
        this.map = jsonReader.parse(new FileHandle(configPath));
    }
}
