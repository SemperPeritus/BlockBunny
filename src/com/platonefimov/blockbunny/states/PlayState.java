package com.platonefimov.blockbunny.states;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.platonefimov.blockbunny.managers.Variables.*;

import com.platonefimov.blockbunny.Game;
import com.platonefimov.blockbunny.managers.StateManager;



public class PlayState extends GameState {

    private World world;

    private Box2DDebugRenderer renderer;

    private OrthographicCamera box2DCamera;


    public PlayState(StateManager stateManager) {
        super(stateManager);

        world = new World(new Vector2(0, -9.81f), true);

        renderer = new Box2DDebugRenderer();

        box2DCamera = new OrthographicCamera();
        box2DCamera.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);

        BodyDef bodyDef = new BodyDef();
        Body body;
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.position.set(160 / PPM, 120 / PPM);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);
        shape.setAsBox(50 / PPM, 5 / PPM);
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);

        bodyDef.position.set(160 / PPM, 200 / PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        shape.setAsBox(5 / PPM, 5 / PPM);
        fixtureDef.shape = shape;
        fixtureDef.restitution = 1f;
        body.createFixture(fixtureDef);
    }


    public void update(float deltaTime) {
        world.step(deltaTime, 6, 2);
    }


    public void handleInput() {
    }


    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render(world, box2DCamera.combined);
    }


    public void dispose() {
    }

}
