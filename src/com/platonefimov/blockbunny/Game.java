package com.platonefimov.blockbunny;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.platonefimov.blockbunny.managers.StateManager;


public class Game implements ApplicationListener {

    public static final String TITLE = "Block Bunny";
    public static final int V_WIDTH = 320;
    public static final int V_HEIGHT = 240;
    public static final int SCALE = 2;
    public static final int WIDTH = V_WIDTH * SCALE;
    public static final int HEIGHT = V_HEIGHT * SCALE;

    public static final float SPF = 1 / 60f;
    private float frameTimer;

    private OrthographicCamera camera;
    private OrthographicCamera hudCamera;

    private SpriteBatch spriteBatch;

    private StateManager stateManager;


    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public OrthographicCamera getHudCamera() {
        return hudCamera;
    }


    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, V_WIDTH, V_HEIGHT);
        hudCamera = new OrthographicCamera();
        hudCamera.setToOrtho(false, V_WIDTH, V_HEIGHT);

        spriteBatch = new SpriteBatch();

        stateManager = new StateManager(this);
    }


    public void render() {
        frameTimer += Gdx.graphics.getDeltaTime();
        while (frameTimer >= SPF) {
            frameTimer -= SPF;
            stateManager.update(SPF);
            stateManager.render();
        }
    }


    public void resize(int width, int height) {
    }
    public void pause() {
    }
    public void resume() {
    }


    public void dispose() {

    }
}
