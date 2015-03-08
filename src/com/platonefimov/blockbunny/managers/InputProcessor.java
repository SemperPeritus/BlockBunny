package com.platonefimov.blockbunny.managers;


import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;



public class InputProcessor extends InputAdapter {

    public boolean keyDown(int key) {
        if (key == Keys.Z)
            GameKeys.setKey(GameKeys.BUTTON_1, true);
        if (key == Keys.X)
            GameKeys.setKey(GameKeys.BUTTON_2, true);

        return true;
    }

    public boolean keyUp(int key) {
        if (key == Keys.Z)
            GameKeys.setKey(GameKeys.BUTTON_1, false);
        if (key == Keys.X)
            GameKeys.setKey(GameKeys.BUTTON_2, false);

        return true;
    }

}
