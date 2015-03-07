package com.platonefimov.blockbunny;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;



public class Main {

    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = Game.TITLE;
        cfg.width = Game.WIDTH;
        cfg.height = Game.HEIGHT;

        new LwjglApplication(new Game(), cfg);
    }
}
