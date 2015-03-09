package com.platonefimov.blockbunny;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.platonefimov.blockbunny.managers.Variables.*;

import com.platonefimov.blockbunny.managers.GameKeys;
import com.platonefimov.blockbunny.managers.InputProcessor;
import com.platonefimov.blockbunny.managers.Resources;
import com.platonefimov.blockbunny.managers.StateManager;



public class Game implements ApplicationListener {

    private float frameTimer;

    private OrthographicCamera camera;
    private OrthographicCamera hudCamera;

    private SpriteBatch spriteBatch;

    private StateManager stateManager;

    public static Resources resources;


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

        resources = new Resources();

        stateManager = new StateManager(this);

        Gdx.input.setInputProcessor(new InputProcessor());
    }


    public void render() {
        frameTimer += Gdx.graphics.getDeltaTime();
        while (frameTimer >= SPF) {
            frameTimer -= SPF;
            stateManager.update(SPF);
            stateManager.render();
            GameKeys.update();
        }

        spriteBatch.setProjectionMatrix(hudCamera.combined);
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
