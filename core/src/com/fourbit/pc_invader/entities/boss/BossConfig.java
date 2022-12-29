package com.fourbit.pc_invader.entities.boss;


import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.fourbit.pc_invader.utils.Anchor;

import java.util.ArrayList;


public class BossConfig {
    private final int mainHp, segmentHp;
    private final ArrayList<Anchor> anchors;


    BossConfig(String configPath) {
        JsonReader jsonReader = new JsonReader();
        JsonValue map = jsonReader.parse(new FileHandle(configPath));

        this.mainHp = map.getInt("mainHp");
        this.segmentHp = map.getInt("segmentHp");

        this.anchors = new ArrayList<>();
        JsonValue anchor = map.getChild("anchors");
        for (; anchor != null; anchor = anchor.next()) {
            this.anchors.add(new Anchor(
                    anchor.getFloat("x"),
                    anchor.getFloat("y"),
                    anchor.getFloat("homingSpeed")
            ));
        }
    }
}
