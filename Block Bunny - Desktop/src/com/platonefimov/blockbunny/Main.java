package com.platonefimov.blockbunny;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import static com.platonefimov.blockbunny.managers.Variables.*;



public class Main {

    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = TITLE;
        cfg.width = WIDTH;
        cfg.height = HEIGHT;

        new LwjglApplication(new Game(), cfg);
    }
}
