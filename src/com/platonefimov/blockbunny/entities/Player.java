package com.platonefimov.blockbunny.entities;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

import com.platonefimov.blockbunny.Game;



public class Player extends Sprite {

    private int numCrystals;
    private int totalCrystals;


    public Player(Body body) {
        super(body);

        setAnimation(TextureRegion.split(Game.resources.getTexture("bunny"), 32, 32)[0]);
    }


    public void collectCrystal() {
        numCrystals++;
    }

    public void setTotalCrystals(int crystals) {
        totalCrystals = crystals;
    }


    public int getNumCrystals() {
        return numCrystals;
    }

    public int getTotalCrystals() {
        return totalCrystals;
    }

}
