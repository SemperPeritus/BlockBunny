package com.platonefimov.blockbunny.managers;


import com.badlogic.gdx.graphics.g2d.TextureRegion;



public class Animation {

    private TextureRegion frames[];

    private float delay;
    private float timer;
    private int currentFrame;
    private int timesPlayed;


    public Animation() {
    }

    public Animation(TextureRegion[] frames) {
        this(frames, 1 / 12f);
    }

    public Animation(TextureRegion[] frames, float delay) {
    }


    public void setFrames(TextureRegion[] frames) {
        this.frames = frames;
        this.delay = 1 / 12f;
        timer = 0;
        currentFrame = 0;
        timesPlayed = 0;
    }

    public void setFrames(TextureRegion[] frames, float delay) {
        this.frames = frames;
        this.delay = delay;
        timer = 0;
        currentFrame = 0;
        timesPlayed = 0;
    }


    public void update(float deltaTime) {
        if (delay <= 0)
            return;
        timer += deltaTime;
        while (timer >= delay)
            step();
    }


    private void step() {
        timer -= delay;
        currentFrame++;
        if (currentFrame == frames.length) {
            currentFrame = 0;
            timesPlayed++;
        }
    }


    public TextureRegion getFrame() {
        return frames[currentFrame];
    }


    public int getTimesPlayed() {
        return timesPlayed;
    }

}
