package com.platonefimov.blockbunny.states;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.platonefimov.blockbunny.Game;
import com.platonefimov.blockbunny.managers.StateManager;



public abstract class GameState {

    protected Game game;
    protected StateManager stateManager;

    protected SpriteBatch spriteBatch;
    protected OrthographicCamera camera;
    protected OrthographicCamera hudCamera;


    protected GameState(StateManager stateManager) {
        this.stateManager = stateManager;
        game = stateManager.game();
        spriteBatch = game.getSpriteBatch();
        camera = game.getCamera();
        hudCamera = game.getHudCamera();
    }

    public abstract void update(float deltaTime);
    public abstract void handleInput();
    public abstract void render();
    public abstract void dispose();

}
