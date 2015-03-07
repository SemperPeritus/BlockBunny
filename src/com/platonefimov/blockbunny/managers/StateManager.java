package com.platonefimov.blockbunny.managers;


import com.platonefimov.blockbunny.Game;
import com.platonefimov.blockbunny.states.GameState;
import com.platonefimov.blockbunny.states.PlayState;

import java.util.Stack;


public class StateManager {

    private Game game;

    private Stack<GameState> gameStates;

    public static final int PLAY = 1;


    public StateManager(Game game) {
        this.game = game;
        gameStates = new Stack<GameState>();
        pushState(PLAY);
    }


    private void popState() {
        GameState gameState = gameStates.pop();
        gameState.dispose();
    }

    private void pushState(int state) {
        gameStates.push(getState(state));
    }


    public Game game() {
        return game;
    }


    private GameState getState(int state) {
        if (state == PLAY)
            return new PlayState(this);

        return null;
    }


    public void setState(int state) {
        popState();
        pushState(state);
    }


    public void update(float deltaTime) {
        gameStates.peek().update(deltaTime);
    }


    public void render() {
        gameStates.peek().render();
    }

}
