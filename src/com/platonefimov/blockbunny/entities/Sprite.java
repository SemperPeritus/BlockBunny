package com.platonefimov.blockbunny.entities;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import static com.platonefimov.blockbunny.managers.Variables.*;

import com.platonefimov.blockbunny.managers.Animation;



public abstract class Sprite {

    public Body body;
    protected Animation animation;
    protected float width;
    protected float height;


    public Sprite(Body body) {
        this.body = body;
        animation = new Animation();
    }


    public Vector2 getPosition() {
        return body.getPosition();
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }


    public void setAnimation(TextureRegion[] frames) {
        setAnimation(frames, 1 / 12f);
        width = frames[0].getRegionWidth();
        height = frames[0].getRegionHeight();
    }

    public void setAnimation(TextureRegion[] frames, float delay) {
        animation.setFrames(frames, delay);
        width = frames[0].getRegionWidth();
        height = frames[0].getRegionHeight();
    }


    public void update(float deltaTime) {
        animation.update(deltaTime);
    }


    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();

        spriteBatch.draw(animation.getFrame(), body.getPosition().x * PPM - width / 2,
                body.getPosition().y * PPM - height / 2);

        spriteBatch.end();
    }

}
