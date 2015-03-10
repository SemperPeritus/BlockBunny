package com.platonefimov.blockbunny.entities;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.platonefimov.blockbunny.managers.Variables.*;

import com.platonefimov.blockbunny.Game;



public class HUD {

    private Player player;

    private TextureRegion[] blocks;


    public HUD(Player player) {
        this.player = player;

        Texture texture = Game.resources.getTexture("hud");

        blocks = new TextureRegion[3];
        for (int i = 0; i < blocks.length; i++)
            blocks[i] = new TextureRegion(texture, 32 + i * 16, 0, 16, 16);
    }


    public void render(SpriteBatch spriteBatch) {
        short bits = player.body.getFixtureList().first().getFilterData().maskBits;

        spriteBatch.begin();

        if ((bits & BIT_RED) != 0)
            spriteBatch.draw(blocks[0], 20, 210);
        if ((bits & BIT_GREEN) != 0)
            spriteBatch.draw(blocks[1], 20, 210);
        if ((bits & BIT_BLUE) != 0)
            spriteBatch.draw(blocks[2], 20, 210);

        spriteBatch.end();
    }

}
