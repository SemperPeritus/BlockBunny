package com.platonefimov.blockbunny.entities;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

import com.platonefimov.blockbunny.Game;



public class Crystal extends Sprite {

    public Crystal(Body body) {
        super(body);

        setAnimation(TextureRegion.split(Game.resources.getTexture("crystal"), 16, 16)[0]);
    }

}
